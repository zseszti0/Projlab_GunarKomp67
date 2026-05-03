package control.commands;

import control.GameManager;
import model.map.Tile;
import model.players.BusChaffeur;

import java.util.List;

public class Bus extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        BusChaffeur busChaffeur = gameManager.getBusChauffeurs().stream().filter(b -> b.getName().equals(args.get(0))).findFirst().orElse(null);
        if (busChaffeur == null)
            return false;
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        busChaffeur.addBus(new model.vehicles.Bus(args.get(0) + busChaffeur.getVehicles().size(),pos ,null));
        return true;
    }
}
