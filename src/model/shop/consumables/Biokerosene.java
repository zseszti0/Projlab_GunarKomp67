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
     * @param id a biokerozin azonositoja
     * @param amount a kezdeti mennyiseg
     */
    public Biokerosene(final int id, int amount, String name){
        super(id, amount,  name);
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
     * Keszletnoveles so eseten.
     * @param b a hozzaadando masik so objektum
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
}
