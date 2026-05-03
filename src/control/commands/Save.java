package control.commands;

import control.GameManager;
import control.XMLParser;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Save extends GameCommand {
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
