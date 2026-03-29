package model;

import skeleton.Skeleton;

import java.util.List;

public class DummyTester {
    public String getName() {
        return name;
    }

    private String name;


    public static void askQuestion(){
        List<String> options = List.of("ELekes Márton", "Senki");
        int b = Skeleton.askListQuestion("[?] Kibe vagy szerelmes Huszerl GÁbor? (Opció száma): \n", options);
    }
}
