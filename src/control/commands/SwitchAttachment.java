package control.commands;

import control.GameManager;
import model.players.Cleaner;
import model.vehicles.SnowShovel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class SwitchAttachment extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        gameManager.switchAttachment(args.get(0), args.get(1));
        /// /CONSOL OUT
        try {
            output.write(("A(z)"+ args.get(0)+". Számú hókotró feje sikeresen lecserélve a következőre: "+args.get(1)+".\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
