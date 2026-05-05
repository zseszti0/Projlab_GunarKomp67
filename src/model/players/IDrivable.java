package model.players;

import model.map.Tile;
import model.vehicles.Vehicle;

/**
 * Interfész, amely a járművek vezetésének (irányított mozgásának) képességét definiálja.
 * @param <T> A jármű típusa, amelyet vezetni lehet (a Vehicle leszármazottja).
 */
public interface IDrivable<T extends Vehicle> {

    /**
     * Vezeti a megadott járművet a kívánt pozícióra.
     * Lépései:
     * 1. Az implementáció felelős a jármű biztonságos eljuttatásáért a megadott mezőre,
     * és az esetleges ütközések lekezeléséért vagy lejelentéséért.
     * @param vehicle A vezetendő jármű
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt (a jármű eljutott a mezőre ütközés nélkül), false egyébként.
     */
    boolean drive(T vehicle, Tile position);
}