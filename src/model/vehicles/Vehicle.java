package model.vehicles;

import model.map.Tile;
import model.map.tilestates.TileState;

/**
 * Absztrakt osztály, amely az összes jármű közös őse a játékban.
 * Tárolja a jármű nevét és aktuális pozícióját a pályán.
 */
public abstract class Vehicle {

    /** A jármű neve. */
    protected String name;

    /** A mező, ahol a jármű jelenleg tartózkodik. */
    protected Tile position;

    /**
     * Konstruktor, amely létrehoz egy új járművet a megadott névvel és pozícióval.
     * @param name A jármű neve
     * @param position A kezdő pozíció mező
     */
    public Vehicle(String name,Tile position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Visszaadja a jármű nevét.
     * @return A jármű neve
     */
    public String getName() {
        return name;
    }

    /**
     * Elmozdítja a járművet a megadott célmezőre.
     * @param target A célmező, ahová a jármű mozogni szeretne
     * @return A mezőn álló jármű, ha van ütközés, egyébként null
     */
    public Vehicle moveTo(Tile target) {
        Vehicle collided = position.moveToNeighbor(target, this);
        if(collided == null){
            position = target;
            return this;
        }
        return collided;
    }

    public abstract void getHitByCar();

    public abstract boolean goToTile(Tile tile);

}