package model.vehicles;

import model.map.Tile;

public abstract class Vehicle {
    protected String name;
    protected Tile position;

    public Vehicle(String name,Tile position) {
        this.name = name;
        this.position=position;
    }

    public String getName() {
        return name;
    }

    public Vehicle moveTo(Tile target) {
        Vehicle collidedVehicle = position.moveToNeighbor(target, this);
        if (collidedVehicle == null) {
            this.position = target;
        }
        return collidedVehicle;
    }

    public abstract void getHitByCar();
}