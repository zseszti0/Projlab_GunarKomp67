package model.players;

import model.map.Tile;
import model.vehicles.Bus;

/**
 * Buszsofőr játékos osztály, amely a buszok vezetéséért felelős.
 */
public class BusChaffeur extends AbstractVehicleOwner<Bus> implements IDrivable<Bus> {

    /**
     * Konstruktor, amely létrehoz egy új buszsofőrt a megadott névvel.
     * @param name A buszsofőr neve
     */
    public BusChaffeur(String name){
        super(name);
    }

    /**
     * Vezeti a megadott buszt a kívánt pozícióra.
     * @param vehicle A vezetendő busz
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt
     */
    @Override
    public boolean drive(Bus vehicle, Tile position) {
        vehicle.moveTo(position);
        return true;
    }

    /**
     * Hozzáad egy új buszt a buszsofőr flottájához.
     * @param bus A hozzáadandó busz
     */
    public void addBus(Bus bus){
        vehicles.add(bus);
    }
}