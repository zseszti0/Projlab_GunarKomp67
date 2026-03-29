package model.vehicles;

import model.map.Tile;

/**
 * Autó jármű osztály, amely egy NPC által vezetett járművet reprezentál a játékban.
 */
public class Car extends RoutedVehicle {

    /**
     * Konstruktor, amely létrehoz egy új autót a megadott névvel és pozícióval.
     * @param name Az autó neve
     * @param position A kezdő pozíció mező
     */
    public Car(String name, Tile position){
        super(name,position);
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