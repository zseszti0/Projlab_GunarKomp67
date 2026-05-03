package control.commands;

import control.GameManager;
import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;
import model.players.Cleaner;
import model.players.BusChaffeur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Drive extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if(pos == null){
            /// /CONSOL OUT
            try {
                output.write(("A(z)" + args.get(1) + " számú mező nem létezik\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        boolean success = gameManager.drive(args.get(0), pos);
        if(success) {
            /// /CONSOL OUT
            try {
                output.write(("A(z)" + args.get(0) + " Számú hókotró/busz sikeresen átlépett a(z)" + args.get(1) + " mezőre.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return success;
    }
}
