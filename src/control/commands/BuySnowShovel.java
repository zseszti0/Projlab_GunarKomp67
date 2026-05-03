package control.commands;

import control.GameManager;
import model.map.Tile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BuySnowShovel extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(0))).findFirst().orElse(null);
        if(pos == null) return false;

        boolean success = gameManager.orderSnowShovel(pos);
        if(success){
            /// /CONSOL OUT
            try {
                output.write(("A vásárlás sikeres. Egy új hókotró sikeresen lehelyezve a(z)"+ args.get(0) +"mezőre.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            /// /CONSOL OUT
            try {
                output.write((" A vásárlás sikertelen, nincs elég tőke.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return success;
    }
}
