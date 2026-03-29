package logger;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SkeletonTracer {
    private static int level = 0;
    private static final Scanner scanner = new Scanner(System.in);


    private static final String PACKAGE_FILTER = "model.*";
    private static final String TARGET_MAIN_CLASS = "skeleton.Skeleton";


    public static void main(String[] args) throws Exception {
        // 1. Get the JDI Launcher
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        LaunchingConnector connector = vmm.defaultConnector();

        // 2. Tell the launcher which class to run
        Map<String, Connector.Argument> env = connector.defaultArguments();
        env.get("main").setValue(TARGET_MAIN_CLASS);

        // 3. Boot up the target Virtual Machine
        VirtualMachine vm = connector.launch(env);

        // 4. Set up our intercepts (Requests)
        EventRequestManager erm = vm.eventRequestManager();


        MethodEntryRequest entryReq = erm.createMethodEntryRequest();
        entryReq.addClassFilter(PACKAGE_FILTER);
        entryReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        entryReq.enable();

        MethodExitRequest exitReq = erm.createMethodExitRequest();
        exitReq.addClassFilter(PACKAGE_FILTER);
        exitReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        exitReq.enable();

        //Listen for the skeleton's logs
        MethodEntryRequest logReq = erm.createMethodEntryRequest();
        logReq.addClassFilter(TARGET_MAIN_CLASS);
        logReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        logReq.enable();
        // ------------------------------------------------------------

        // 5. Start the Event Loop to listen to the JVM
        EventQueue eventQueue = vm.eventQueue();
        boolean connected = true;

        while (connected) {
            EventSet eventSet = eventQueue.remove();
            for (Event event : eventSet) {
                if (event instanceof MethodEntryEvent) {
                    handleMethodEntry((MethodEntryEvent) event);
                }
                else if (event instanceof MethodExitEvent) {
                    handleMethodExit((MethodExitEvent) event);
                }
                else if (event instanceof VMDisconnectEvent || event instanceof VMDeathEvent) {
                    connected = false;
                }
            }
            eventSet.resume();
        }
    }

    private static void handleMethodEntry(MethodEntryEvent event) {

        Method method = event.method();
        String className = method.declaringType().name();

        //Communication with the vm's console
        boolean isSkeletonFunc = className.equals(TARGET_MAIN_CLASS);
        if (isSkeletonFunc && method.name().equals("log")) {
            try {
                Value msgArg = event.thread().frame(0).getArgumentValues().get(0);
                String cleanMsg = msgArg.toString().replace("\"", "");
                System.out.println(cleanMsg);
            } catch (Exception e) {}
            return;
        }
        else if(isSkeletonFunc && method.name().equals("askBoolQuestion")) {
            try {
                // 1. Grab the question string passed from the skeleton
                Value msgArg = event.thread().frame(0).getArgumentValues().get(0);
                String question = msgArg.toString().replace("\"", "");

                // 2. Print the question to the Tracer's console and get the user's answer
                System.out.print(question);
                String userInput = scanner.nextLine().trim().toLowerCase();
                boolean answer = userInput.equals("i") || userInput.equals("I") || userInput.equals("Igen");

                // 3. Convert our local boolean into a JDI VirtualMachine boolean
                Value jdiAnswer = event.virtualMachine().mirrorOf(answer);

                // 4. THE MAGIC: Force the target VM to immediately return our answer!
                event.thread().forceEarlyReturn(jdiAnswer);

            } catch (Exception e) {
                System.out.println("   [ERROR] Failed to force return: " + e.getMessage());
            }

            return; // Stop processing so we don't print the >>> [CALL] log
        }
        else if(isSkeletonFunc && method.name().equals("askListQuestion")) {
            try {
                Value msgArg = event.thread().frame(0).getArgumentValues().get(0);
                String question = msgArg.toString().replace("\"", "");

                System.out.print(question);

                //Get this options
                Value arrayArg = event.thread().frame(0).getArgumentValues().get(1);
                ArrayReference arrayRef = (ArrayReference) arrayArg; // JDI natively understands arrays!

                // 3. Loop through the JDI array and print them
                List<Value> jdiValues = arrayRef.getValues();
                for (int i = 0; i < jdiValues.size(); i++) {
                    String optionString = jdiValues.get(i).toString().replace("\"", "");
                    System.out.println("      " + (i + 1) + ". " + optionString);
                }

                //Get the user's answer'
                String userInput = scanner.nextLine().trim().toLowerCase();
                int answer = Integer.parseInt(userInput);

                Value jdiAnswer = event.virtualMachine().mirrorOf(answer);

                event.thread().forceEarlyReturn(jdiAnswer);

            } catch (Exception e) {
                System.out.println("   [ERROR] Failed to force return: " + e.getMessage());
            }

            return;
        }
        else if (method.name().equals("<init>") || method.isSynthetic() || isSkeletonFunc) return;



        //Normal function calls
        level++;
        System.out.println(writeOutFullMethod(event));
    }

    private static void handleMethodExit(MethodExitEvent event) {
        Method method = event.method();
        if (method.name().equals("<init>") || method.isSynthetic()) return;

        Value returnValue = event.returnValue();

        //Normal function calls
        System.out.println(writeOutFullMethod(event) + " visszatért " +
                (returnValue.toString() == "<void value>" ? ": void" : (returnValue.toString() + " : " + returnValue.type().name()))
            + " értékkel.");

        level--;
    }

    private static String writeOutFullMethod(MethodEntryEvent event){

        Method method = event.method();
        String className = method.declaringType().name();
        String ret = "";

        //indentation
        for(int i=0; i < level; i++) ret += "       ";
        ret += "-> ";

        try {
            ObjectReference thisObject = event.thread().frame(0).thisObject();
            String callerName = getInstanceName(thisObject, className);
            ret += (callerName+ ".");

            ret += method.name() + "(";

            List<Value> args = event.thread().frame(0).getArgumentValues();
            for (int i = 0; i < args.size(); i++) {
                ret += (args.get(i));
                if (i < args.size() - 1) ret +=(", ");
            }
        } catch (IncompatibleThreadStateException e) {
            System.out.print("?");
        }
        ret +=(")");
        return ret;
    }
    private static String writeOutFullMethod(MethodExitEvent event){

        Method method = event.method();
        String className = method.declaringType().name();
        String ret = "";

        //indentation
        for(int i=0; i < level; i++) ret += "       ";
        ret += "<- ";

        try {

            ObjectReference thisObject = event.thread().frame(0).thisObject();
            String callerName = getInstanceName(thisObject, className);
            ret += (callerName+ ".");
            ret += method.name() + "(";


            List<Value> args = event.thread().frame(0).getArgumentValues();
            for (int i = 0; i < args.size(); i++) {
                ret += (args.get(i));
                if (i < args.size() - 1) ret +=(", ");
            }
        } catch (IncompatibleThreadStateException e) {
            System.out.print("?");
        }
        ret +=(")");
        return ret;
    }

    // Helper to extract the "name" attribute from any object in memory
    private static String getInstanceName(ObjectReference obj, String className) {
        if (obj == null) return className; //in case of static methods

        try {
            Field nameField = obj.referenceType().fieldByName("name");
            if (nameField != null) {
                Value nameValue = obj.getValue(nameField);

                if (nameValue instanceof StringReference) {
                    return ((StringReference) nameValue).value();
                } else if (nameValue != null) {
                    return nameValue.toString().replace("\"", "");
                }
            }
        } catch (Exception e) {
        }

        return className + "_" + obj.uniqueID();
    }
}