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
     * Konstruktor a zuzalekhoz.
     * @param amount a kezdeti mennyiseg
     * @param name a zuzalek neve
     */
    public Rubble(int amount, String name){
        super(amount, name);
    }

    /**
     * A zuzalek felhasznalasa a zuzalekszoro fejjel. Levon egy egyseget.
     * @param s a zuzalekszoro fej, ami felhasznalja
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
     * Keszletnoveles zuzalek eseten.
     * @param r a hozzaadando masik zuzalek objektum
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

    @Override
    public String getType(){
        return "Rubble";
    }
}
