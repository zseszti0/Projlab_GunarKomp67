package model.players;

import model.map.Tile;
import model.vehicles.Bus;

public class BusChaffeur extends AbstractVehicleOwner<Bus> implements IDrivable<Bus> {

    public BusChaffeur(String name){
        super(name);
    }

    @Override
    public boolean drive(Bus vehicle, Tile position) {
        vehicle.moveTo(position);
        return true;
    }
}