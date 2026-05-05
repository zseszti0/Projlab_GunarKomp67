package model.vehicles;

import model.map.Tile;
import java.util.List;

/**
 * Absztrakt osztály, amely az útvonalhoz kötött járművek közös őse.
 * Ilyen járművek például az autók és buszok, amelyeknek előre meghatározott útvonaluk van a pályán.
 */
public abstract class RoutedVehicle extends Vehicle {
    /** A jármű útvonalának céljait tartalmazó lista. */
    protected List<Tile> landMarks;
    /** Az aktuális úticél, ami felé a jármű halad. */
    protected int currentDestinationIndex = 0;

    /**
     * Konstruktor útvonallal rendelkező járműhöz.
     * Lépései:
     * 1. Beállítja az alapvető jármű adatokat az ősosztály (Vehicle) meghívásával.
     * 2. Tárolja a megállóhelyek (landMarks) listáját.
     *
     * @param name A jármű neve.
     * @param position Kezdőpozíció.
     * @param landMarks Az útvonal pontjai.
     */
    public RoutedVehicle(String name,Tile position, List<Tile> landMarks) {
        super(name,position);
        this.landMarks = landMarks;
    }

    /**
     * Teljes konstruktor az XML betöltéshez.
     * Lépései:
     * 1. Beállítja az alapvető adatokat az ősosztály konstruktorával.
     * 2. Tárolja a megállóhelyek listáját.
     * 3. Megkeresi és beállítja az aktuális úticél indexét a megadott destination alapján.
     *
     * @param name A jármű neve.
     * @param position Aktuális pozíció.
     * @param landMarks Megállóhelyek.
     * @param destination Aktuális úticél.
     */
    public RoutedVehicle(String name,Tile position, List<Tile> landMarks, Tile destination) {
        super(name,position);
        this.landMarks = landMarks;
        currentDestinationIndex = landMarks.indexOf(destination);
    }

    /**
     * Ellenőrzi, hogy a jármű elérte-e az aktuális úticélját, és ha igen, frissíti azt.
     * Lépései:
     * 1. Megnézi, hogy a jelenlegi pozíció megegyezik-e az aktuális indexen lévő célállomással.
     * 2. Ha igen, a listában a következő megállót állítja be célként (modulo aritmetikával a körkörösségért).
     */
    public void checkDestinationReached(){
        if(position.equals(landMarks.get(currentDestinationIndex))){
            currentDestinationIndex = (currentDestinationIndex+1)%landMarks.size();
        }
    }

    /** * Visszaadja a jármű útvonalpontjait.
     * * @return Az útvonal pontjait tartalmazó lista.
     */
    public List<Tile> getLandMarks() {
        return landMarks;
    }
}