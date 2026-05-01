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

    /**
     * Teljes konstruktor XML parserhez: destination és isStunned státusz megadható.
     */
    public Bus(String name, Tile position, List<Tile> route, Tile destination, boolean isStunned){
        super(name, position, route);
        this.isStunned = isStunned;
        if(destination != null && !route.isEmpty()){
            // Meghatározza az index-et a destination alapján
            for(int i = 0; i < route.size(); i++){
                if(route.get(i).equals(destination)){
                    this.currentDestinationIndex = i;
                    break;
                }
            }
        }
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

    public boolean isStunned(){
        return isStunned;
    }

    public Tile getCurrentDestination(){
        if(landMarks != null && !landMarks.isEmpty()){
            return landMarks.get(currentDestinationIndex);
        }
        return null;
    }

    public Tile getPosition(){
        return position;
    }
}