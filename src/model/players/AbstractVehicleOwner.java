package model.players;

import model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVehicleOwner<T extends Vehicle> {
    protected String name;
    protected List<T> vehicles=new ArrayList<>();

    public AbstractVehicleOwner(String name){
        this.name=name;
    }

    public String getName(){
        return name;
    }
}