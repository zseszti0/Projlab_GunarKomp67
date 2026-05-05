package model.vehicles;

import model.map.Tile;

import java.util.List;

/**
 * Busz jármű osztály, amelyet a buszsofőr játékos irányít.
 */
public class Bus extends RoutedVehicle {
    /** Jelzi, ha a busz elakadt/megállt valamilyen esemény miatt. */
    boolean isStunned;

    /**
     * Konstruktor új buszhoz.
     * Lépései:
     * 1. Meghívja az ősosztály (RoutedVehicle) konstruktorát a név, pozíció és útvonal beállításához.
     *
     * @param name Név.
     * @param position Kezdőhely.
     * @param route Útvonal.
     */
    public Bus(String name, Tile position, List<Tile> route){
        super(name,position, route);
    }

    /**
     * Teljes konstruktor XML parserhez: destination és isStunned státusz megadható.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát az alapvető adatok beállításához.
     * 2. Beállítja az isStunned attribútumot a paraméter alapján.
     * 3. Ha a célmező (destination) nem null és van útvonal, megkeresi annak indexét az útvonalban.
     * 4. Beállítja a megtalált indexet aktuális célként (currentDestinationIndex).
     *
     * @param name A busz neve.
     * @param position A kezdő pozíció mező.
     * @param route A busz útvonalának pontjai.
     * @param destination A jelenlegi célmező.
     * @param isStunned A busz bénultsági állapota.
     */
    public Bus(String name, Tile position, List<Tile> route, Tile destination, boolean isStunned){
        super(name, position, route);
        this.isStunned = isStunned;
        if(destination != null && !route.isEmpty()){
            // Meghatározza az index-et a destination alapján
            for(int i = 0; i < route.size(); i++){
                if(route.get(i).equals(destination)){
                    this.currentDestinationIndex = i;
                    break;
                }
            }
        }
    }

    /**
     * Kezeli a busszal történő ütközést.
     * Lépései:
     * 1. Ütközés esetén a buszt lebénítja (isStunned = true).
     */
    @Override
    public void getHitByCar() {
        isStunned = true;
    }

    /**
     * Átmozgatja a buszt a megadott célmezőre.
     * Lépései:
     * 1. Ellenőrzi, hogy a busz le van-e bénulva. Ha igen, feloldja a bénulást és megszakítja a mozgást (null értékkel tér vissza).
     * 2. Ha nincs lebénulva, meghívja az ősosztály (Vehicle) mozgás logikáját.
     * 3. Ellenőrzi, hogy a mozgás során elérte-e az úticélját a checkDestinationReached() segítségével.
     * 4. Visszatér a mozgás során esetlegesen elütött járművel.
     *
     * @param target A célmező, ahova a busz mozogni próbál.
     * @return Az ütközött jármű referenciája, vagy null, ha a mozgás sikeres és ütközésmentes volt.
     */
    @Override
    public Vehicle moveTo(Tile target) {
        if(isStunned){
            isStunned=false;
            return null;
        }
        Vehicle crashed = super.moveTo(target);
        checkDestinationReached();
        return crashed;
    }

    /**
     * Megpróbál rálépni a megadott mezőre.
     * Lépései:
     * 1. Meghívja a célmező acceptVehicle metódusát, átadva saját magát.
     *
     * @param tile A célmező.
     * @return true, ha a mező befogadta a buszt, egyébként false.
     */
    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}

    /**
     * Visszaadja a busz bénultsági állapotát.
     * * @return true, ha a busz le van bénulva, egyébként false.
     */
    public boolean isStunned(){
        return isStunned;
    }

    /**
     * Lekérdezi a busz aktuális célmezőjét az útvonalából.
     * Lépései:
     * 1. Ellenőrzi, hogy az útvonal (landMarks) létezik-e és nem üres-e.
     * 2. Ha van útvonal, visszaadja az aktuális indexen lévő mezőt.
     *
     * @return Az aktuális célmező, vagy null, ha nincs útvonal megadva.
     */
    public Tile getCurrentDestination(){
        if(landMarks != null && !landMarks.isEmpty()){
            return landMarks.get(currentDestinationIndex);
        }
        return null;
    }

    /**
     * Visszaadja a busz jelenlegi pozícióját.
     *
     * @return A mező, ahol a busz tartózkodik.
     */
    public Tile getPosition(){
        return position;
    }
}