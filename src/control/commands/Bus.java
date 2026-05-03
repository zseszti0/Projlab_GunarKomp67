package control.commands;

import control.GameManager;
import model.map.Tile;
import model.players.BusChaffeur;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Parancs, amely egy új buszt hoz létre és rendel egy létező buszvezetőhöz egy adott mezőn.
 */
public class Bus extends ConfigCommand {
    /**
     * Létrehozza és elhelyezi a buszt.
     * Lépései:
     * 1. Megkeresi a megadott nevű (args[0]) buszvezetőt a GameManager-ben. Ha nincs ilyen, kilép.
     * 2. Megkeresi a játéktér mezői között a célmezőt a megadott név (args[1]) alapján.
     * 3. Példányosít egy új buszt, amelynek neve a vezető nevéből és a már birtokolt járművei számából áll össze.
     * 4. Hozzáadja az új buszt a vezetőhöz, és egyúttal le is helyezi a célmezőre.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a buszvezető neve, args[1]: a célmező neve.
     * @return true, ha a busz hozzáadása sikeres, false, ha a megadott buszvezető nem létezik.
     */
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
