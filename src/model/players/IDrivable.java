package model.players;

import model.map.Tile;
import model.vehicles.Vehicle;

public interface IDrivable<T extends Vehicle> {
    boolean drive(T vehicle, Tile position);
}