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

    /**
     * Teljes konstruktor XML parserhez: destination és isCrashed státusz megadható.
     */
    public Car(String name, Tile position, List<Tile> route, Tile destination, boolean isCrashed){
        super(name, position, route);
        this.isCrashed = isCrashed;
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

    public Tile getCurrentDestination(){
        if(landMarks != null && !landMarks.isEmpty()){
            return landMarks.get(currentDestinationIndex);
        }
        return null;
    }

    public Tile getPosition(){
        return position;
    }

    public List<Tile> getLandMarks(){
        return landMarks;
    }
}