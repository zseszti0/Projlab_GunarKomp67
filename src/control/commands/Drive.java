package control.commands;

import control.GameManager;
import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;
import model.players.Cleaner;
import model.players.BusChaffeur;

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
    public boolean execute(GameManager gameManager, List<String> args) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if(pos == null) return false;

        List<Cleaner> cleaners = gameManager.getCleaners();
        SnowShovel snowShovel = null;
        Cleaner cleaner = null;
        for(Cleaner clnr : cleaners){
            for(SnowShovel ss : clnr.getVehicles()){
                if(ss.getName().equals(args.get(0))) {
                    snowShovel = ss;
                    cleaner = clnr;
                    break;
                }
            }
        }
        if(snowShovel == null){
            List<BusChaffeur> busChauffeurs = gameManager.getBusChauffeurs();
            Bus bus = null;
            BusChaffeur busChauffer = null;
            for(BusChaffeur bsch : busChauffeurs){
                for(Bus b : bsch.getVehicles()){
                    if(b.getName().equals(args.get(0))) {
                        bus = b;
                        busChauffer = bsch;
                        break;
                    }
                }
            }
            if(bus == null) return false;
            return busChauffer.drive(bus,pos);
        }
        else{
            return cleaner.drive(snowShovel,pos);
        }
    }
}
