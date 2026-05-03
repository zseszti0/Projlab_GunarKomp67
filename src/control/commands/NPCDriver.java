package control.commands;

import control.GameManager;
import model.map.PathFinder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class NPCDriver extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<model.players.NPCDriver> npc = gameManager.getNpcDrivers();
        npc.add(new model.players.NPCDriver(args.get(0), new PathFinder("pathFinder")));
        gameManager.setNpcDrivers(npc);

        /// /CONSOL OUT
        try {
            output.write(("Az NPC autó(k) sikeresen hozzáadva a játékkonfigurációhoz.").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
