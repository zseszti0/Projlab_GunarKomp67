package logger;

import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.List;
import java.util.Map;

public class SkeletonTracer {
    private static int level = 0;

    // The package you want to monitor (ignoring java.lang, etc.)
    private static final String PACKAGE_FILTER = "model.*";
    // The actual main class of your MVC project
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


        // Request to listen for Method Entries
        MethodEntryRequest entryReq = erm.createMethodEntryRequest();
        entryReq.addClassFilter(PACKAGE_FILTER);
        entryReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        entryReq.enable();

        // Request to listen for Method Exits
        MethodExitRequest exitReq = erm.createMethodExitRequest();
        exitReq.addClassFilter(PACKAGE_FILTER);
        exitReq.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        exitReq.enable();


        // --- NEW CODE: Add a special listener just for the Logger ---
        MethodEntryRequest logReq = erm.createMethodEntryRequest();
        logReq.addClassFilter("skeleton.TestForTheLogger"); // Listen ONLY to this specific class
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
            // Let the target program continue running!
            eventSet.resume();
        }
    }

    private static void handleMethodEntry(MethodEntryEvent event) {
        Method method = event.method();
        String className = method.declaringType().name();

        // --- NEW CODE: Intercept our special log method ---
        if (className.equals(TARGET_MAIN_CLASS) && method.name().equals("log")) {
            try {
                // Grab the string they passed in
                Value msgArg = event.thread().frame(0).getArgumentValues().get(0);
                // Remove the quotes around the JDI string value
                String cleanMsg = msgArg.toString().replace("\"", "");
                System.out.println(cleanMsg);
            } catch (Exception e) {}
            return; // Don't print the normal >>> [CALL] for this
        }
        else if (method.name().equals("<init>") || method.isSynthetic() || (className.equals(TARGET_MAIN_CLASS) && !method.name().equals("log"))) return;

        level++;
        System.out.println(writeOutFullMethod(event));
    }

    private static void handleMethodExit(MethodExitEvent event) {
        Method method = event.method();
        if (method.name().equals("<init>") || method.isSynthetic()) return;

        Value returnValue = event.returnValue();

        System.out.println(writeOutFullMethod(event) + " visszatért " +
                (returnValue.toString() == "<void value>" ? ": void" : (returnValue.toString() + " : " + returnValue.type().name()))
            + " értékkel.");

        level--;
    }

    private static String writeOutFullMethod(MethodEntryEvent event){

        Method method = event.method();
        String className = method.declaringType().name();
        String ret = "";

        for(int i=0; i < level; i++) ret += "       "; // Indent based on call depth
        ret += "-> ";

        try {
            ObjectReference thisObject = event.thread().frame(0).thisObject();
            String callerName = getInstanceName(thisObject, className);
            ret += (callerName+ ".");

            ret += method.name() + "(";

            // Attempt to print argument values
            List<Value> args = event.thread().frame(0).getArgumentValues();
            for (int i = 0; i < args.size(); i++) {
                ret += (args.get(i));
                if (i < args.size() - 1) ret +=(", ");
            }
        } catch (IncompatibleThreadStateException e) {
            System.out.print("?"); // Thread wasn't suspended properly
        }
        ret +=(")");
        return ret;
    }
    private static String writeOutFullMethod(MethodExitEvent event){

        Method method = event.method();
        String className = method.declaringType().name();
        String ret = "";

        for(int i=0; i < level; i++) ret += "       "; // Indent based on call depth
        ret += "<- ";

        try {

            ObjectReference thisObject = event.thread().frame(0).thisObject();
            String callerName = getInstanceName(thisObject, className);
            ret += (callerName+ ".");
            ret += method.name() + "(";

            // Attempt to print argument values
            List<Value> args = event.thread().frame(0).getArgumentValues();
            for (int i = 0; i < args.size(); i++) {
                ret += (args.get(i));
                if (i < args.size() - 1) ret +=(", ");
            }
        } catch (IncompatibleThreadStateException e) {
            System.out.print("?"); // Thread wasn't suspended properly
        }
        ret +=(")");
        return ret;
    }

    // Helper to extract the "name" attribute from any object in memory
    private static String getInstanceName(ObjectReference obj, String className) {
        if (obj == null) return className; // It's a static method, just use the class name

        try {
            // Look for the attribute literally named "name"
            Field nameField = obj.referenceType().fieldByName("name");
            if (nameField != null) {
                Value nameValue = obj.getValue(nameField);

                // If the name is set, clean off the JDI quotes and return it!
                if (nameValue instanceof StringReference) {
                    return ((StringReference) nameValue).value();
                } else if (nameValue != null) {
                    return nameValue.toString().replace("\"", "");
                }
            }
        } catch (Exception e) {
            // If anything goes wrong, we will just fall through to the fallback below
        }

        // Fallback just in case a class forgot to set its name
        return className + "_" + obj.uniqueID();
    }
}