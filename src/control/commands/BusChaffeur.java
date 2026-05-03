package control.commands;

import control.GameManager;

import java.util.List;

public class BusChaffeur extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<model.players.BusChaffeur> busSs = gameManager.getBusChauffeurs();
        busSs.add(new model.players.BusChaffeur(args.get(0)));
        gameManager.setBusChauffeurs(busSs);
        return true;
    }
}
