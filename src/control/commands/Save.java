package control.commands;

import control.GameManager;
import control.XMLParser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Játékparancs a jelenlegi játékállás kimentésére egy XML fájlba.
 */
public class Save extends GameCommand {
    /**
     * Elmenti a játékot.
     * Lépései:
     * 1. Példányosít egy XMLParser-t.
     * 2. Meghívja a parser mentő függvényét, átadva neki a játékállapotot és a fájlnevet (args[0]).
     *
     * @param gameManager A mentendő játékállapotot tartalmazó objektum.
     * @param args args[0]: a fájl neve vagy útvonala, amibe a mentés történik.
     * @return Mindig true értékkel tér vissza.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        XMLParser parser = new XMLParser();
        parser.saveGame(gameManager, args.get(0));

        /// /CONSOL OUT
        try {
            output.write(("A játékállás sikeresen elmentve a(z) " + args.get(0) + "fájlba. A játék folytatódik.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
