package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Cleaner extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<model.players.Cleaner> cleaners = gameManager.getCleaners();
        cleaners.add(new model.players.Cleaner(args.get(0)));
        gameManager.setCleaners(cleaners);

        /// /CONSOL OUT
        try {
            output.write(("A(z)" + args.get(0) + "nevű takarító szerepű játékos sikeresen hozzáadva a játékkonfigurációhoz.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
