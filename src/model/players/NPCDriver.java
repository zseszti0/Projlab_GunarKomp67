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
     * @param name Az NPC sofőr neve
     * @param pathFinder Az útkereső algoritmus
     */
    public NPCDriver(String name, PathFinder pathFinder){
        super(name);
        this.pathFinder=pathFinder;
    }

    /**
     * Frissíti az NPC sofőr állapotát.
     * Végigmegy az összes autón, meghatározza a következő lépést, és végrehajtja a mozgást. Ütközés esetén lezárja a sávot.
     */
    @Override
    public void update() {
        for (Car c : vehicles) {
            if(!c.isCrashed()) {
                Tile nt = c.nextStep(pathFinder);
                Vehicle collided = c.moveTo(nt);
                if (collided == null) {
                    c.checkDestinationReached();
                } else if (collided != null) {
                    c.getHitByCar();
                    collided.getHitByCar();
                }
            }
        }

        vehicles.removeAll(List.of(vehicles.stream().filter(c -> c.isCrashed()).toArray(Car[]::new)));
    }

    /**
     * Hozzáad egy új autót az NPC sofőr flottájához.
     * @param car A hozzáadandó autó
     */
    public void addCar(Car car){
        vehicles.add(car);
    }
}