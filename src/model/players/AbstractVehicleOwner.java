package model.players;

import vehicles.Vehicle;
import java.util.List;

public abstract class AbstractVehicleOwner<T extends Vehicle> {
    protected List<T> vehicles;
}