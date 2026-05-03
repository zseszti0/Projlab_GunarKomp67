package control.commands;

import control.GameManager;
import model.map.Tile;
import model.players.Cleaner;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Konfigurációs parancs, amely egy új hókotrót hoz létre, és hozzárendeli egy takarítóhoz.
 */
public class SnowShovel extends ConfigCommand {
    /**
     * Létrehozza és elhelyezi a hókotrót.
     * Lépései:
     * 1. Megkeresi a megadott nevű (args[0]) takarítót. Ha nincs meg, kilép.
     * 2. Megkeresi a megadott nevű (args[1]) célmezőt. Ha nincs meg, kilép.
     * 3. Példányosít egy új hókotrót (neve a takarító neve és a járművei számának összefűzése).
     * 4. Hozzáadja a járművet a takarító flottájához, lehelyezve a célmezőre.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a takarító neve, args[1]: a célmező neve.
     * @return true, ha sikeres a hozzáadás, false, ha a takarító vagy a mező nem található.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        Cleaner cleaner = gameManager.getCleaners().stream().filter(b -> b.getName().equals(args.get(0))).findFirst().orElse(null);
        if (cleaner == null)
            return false;
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if (pos == null)
            return false;
        model.vehicles.SnowShovel snowShovel1 = new model.vehicles.SnowShovel(args.get(0) + cleaner.getVehicles().size());
        cleaner.addToFleet(snowShovel1, pos);

        /// /CONSOL OUT
        try {
            output.write(("Egy új hókotró sikeresen hozzáadva a(z)"+ args.get(0) +"nevű játékos flottájához. A jármű a(z) <mező> mezőn kezd.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
