package model.vehicles;

import model.map.Tile;
import model.map.tilestates.TileState;

/**
 * A játékban szereplő összes jármű közös absztrakt ősosztálya.
 * Felelőssége a járművek alapvető tulajdonságainak (név, pozíció) tárolása
 * és az alapvető mozgási mechanika biztosítása.
 */
public abstract class Vehicle {
    /** A jármű neve. */
    protected String name;

    /** A mező, ahol a jármű jelenleg tartózkodik. */
    protected Tile position;

    /**
     * Konstruktor a jármű létrehozásához.
     * Lépései:
     * 1. Beállítja a jármű nevét.
     * 2. Beállítja a kezdeti pozícióját.
     *
     * @param name A jármű neve.
     * @param position A mező, ahol a jármű kezd.
     */
    public Vehicle(String name,Tile position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Egyszerűsített konstruktor, csak név beállításához.
     * Lépései:
     * 1. Beállítja a jármű nevét a kapott paraméter alapján.
     *
     * @param s A jármű neve.
     */
    public Vehicle(String s) {
        this.name = s;
    }

    /**
     * Visszaadja a jármű nevét.
     *
     * @return A jármű azonosítója.
     */
    public String getName() {
        return name;
    }

    /**
     * Átmozgatja a járművet egy szomszédos mezőre.
     * Lépései:
     * 1. Meghívja az aktuális pozíció (mező) moveToNeighbor metódusát a célmezővel és önmagával.
     * 2. A mező ellenőrzi a szomszédságot és a célmező fogadókészségét.
     * 3. Ha a mozgás sikeres (nem történt ütközés), frissíti az aktuális pozíciót a célmezőre.
     * 4. Visszatér null-al ha sikeres volt, vagy az ütközött járművel, ha volt akadály.
     *
     * @param target A szomszédos célmező.
     * @return Az ütközött jármű referenciája, vagy null, ha a mozgás sikeres és ütközésmentes volt.
     */
    public Vehicle moveTo(Tile target) {
        Vehicle collided = position.moveToNeighbor(target, this);
        if(collided == null){
            position = target;
            return null;
        }
        return collided;
    }

    /**
     * Virtuális metódus autóval való ütközés lekezelésére.
     * Alapértelmezésben nem történik semmi, a leszármazottak (pl. Autó, Busz) felülírják a specifikus következményekhez.
     */
    public abstract void getHitByCar();

    /**
     * Absztrakt metódus, amely kezeli a jármű megadott mezőre való belépését.
     * A konkrét járműtípusok (Busz, Autó, Hókotró) delegálják a hívást a mező állapotának.
     *
     * @param tile A célmező.
     * @return true, ha a jármű ráléphet a mezőre, false egyébként.
     */
    public abstract boolean goToTile(Tile tile);

}