package model.players;

import vehicles.Vehicle;

public interface IDrivable<T extends Vehicle> {
    boolean drive(T vehicle, Tile position);
}