package model.players;

import model.vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Absztrakt osztály, amely a játékosok közös őse, akik járműveket birtokolnak.
 * @param <T> A birtokolt járművek típusa, amelynek a Vehicle leszármazottjának kell lennie
 */
public abstract class AbstractVehicleOwner<T extends Vehicle> {
    /** A játékos neve. */
    protected String name;
    /** A játékos által birtokolt járművek listája. */
    protected List<T> vehicles = new ArrayList<>();

    /**
     * Konstruktor, amely létrehoz egy új járműtulajdonost a megadott névvel.
     * @param name A játékos neve
     */
    public AbstractVehicleOwner(String name){
        this.name=name;
    }

    /**
     * Konstruktor, amely létrehoz egy járműtulajdonost név és flotta alapján (XML parserhez).
     */
    public AbstractVehicleOwner(String name, List<T> vehicles){
        this.name = name;
        if(vehicles != null) this.vehicles = vehicles;
    }

    /**
     * Visszaadja a játékos nevét.
     * @return A játékos neve
     */
    public String getName(){
        return name;
    }

    /**
     * Visszaadja a játékos nevét.
     * @return A játékos járműveit
     */
    public List<T> getVehicles() {
        return vehicles;
    }
}