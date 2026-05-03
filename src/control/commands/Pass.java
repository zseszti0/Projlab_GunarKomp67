package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Pass extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        gameManager.pass();
        /// /CONSOL OUT
        try {
            output.write(("A körödet sikeresen befejezted (passzoltál). A következő játékos jön.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
