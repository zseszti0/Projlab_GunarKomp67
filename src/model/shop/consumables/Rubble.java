package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.CobblestoneHead;
import model.shop.attachements.SalterHead;

/**
 * Feladata a rendelkezesre allo somennyiseg nyilvantartasa es a felhasznalasi kiserletek validalasa.
 * Biztositja, hogy a soszorofej csak akkor mukodhessen, ha van so az eszkoztarban.
 */
public class Rubble extends Consumable {
    /**
     * Konstruktor a so letrehozasahoz.
     * @param id a so azonositoja
     * @param amount a kezdeti mennyiseg
     */
    public Rubble(final int id, int amount, String name){
        super(id, amount, name);
    }

    /**
     * A so felhasznalasa a soszorofej altal. Levon egy egyseget.
     * @param s a soszorofej, ami felhasznalja
     * @return true, jelezve a sikeres felhasznalast
     */
    public boolean consume(CobblestoneHead s){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Keszletnoveles so eseten.
     * @param r a hozzaadando masik so objektum
     * @return true a sikeres hozzaadas utan
     */
    @Override
    public boolean addAmount(Rubble r){
        if(amount + r.getAmount() >= 0) {
            this.amount = this.getAmount() + r.getAmount();
            return true;
        }
        return false;
    }

    @Override
    public void addToInventory(Inventory inventory){
        inventory.addConsumable(this);
    }
}
