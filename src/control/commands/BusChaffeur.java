package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BusChaffeur extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<model.players.BusChaffeur> busSs = gameManager.getBusChauffeurs();
        busSs.add(new model.players.BusChaffeur(args.get(0)));
        gameManager.setBusChauffeurs(busSs);

        /// /CONSOL OUT
        try {
            output.write(("A(z)"+ args.get(0)+" nevű buszsofőr szerepű játékos sikeresen hozzáadva a játékkonfigurációhoz.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
