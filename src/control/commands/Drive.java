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

/**
 * Játékparancs, amellyel egy konkrét járművet (hókotró vagy buszt) egy adott mezőre lehet vezetni.
 */
public class Drive extends GameCommand {
    /**
     * Végrehajtja a vezetés műveletet.
     * Lépései:
     * 1. Megkeresi a célmezőt a második argumentum (args[1]) alapján. Ha nincs ilyen, kilép.
     * 2. Végigkeresi a takarítókat, hogy megtalálja az első argumentumban (args[0]) megadott nevű hókotrót.
     * 3. Ha nem talált hókotrót, végigkeresi a buszsofőröket a megadott nevű buszért.
     * 4. Ha semmilyen járművet nem talált, sikertelenül tér vissza (false).
     * 5. Ha megvan a jármű és a gazdája, meghívja a megfelelő játékos drive metódusát a célmezőre.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a vezetni kívánt jármű neve, args[1]: a célmező neve.
     * @return true, ha a vezetés sikeresen lezajlott, false, ha rossz a mező vagy a jármű azonosítója.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if(pos == null){
            try {
                output.write(("A(z)" + args.get(1) + " számú mező nem létezik\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        boolean success = gameManager.drive(args.get(0), pos);
        if(success) {
            try {
                output.write(("A(z)" + args.get(0) + " Számú hókotró/busz sikeresen átlépett a(z)" + args.get(1) + " mezőre.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                output.write(("A(z)" + args.get(0) + " Számú hókotró/busz nem tudott sikeresen átlépni.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return success;
    }
}
