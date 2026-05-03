package control.commands;

import control.GameManager;
import model.map.Tile;
import model.players.Cleaner;

import java.util.List;

public class SnowShovel extends ConfigCommand {

    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        Cleaner cleaner = gameManager.getCleaners().stream().filter(b -> b.getName().equals(args.get(0))).findFirst().orElse(null);
        if (cleaner == null)
            return false;
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if (pos == null)
            return false;
        model.vehicles.SnowShovel snowShovel1 = new model.vehicles.SnowShovel(args.get(0) + cleaner.getVehicles().size());
        cleaner.addToFleet(snowShovel1, pos);
        return true;
    }
}
