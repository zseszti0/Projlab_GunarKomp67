package model.vehicles;

import model.map.Tile;

import java.util.List;

/**
 * Busz jármű osztály, amelyet a buszsofőr játékos irányít.
 */
public class Bus extends RoutedVehicle {

    /**
     * Konstruktor, amely létrehoz egy új buszt a megadott névvel és pozícióval.
     * @param name A busz neve
     * @param position A kezdő pozíció mező
     */
    public Bus(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }
    /**
     * Kezeli a busszal történő ütközést.
     * Ütközés esetén a busz megbénulhat.
     */
    @Override
    public void getHitByCar() {
        this.isStunned();
    }

    /**
     * Ellenőrzi, hogy a busz le van-e bénulva.
     * A szkeleton módban felhasználói bevitelt kér.
     * @return true, ha a busz le van bénulva
     */
    public boolean isStunned(){
        return skeleton.Skeleton.askBoolQuestion("Le van bénulva a busz?");    }
}