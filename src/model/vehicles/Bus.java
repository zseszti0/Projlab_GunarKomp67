package model.vehicles;

import model.map.Tile;

import java.util.List;

/**
 * Busz jármű osztály, amelyet a buszsofőr játékos irányít.
 */
public class Bus extends RoutedVehicle {
    boolean isStunned;
    /**
     * Konstruktor, amely létrehoz egy új buszt a megadott névvel és pozícióval.
     * @param name A busz neve
     * @param position A kezdő pozíció mező
     */
    public Bus(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }

    public Bus(String busId, Tile position, List<Tile> landMarks, Tile destination, boolean isStunned) {
        super(busId, position, landMarks, destination);
        this.isStunned = isStunned;
    }

    /**
     * Kezeli a busszal történő ütközést.
     * Ütközés esetén a busz megbénulhat.
     */
    @Override
    public void getHitByCar() {
        isStunned = true;
    }

    @Override
    public Vehicle moveTo(Tile target) {
        if(isStunned){
            isStunned=false;
            return null;
        }
        Vehicle crashed = super.moveTo(target);
        checkDestinationReached();
        return crashed;
    }

    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}
}