package control.commands;

import control.GameManager;
import model.map.Tile;

import java.util.List;

public class BuySnowShovel extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if(pos == null) return false;

        return gameManager.orderSnowShovel(pos);
    }
}
