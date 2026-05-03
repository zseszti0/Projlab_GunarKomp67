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
        gameCommands.put("switchattachment", new SwitchAttachment());


        /// /CONSOL OUT
        try {
            outputStream.write(("A játék elkezdődött, a hó hullik Zúzmaravárosban.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            Scanner scanner = new Scanner(inputStream);
            String input = scanner.nextLine();
            List<String> in_args = Arrays.asList(input.split(" "));
            String command = in_args.get(0);
            if(in_args.size() > 1) in_args = in_args.subList(1, in_args.size());
            if (command.equals("exit")) {
                /// /CONSOL OUT
                try {
                    outputStream.write(("Sikeresen kiléptél az aktuális játszmából. A program visszatért a tétlen fázisba.\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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

                            /// /CONSOL OUT
                            try {
                                outputStream.write(("Sikeresen létrehoztál egy új játékot a(z)" + in_args.get(0) + " térképen. A véletlen események beállítva. A konfigurációs fázis megkezdődött.\n").getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        case "load": {
                            XMLParser parser = new XMLParser();
                            gameManager = parser.loadGame(in_args.get(0));
                            state = ProgramStates.GAME;

                            /// /CONSOL OUT
                            try {
                                outputStream.write(("A(z)"+ in_args.get(0) +"<játékállás neve> nevű játékállás sikeresen betöltve. Belépés a játszma fázisba.\n").getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        default:
                            try {
                                outputStream.write(("Error Command").getBytes());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                    }
                    break;
                case CONFIG:
                    if(command.equals("start")) {
                        state = ProgramStates.GAME;
                        gameManager.startGame();
                        /// /CONSOL OUT
                        try {
                            outputStream.write(("A konfiguráció befejeződött. A játék sikeresen elindult, belépés a játék fázisba.\n").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(!configCommands.get(command).execute(gameManager,in_args,outputStream )){
                        try {
                            outputStream.write(("Error Command").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case GAME:
                    if(command.equals("exit")) {
                        state = ProgramStates.IDLE;
                    }
                    if(!gameCommands.get(command).execute(gameManager,in_args, outputStream)){
                        try {
                            outputStream.write(("Error Command").getBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }
        }
    }
}
