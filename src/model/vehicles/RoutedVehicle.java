package model.vehicles;

import model.map.Tile;
import java.util.List;

/**
 * Absztrakt osztály, amely az útvonalhoz kötött járművek közös őse.
 * Ilyen járművek például az autók és buszok, amelyeknek előre meghatározott útvonaluk van a pályán.
 */
public abstract class RoutedVehicle extends Vehicle {

    /** A jármű útvonalának céljait tartalmazó lista. */
    protected List<Tile> landMarks;
    protected int currentDestinationIndex = 0;

    /**
     * Konstruktor, amely létrehoz egy új útvonalhoz kötött járművet.
     * @param name A jármű neve
     * @param position A kezdő pozíció mező
     */
    public RoutedVehicle(String name,Tile position, List<Tile> landMarks) {
        super(name,position);
        this.landMarks = landMarks;

    }

    public void checkDestinationReached(){
        if(position.equals(landMarks.get(currentDestinationIndex))){
            currentDestinationIndex = (currentDestinationIndex+1)%landMarks.size();
        }
    }
}