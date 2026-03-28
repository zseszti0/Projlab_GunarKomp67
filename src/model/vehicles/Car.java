package model.vehicles;

import model.map.Tile;

public class Car extends RoutedVehicle {

    public Car(String name, Tile position){
        super(name,position);
    }

    public Tile getPosition(){
        return position;
    }

    @Override
    public void getHitByCar() {
    }
}