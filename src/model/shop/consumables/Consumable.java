package model.shop.consumables;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.vehicles.SnowShovel;

/**
 * Uzemanyagok es egyeb fogyoeszkozok absztrakt alaposztalya.
 * Egyseges mennyisegi kezeles biztositasat es a takaritofejekkel valo interakcio definialasat vegzi.
 */
abstract public class Consumable implements Purchasable {
    /**
     * A rendelkezesre allo egysegek szama.
     */
    protected int amount;

    /**
     * A fogyoeszkoz neve.
     */
    private final String name;


    /**
     * Konstruktor a fogyoeszkozokhoz.
     * @param id azonosito
     * @param amount a kezdeti mennyiseg
     * @param name a fogyoeszkoz neve
     */
    public Consumable(final int id, int amount, String name) {
        this.amount = amount;
        this.name = name;
    }


    /**
     * Absztraktnak szant metodus az uzemanyag aktivalasara Söprő fejjel (latogato minta resze).
     * @param a a fej, ami a fogyoeszkzozt hasznalja
     * @return true alapertelmezetten
     */
    public boolean consume(SweeperHead a){
        return true;
    }
    /**
     * Absztraktnak szant metodus az uzemanyag aktivalasara Hányó fejjel (latogato minta resze).
     * @param a a fej, ami a fogyoeszkzozt hasznalja
     * @return true alapertelmezetten
     */
    public boolean consume(BlowerHead a){
        return true;
    }
    /**
     * Absztraktnak szant metodus az uzemanyag aktivalasara Jlgtörő fejjel (latogato minta resze).
     * @param a a fej, ami a fogyoeszkzozt hasznalja
     * @return true alapertelmezetten
     */
    public boolean consume(IcebreakerHead a){
        return true;
    }
    /**
     * Absztraktnak szant metodus az uzemanyag aktivalasara Sószóró fejjel (latogato minta resze).
     * @param a a fej, ami a fogyoeszkzozt hasznalja
     * @return true alapertelmezetten
     */
    public boolean consume(SalterHead a){
        return true;
    }
    /**
     * Absztraktnak szant metodus az uzemanyag aktivalasara Sárkány fejjel (latogato minta resze).
     * @param a a fej, ami a fogyoeszkzozt hasznalja
     * @return true alapertelmezetten
     */
    public boolean consume(DragonHead a){
        return true;
    }


    /**
     * Tipus-specifikus keszletnoveles so eseten. Leszarmazottak felulirjak.
     * @param s a hozzaadando so
     * @return true ha sikeres, maskulonben false
     */
    public boolean addAmount(Salt s){
        return false;
    }

    /**
     * Tipus-specifikus keszletnoveles biokerozin eseten. Leszarmazottak felulirjak.
     * @param b a hozzaadando biokerozin
     * @return true ha sikeres, maskulonben false
     */
    public boolean addAmount(Biokerosene b){
        return false;
    }

    /**
     * Visszaadja a rendelkezesre allo egysegek szamat.
     * @return az amount erteke
     */
    public int getAmount(){
        return amount;
    }

    /**
     * Visszaadja a fogyoeszkoz nevet.
     * @return a name attributum
     */
    public String getName(){
        return name;
    }

    public abstract void addToInventory(Inventory inventory);

    public SnowShovel addToBoard(Tile tile){return null;}
}
