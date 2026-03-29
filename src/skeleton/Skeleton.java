package skeleton;

import model.DummyTester;

import java.util.List;

public class Skeleton {

    public static void main(String[] args) {
        DummyTester.askQuestion();
    }

    public static void log(String command){}

    public static boolean askBoolQuestion(String question){
        return true;
    }
    public static int askListQuestion(String question, List<String> options){
        return 0;
    }
}