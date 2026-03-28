package model.vehicles;

import java.util.List;

public abstract class RoutedVehicle extends Vehicle {
    protected int currentDestinationIndex;
    protected List<Tile> landMarks;
}