package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.SalterHead;
import skeleton.Skeleton;

/**
 * Feladata a rendelkezesre allo somennyiseg nyilvantartasa es a felhasznalasi kiserletek validalasa.
 * Biztositja, hogy a soszorofej csak akkor mukodhessen, ha van so az eszkoztarban.
 */
public class Salt extends Consumable {
    /**
     * Konstruktor a so letrehozasahoz.
     * @param id a so azonositoja
     * @param amount a kezdeti mennyiseg
     */
    public Salt(final int id, int amount, String name){
        super(id, amount, name);
    }

    /**
     * A so felhasznalasa a soszorofej altal. Levon egy egyseget.
     * @param s a soszorofej, ami felhasznalja
     * @return true, jelezve a sikeres felhasznalast
     */
    public boolean consume(SalterHead s){
        boolean answer = Skeleton.askBoolQuestion("Van-e elég só?");
        if(!answer) return false;
        amount--;
        return true;
    }

    /**
     * Keszletnoveles so eseten.
     * @param s a hozzaadando masik so objektum
     * @return true a sikeres hozzaadas utan
     */
    @Override
    public boolean addAmount(Salt s){
        this.amount = this.getAmount() + s.getAmount();
        return true;
    }

    @Override
    public void addToInventory(Inventory inventory){
        inventory.addConsumable(this);
    }
}
