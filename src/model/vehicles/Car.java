package model.vehicles;

import model.map.Tile;

import java.util.List;

/**
 * Autó jármű osztály, amely egy NPC által vezetett járművet reprezentál a játékban.
 */
public class Car extends RoutedVehicle {
    boolean slipping;

    /**
     * Konstruktor, amely létrehoz egy új autót a megadott névvel és pozícióval.
     * @param name Az autó neve
     * @param position A kezdő pozíció mező
     */
    public Car(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }

    /**
     * Visszaadja az autó aktuális pozícióját.
     * @return A mező, ahol az autó jelenleg tartózkodik
     */
    public Tile getPosition(){
        return position;
    }

    @Override
    public void getHitByCar() {

    }
}