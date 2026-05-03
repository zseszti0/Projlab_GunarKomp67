package control.commands;

import control.GameManager;
import model.map.PathFinder;

import java.util.List;

public class NPCDriver extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<model.players.NPCDriver> npc = gameManager.getNpcDrivers();
        npc.add(new model.players.NPCDriver(args.get(0), new PathFinder("pathFinder")));
        gameManager.setNpcDrivers(npc);
        return true;
    }
}
