package model.players;

import model.map.Tile;
import model.vehicles.Vehicle;

/**
 * Interfész, amely a járművek vezetésének képességét definiálja.
 */
public interface IDrivable<T extends Vehicle> {

    /**
     * Vezeti a megadott járművet a kívánt pozícióra.
     * @param vehicle A vezetendő jármű
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt
     */
    boolean drive(T vehicle, Tile position);
}