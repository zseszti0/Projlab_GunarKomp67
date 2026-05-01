package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.SalterHead;

/**
 * Feladata a rendelkezesre allo somennyiseg nyilvantartasa es a felhasznalasi kiserletek validalasa.
 * Biztositja, hogy a soszorofej csak akkor mukodhessen, ha van so az eszkoztarban.
 */
public class Salt extends Consumable {
    /**
     * Konstruktor a so letrehozasahoz.
     * @param amount a kezdeti mennyiseg
     * @param name a so neve
     */
    public Salt(int amount, String name){
        super(amount, name);
    }

    /**
     * A so felhasznalasa a soszorofej altal. Levon egy egyseget.
     * @param s a soszorofej, ami felhasznalja
     * @return true, jelezve a sikeres felhasznalast
     */
    public boolean consume(SalterHead s){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Keszletnoveles so eseten.
     * @param s a hozzaadando masik so objektum
     * @return true a sikeres hozzaadas utan
     */
    @Override
    public boolean addAmount(Salt s){
        if(amount + s.getAmount() >= 0) {
            this.amount = this.getAmount() + s.getAmount();
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
        return "Salt";
    }
}
