package model.vehicles;

import model.map.Tile;
import java.util.List;

public abstract class RoutedVehicle extends Vehicle {

    protected List<Tile> landMarks;

    public RoutedVehicle(String name,Tile position){
        super(name,position);
    }
}