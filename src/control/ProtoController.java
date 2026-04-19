package control;

import control.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ProtoController {
    private static boolean isTextInput = false;
    private static states state = states.IDLE;
    private static enum states {
        IDLE,
        CONFIG,
        GAME
    }
    private static GameManager gameManager;
    private static HashMap<String, Command> commands;

    public static void main(String[] args) {
        // Check if arguments were actually passed
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                System.out.println("Argument " + i + ": " + args[i]);
            }
        }
        XMLParser parser = new XMLParser();
        gameManager = parser.LoadGame("input.xml");

        commands = new HashMap<>();
        commands.put("bus", new Bus());
        commands.put("buschauffeur", new BusChauffeur());
        commands.put("buy",new Buy());
        commands.put("buysnowshovel",new BuySnowShovel());
        commands.put("cleaner", new Cleaner());
        commands.put("drive", new Drive());
        commands.put("exit", new Exit());
        commands.put("load", new Load());
        commands.put("new", new New());
        commands.put("npcdriver", new NPCDriver());
        commands.put("pass", new Pass());
        commands.put("save", new Save());
        commands.put("snowshovel", new SnowShovel());
        commands.put("start", new Start());
        commands.put("switchattachment", new SwitchAttachment());

        Scanner scanner = new Scanner(System.in);
        commands.get(scanner.nextLine()).Execute();
    }
}
