package model.players;

import model.map.PathFinder;
import model.map.Tile;
import model.vehicles.Car;
import model.vehicles.Vehicle;

public class NPCDriver extends AbstractVehicleOwner<Car> implements IAutomatic {

    private PathFinder pathFinder;

    public NPCDriver(String name,PathFinder pathFinder){
        super(name);
        this.pathFinder=pathFinder;
    }

    @Override
    public void update() {
        for (Car c : vehicles) {
            Tile nt = pathFinder.findNextStep(c.getPosition(), null);

            if (nt != null) {
                Vehicle collided = c.moveTo(nt);
                if (collided != null) {
                    nt.closeLane();
                    collided.getHitByCar();
                }
            }
        }
    }
}