package skeleton;

import model.TestModel;

import java.util.function.Function;

public class UseCase {
    private String name;
    private Function<String, TestModel> arrange;
    private Function<String, Boolean> act;

    public UseCase(String name, Function<String, TestModel> arrange, Function<String, Boolean> act) {
        this.name = name;
        this.arrange = arrange;
        this.act = act;
    }

    public void run(String input){
        TestModel model = arrange.apply(input);
        act.apply(input);
    }
}
