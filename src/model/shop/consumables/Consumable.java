package model.shop.consumables;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.vehicles.SnowShovel;

/**
 * Üzemanyagok és egyéb fogyóeszközök absztrakt alaposztálya.
 * Egységes mennyiségi kezelés biztosítását és a takarítófejekkel való interakció (Látogató minta) definiálását végzi.
 */
abstract public class Consumable implements Purchasable {
    /**
     * A rendelkezésre álló egységek száma.
     */
    protected int amount;

    /**
     * A fogyóeszköz azonosító neve.
     */
    private final String name;

    /**
     * Konstruktor a fogyóeszközökhöz.
     * Lépései:
     * 1. Beállítja az induló mennyiséget.
     * 2. Beállítja az azonosító nevet.
     *
     * @param amount A kezdeti mennyiség.
     * @param name A fogyóeszköz neve.
     */
    public Consumable(int amount, String name) {
        this.amount = amount;
        this.name = name;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására söprőfejjel (Látogató minta része).
     * Lépései:
     * 1. Mivel a söprőfej nem fogyaszt anyagot, alapértelmezetten mindig true értékkel tér vissza.
     *
     * @param a A söprőfej, ami a fogyóeszközt ellenőrzi.
     * @return true alapértelmezetten.
     */
    public boolean consume(SweeperHead a){
        return true;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására hóhányófejjel.
     * Lépései:
     * 1. Mivel a hóhányófej nem fogyaszt anyagot, mindig true értékkel tér vissza.
     *
     * @param a A hóhányófej.
     * @return true alapértelmezetten.
     */
    public boolean consume(BlowerHead a){
        return true;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására jégtörő fejjel.
     * Lépései:
     * 1. Mivel a jégtörő fej nem fogyaszt anyagot, mindig true értékkel tér vissza.
     *
     * @param a A jégtörő fej.
     * @return true alapértelmezetten.
     */
    public boolean consume(IcebreakerHead a){
        return true;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására kőzúzalékszóró fejjel.
     * Lépései:
     * 1. Az alapértelmezett visszatérés true (a Rubble leszármazottban van felülírva a tényleges fogyasztás).
     *
     * @param a A kőzúzalékszóró fej.
     * @return true alapértelmezetten.
     */
    public boolean consume(CobblestoneHead a){
        return true;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására sószóró fejjel.
     * Lépései:
     * 1. Az alapértelmezett visszatérés true (a Salt leszármazottban van felülírva a tényleges fogyasztás).
     *
     * @param a A sószóró fej.
     * @return true alapértelmezetten.
     */
    public boolean consume(SalterHead a){
        return true;
    }

    /**
     * Alapértelmezett metódus a fogyóeszköz aktiválására sárkányfejjel.
     * Lépései:
     * 1. Az alapértelmezett visszatérés true (a Biokerosene leszármazottban van felülírva a tényleges fogyasztás).
     *
     * @param a A sárkányfej.
     * @return true alapértelmezetten.
     */
    public boolean consume(DragonHead a){
        return true;
    }


    /**
     * Típus-specifikus készletnövelés só esetén.
     * Lépései:
     * 1. Alapértelmezetten false-t ad vissza, mivel ez nem só. A Salt osztály felülírja.
     *
     * @param s A hozzáadandó só.
     * @return true, ha sikeres volt az összevonás, egyébként false.
     */
    public boolean addAmount(Salt s){
        return false;
    }

    /**
     * Tipus-specifikus keszletnoveles so eseten. Leszarmazottak felulirjak.
     * @param r a hozzaadando so
     * @return true ha sikeres, maskulonben false
     */
    public boolean addAmount(Rubble r){
        return false;
    }

    /**
     * Típus-specifikus készletnövelés biokerozin esetén.
     * Lépései:
     * 1. Alapértelmezetten false-t ad vissza. A Biokerosene osztály felülírja.
     *
     * @param b A hozzáadandó biokerozin.
     * @return true, ha sikeres volt az összevonás, egyébként false.
     */
    public boolean addAmount(Biokerosene b){
        return false;
    }

    /**
     * Visszaadja a rendelkezésre álló egységek számát.
     * Lépései:
     * 1. Visszatér az amount attribútum értékével.
     *
     * @return A fogyóeszköz mennyisége.
     */
    public int getAmount(){
        return amount;
    }

    /**
     * Visszaadja a fogyóeszköz nevét.
     * Lépései:
     * 1. Visszatér a név attribútummal.
     *
     * @return A fogyóeszköz neve.
     */
    public String getName(){
        return name;
    }

    /**
     * Absztrakt metódus a fogyóeszköz eszköztárba (Inventory) való felvételére.
     *
     * @param inventory A cél eszköztár.
     */
    public abstract void addToInventory(Inventory inventory);

    /**
     * Absztrakt metódus a fogyóeszköz típusának stringként való lekérdezésére.
     *
     * @return A típus string reprezentációja.
     */
    public abstract String getType();

    /**
     * A Purchasable interfészből származó metódus, amely járművek esetén a pályára helyezést intézi.
     * Lépései:
     * 1. Fogyóeszközök esetén a pálya helyett az eszköztárba kerülnek, így itt null-t ad vissza.
     *
     * @param tile A célmező.
     * @return Mindig null.
     */
    public SnowShovel addToBoard(Tile tile){return null;}
}
