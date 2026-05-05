package model.players;

import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.Vehicle;

import java.util.List;

/**
 * Buszsofőr játékos osztály, amely a buszok vezetéséért felelős.
 */
public class BusChaffeur extends AbstractVehicleOwner<Bus> implements IDrivable<Bus> {

    /**
     * Konstruktor, amely létrehoz egy új buszsofőrt a megadott névvel.
     * Lépései:
     * 1. Meghívja az ősosztály (AbstractVehicleOwner) konstruktorát a név beállítására.
     * * @param name A buszsofőr neve
     */
    public BusChaffeur(String name){
        super(name);
    }

    /**
     * Teljes konstruktor buszsofőr példányosításához meglevő flottával.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név és a járműlista beállítására.
     * * @param id A buszsofőr azonosítója.
     * @param fleet A buszsofőrhöz tartozó buszok listája.
     */
    public BusChaffeur(String id, List<Bus> fleet) {
        super(id,fleet);
    }

    /**
     * Vezeti a megadott buszt a kívánt pozícióra.
     * Lépései:
     * 1. Meghívja a busz (vehicle) moveTo() metódusát a célmező (position) átadásával.
     * 2. Ellenőrzi, hogy a mozgás során történt-e ütközés (a moveTo null-t ad vissza, ha tiszta a lépés).
     * 3. Igaz értékkel tér vissza, ha a lépés sikeres és ütközésmentes volt.
     * * @param vehicle A vezetendő busz
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt, false egyébként
     */
    @Override
    public boolean drive(Bus vehicle, Tile position) {
        return vehicle.moveTo(position) == null;
    }

    /**
     * Hozzáad egy új buszt a buszsofőr flottájához.
     * Lépései:
     * 1. A paraméterként kapott buszt hozzáadja a belső (örökölt) vehicles listához.
     * * @param bus A hozzáadandó busz
     */
    public void addBus(Bus bus){
        vehicles.add(bus);
    }
}