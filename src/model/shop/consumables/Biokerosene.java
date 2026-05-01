package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.DragonHead;

/**
 * A rendelkezesre allo biokerozinmennyiseg nyilvantartasa es a felhasznalasi kiserletek validalasa.
 * Biztositja, hogy a sarkanyfej csak akkor fejthesse ki hatasat, ha van elerheto biokerozin.
 */
public class Biokerosene extends Consumable{
    /**
     * Konstruktor a biokerozin letrehozasahoz.
     * @param amount a kezdeti mennyiseg
     * @param name a biokerozin neve
     */
    public Biokerosene(int amount, String name){
        super(amount, name);
    }

    /**
     * A biokerozin felhasznalasa a sarkanyfej altal. Levon egy egyseget.
     * @param d a sarkanyfej, ami felhasznalja
     * @return true, jelezve a sikeres felhasznalast
     */
    public boolean consume(DragonHead d){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Keszletnoveles biokerozin eseten.
     * @param b a hozzaadando masik biokerozin objektum
     * @return true a sikeres hozzaadas utan
     */
    @Override
    public boolean addAmount(Biokerosene b){
        if(amount + b.getAmount() >= 0) {
            this.amount = this.getAmount() + b.getAmount();
            return true;
        }
        return false;
    }

    @Override
    public void addToInventory(Inventory inventory){
        inventory.addConsumable(this);
    }

    @Override
    public String getType(){
        return "Biokerosene";
    }
}
