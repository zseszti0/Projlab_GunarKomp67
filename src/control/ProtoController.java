package control;

import control.commands.*;
import model.map.Tile;

import java.io.*;
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
    private static ProgramStates state = ProgramStates.IDLE;

    private static GameManager gameManager;
    private static HashMap<String, ConfigCommand> configCommands;
    private static HashMap<String, GameCommand> gameCommands;

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        if (args.length > 0) {
            //first arg is input txt, second is output txt
            try {
                inputStream = new FileInputStream(args[0]);
                outputStream = new FileOutputStream(args[1]);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        configCommands = new HashMap<>();
        gameCommands = new HashMap<>();
        configCommands.put("bus", new Bus());
        configCommands.put("buschauffeur", new BusChaffeur());
        gameCommands.put("buy",new Buy());
        gameCommands.put("buysnowshovel",new BuySnowShovel());
        configCommands.put("cleaner", new Cleaner());
        gameCommands.put("drive", new Drive());
        configCommands.put("npcdriver", new NPCDriver());
        gameCommands.put("pass", new Pass());
        gameCommands.put("save", new Save());
        configCommands.put("snowshovel", new SnowShovel());
        configCommands.put("start", new Start());
        gameCommands.put("switchattachment", new SwitchAttachment());

        while (true) {
            Scanner scanner = new Scanner(inputStream);
            String input = scanner.nextLine();
            List<String> in_args = Arrays.asList(input.split(" "));
            String command = in_args.get(0);
            if(in_args.size() > 1) in_args.remove(0);
            if (command.equals("exit")) {
                break;
            }
            switch (state) {
                case IDLE:
                    switch (command) {
                        case "new": {
                            gameManager = new GameManager();
                            XMLParser parser = new XMLParser();
                            List<Tile> map = parser.loadMap(in_args.get(0));
                            gameManager.setMap(map);
                            if(in_args.size() > 1) {
                                if(in_args.get(1).equals("-r"))
                                    gameManager.setRandomized(false);
                            }
                            state = ProgramStates.CONFIG;
                            break;
                        }
                        case "load": {
                            gameManager = new GameManager();
                            XMLParser parser = new XMLParser();
                            parser.loadGame(in_args.get(0));
                            state = ProgramStates.GAME;
                            break;
                        }
                        default:
                            try {
                                outputStream.write(("Invalid Command").getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                    }
                    break;
                case CONFIG:
                    if(command.equals("start")) {
                        state = ProgramStates.GAME;
                    }
                    if(!configCommands.get(command).execute(gameManager,in_args)){
                        try {
                            outputStream.write(("Invalid Command").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case GAME:
                    if(command.equals("exit")) {
                        state = ProgramStates.IDLE;
                    }
                    if(!gameCommands.get(command).execute(gameManager,in_args)){
                        try {
                            outputStream.write(("Invalid Command").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
        }
    }
}
