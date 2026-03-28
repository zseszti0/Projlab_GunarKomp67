package skeleton;

import java.util.List;

public class Skeleton {

    public static void main(String[] args) {

    }

    public static void log(String command){}

    public static boolean askBoolQuestion(String question){
        log(question);
        return true;
    }
    public static int askListQuestion(String question, List<Object> options){
        log(question);
        for( int i = 0; i < options.size(); i++){
            System.out.println(i + ": " + options.get(i));
        }
        return 0;
    }
}
