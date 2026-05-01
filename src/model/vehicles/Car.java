package model.vehicles;

import model.map.PathFinder;
import model.map.Tile;

import java.util.List;

/**
 * Autó jármű osztály, amely egy NPC által vezetett járművet reprezentál a játékban.
 */
public class Car extends RoutedVehicle {
    private boolean isCrashed;

    /**
     * Konstruktor, amely létrehoz egy új autót a megadott névvel és pozícióval.
     * @param name Az autó neve
     * @param position A kezdő pozíció mező
     */
    public Car(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }

    public Car(String carId, Tile position, List<Tile> landMarks, Tile destination, boolean isCrashed) {
        super(carId, position, landMarks, destination);
        this.isCrashed = isCrashed;
    }

    @Override
    public void getHitByCar() {
        position.closeLane();
        isCrashed=true;
    }

    public boolean isCrashed() {
        return isCrashed;
    }

    public Tile nextStep(PathFinder pathFinder){
        return pathFinder.findNextStep(position, landMarks.get(currentDestinationIndex));
    }

    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}
}