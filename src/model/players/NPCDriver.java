package model.players;

import model.map.PathFinder;
import model.map.Tile;
import model.vehicles.Car;
import model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * NPC sofőr osztály, amely automatikusan irányítja az autókat a játékban.
 */
public class NPCDriver extends AbstractVehicleOwner<Car> implements IAutomatic {

    /** Az útkereső algoritmus, amely meghatározza a következő lépést. */
    private PathFinder pathFinder;

    /**
     * Konstruktor, amely létrehoz egy új NPC sofőrt a megadott névvel és útkeresővel.
     * Lépései:
     * 1. Meghívja az ősosztályt a név beállítására.
     * 2. Beállítja a PathFinder-t.
     * * @param name Az NPC sofőr neve
     * @param pathFinder Az útkereső algoritmus
     */
    public NPCDriver(String name, PathFinder pathFinder){
        super(name);
        this.pathFinder=pathFinder;
    }

    /**
     * Konstruktor meglévő flotta beállításával.
     * Lépései:
     * 1. Meghívja az ősosztályt az azonosító és a meglévő flotta beállítására.
     * 2. Beállítja az útkeresőt.
     * * @param id Az NPC sofőr azonosítója.
     * @param pathFinder Az útkereső algoritmus.
     * @param fleet Az NPC által vezetett autók listája.
     */
    public NPCDriver(String id, PathFinder pathFinder, List<Car> fleet) {
        super(id,fleet);
        this.pathFinder = pathFinder;
    }

    /**
     * Frissíti az NPC sofőr állapotát egy adott tick (kör) során.
     * Lépései:
     * 1. Végigiterál az NPC összes autóján.
     * 2. Ha az autó nem roncs (nem karambolozott korábban), lekérdezi az útkeresőtől a következő lépést (c.nextStep).
     * 3. Megpróbálja oda mozgatni a járművet (c.moveTo).
     * 4. Ha az autó nem ütközött semmivel (collided == null), akkor ellenőrzi, hogy elérte-e a célállomását.
     * 5. Ha a jármű ütközött egy MÁSIK entitással (collided != c), akkor mindkét járművön meghívja a karambol logikát (getHitByCar).
     * 6. A ciklus lefutása után kigyűjti a roncsokat, és eltávolítja őket az NPC aktív járműlistájából.
     */
    @Override
    public void update() {
        for (Car c : vehicles) {
            if(!c.isCrashed()) {
                Tile nt = c.nextStep(pathFinder);
                Vehicle collided = c.moveTo(nt);
                if (collided == null) {
                    c.checkDestinationReached();
                } else if (collided != c) {
                    c.getHitByCar();
                    collided.getHitByCar();
                }
            }
        }

        vehicles.removeAll(List.of(vehicles.stream().filter(c -> c.isCrashed()).toArray(Car[]::new)));
    }

    /**
     * Hozzáad egy új autót az NPC sofőr flottájához.
     * Lépései:
     * 1. A paraméterként kapott autót hozzáadja az NPC vehicles listájához.
     * * @param car A hozzáadandó autó
     */
    public void addCar(Car car){
        vehicles.add(car);
    }
}