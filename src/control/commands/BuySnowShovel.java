package control.commands;

import control.GameManager;
import model.map.Tile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Játékparancs, amellyel egy takarító (Cleaner) hótolót vásárolhat magának.
 */
public class BuySnowShovel extends GameCommand {
    /**
     * Végrehajtja a hótoló megvásárlását.
     * Lépései:
     * 1. Megkeresi a megadott nevű (args[0]) takarítót a GameManager-ben. Ha nincs ilyen, kilép (false).
     * 2. Meghívja a takarító buySnowShovel() metódusát a tranzakció lebonyolítására.
     * 3. Visszatér a belső vásárlási folyamat eredményével.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a vásárlást indító takarító neve.
     * @return true, ha a takarító megvan és a vásárlás sikeresen lezajlott, egyébként false.
     */
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
