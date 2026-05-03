package control.commands;

import control.GameManager;
import model.map.Tile;
import model.players.BusChaffeur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Bus extends ConfigCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        BusChaffeur busChaffeur = gameManager.getBusChauffeurs().stream().filter(b -> b.getName().equals(args.get(0))).findFirst().orElse(null);
        if (busChaffeur == null)
            return false;
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        busChaffeur.addBus(new model.vehicles.Bus(args.get(0) + busChaffeur.getVehicles().size(),pos ,null));

        /// /CONSOL OUT
        try {
            output.write(("Egy új busz sikeresen hozzáadva a(z)"+args.get(0)+" nevű játékos flottájához. A jármű a(z) <mező> mezőn kezd.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
