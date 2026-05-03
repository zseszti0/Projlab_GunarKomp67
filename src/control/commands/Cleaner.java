package control.commands;

import control.GameManager;

import java.util.List;

public class Cleaner extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<model.players.Cleaner> cleaners = gameManager.getCleaners();
        cleaners.add(new model.players.Cleaner(args.get(0)));
        gameManager.setCleaners(cleaners);
        return true;
    }
}
