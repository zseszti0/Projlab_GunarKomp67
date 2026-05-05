package model.vehicles;

import model.map.PathFinder;
import model.map.Tile;

import java.util.List;

/**
 * Autó jármű osztály, amely egy NPC által vezetett járművet reprezentál a játékban.
 */
public class Car extends RoutedVehicle {
    /**
     * Jelzi, hogy az autó karambolozott-e.
     */
    private boolean isCrashed;

    /**
     * Konstruktor, amely létrehoz egy új autót a megadott névvel, pozícióval és útvonallal.
     * Lépései:
     * 1. Meghívja az ősosztály (RoutedVehicle) konstruktorát a paraméterek beállításához.
     *
     * @param name Az autó neve
     * @param position A kezdő pozíció mező
     * @param route Az autó által követendő útvonal.
     */
    public Car(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }

    /**
     * Teljes konstruktor XML parserhez: destination és isCrashed státusz megadható.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát az alap adatok beállításához.
     * 2. Beállítja az autó törött állapotát (isCrashed).
     * 3. Ha van megadott cél és útvonal, megkeresi a célmező indexét az útvonalban és beállítja aktuális úticélnak.
     *
     * @param name Az autó neve.
     * @param position A kezdő pozíció mező.
     * @param route Az útvonal pontjai.
     * @param destination Az aktuális célmező.
     * @param isCrashed Az autó állapota (törött-e).
     */
    public Car(String name, Tile position, List<Tile> route, Tile destination, boolean isCrashed){
        super(name, position, route);
        this.isCrashed = isCrashed;
        if(destination != null && !route.isEmpty()){
            for(int i = 0; i < route.size(); i++){
                if(route.get(i).equals(destination)){
                    this.currentDestinationIndex = i;
                    break;
                }
            }
        }
    }

    /**
     * Kezeli az autóval történő ütközést.
     * Lépései:
     * 1. Lezárja a sávot (closeLane) azon a mezőn, ahol az autó éppen áll.
     * 2. Beállítja az autót törött állapotúra (isCrashed = true).
     */
    @Override
    public void getHitByCar() {
        position.closeLane();
        isCrashed=true;
    }

    /**
     * Visszaadja az autó állapotát.
     *
     * @return true, ha az autó összetört, egyébként false.
     */
    public boolean isCrashed() {
        return isCrashed;
    }

    /**
     * Kiszámolja és visszaadja az autó következő lépését az aktuális úticél felé.
     * Lépései:
     * 1. Meghívja a PathFinder findNextStep metódusát a jelenlegi pozícióval és az aktuális célmezővel.
     *
     * @param pathFinder Az útvonalkeresésért felelős objektum.
     * @return A következő mező, amire az autónak lépnie kell.
     */
    public Tile nextStep(PathFinder pathFinder){
        return pathFinder.findNextStep(position, landMarks.get(currentDestinationIndex));
    }

    /**
     * Megpróbál rálépni a megadott mezőre.
     * Lépései:
     * 1. Meghívja a célmező acceptVehicle metódusát, átadva magát.
     *
     * @param tile A célmező.
     * @return true, ha a mező befogadta az autót, egyébként false.
     */
    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}

    /**
     * Lekérdezi az autó aktuális úticélját.
     * Lépései:
     * 1. Ellenőrzi, hogy van-e megadott útvonal.
     * 2. Ha igen, visszaadja az aktuális index által kijelölt mezőt.
     *
     * @return Az aktuális célmező, vagy null.
     */
    public Tile getCurrentDestination(){
        if(landMarks != null && !landMarks.isEmpty()){
            return landMarks.get(currentDestinationIndex);
        }
        return null;
    }

    /**
     * Visszaadja az autó aktuális pozícióját.
     *
     * @return A mező, ahol az autó áll.
     */
    public Tile getPosition(){
        return position;
    }

    /**
     * Lekérdezi az autó teljes útvonalát.
     *
     * @return Az útvonal megállóhelyeit tartalmazó lista.
     */
    public List<Tile> getLandMarks(){
        return landMarks;
    }
}