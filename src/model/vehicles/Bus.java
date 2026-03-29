package model.vehicles;

import model.map.Tile;

public class Bus extends RoutedVehicle {

    public Bus(String name, Tile position){
        super(name,position);
    }

    @Override
    public void getHitByCar() {

    }

    public boolean isStunned(){
        return skeleton.Skeleton.askBoolQuestion("Le van bénulva a busz?");    }
}