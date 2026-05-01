package control;

import control.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

enum ProgramStates {
    IDLE,
    CONFIG,
    GAME
}
public class ProtoController {
    private static boolean isTextInput = false;
    private static ProgramStates state = ProgramStates.IDLE;

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
        gameManager = parser.loadGame("input.xml");

        commands = new HashMap<>();
        commands.put("bus", new Bus());
        commands.put("buschauffeur", new BusChauffeur());
        commands.put("buy",new Buy());
        commands.put("buysnowshovel",new BuySnowShovel());
        commands.put("cleaner", new Cleaner());
        commands.put("drive", new Drive());
        commands.put("exit", new Exit());
        commands.put("npcdriver", new NPCDriver());
        commands.put("pass", new Pass());
        commands.put("save", new Save());
        commands.put("snowshovel", new SnowShovel());
        commands.put("start", new Start());
        commands.put("switchattachment", new SwitchAttachment());

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        List<String> in_args = Arrays.asList(input.split(" "));
        if(!commands.get(in_args.get(0)).execute(gameManager,state,in_args.subList(1, in_args.size()))){
            System.out.println("Invalid command");
        }
    }
}
