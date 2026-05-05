package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.CobblestoneHead;
import model.shop.attachements.SalterHead;

/**
 * Feladata a rendelkezésre álló kőzúzalék mennyiség nyilvántartása és a felhasználási kísérletek validálása.
 * Biztosítja, hogy a kőzúzalékszóró fej csak akkor működhessen, ha van zúzalék az eszköztárban.
 */
public class Rubble extends Consumable {
    /**
     * Konstruktor a zúzalékhoz.
     * Lépései:
     * 1. Meghívja az ősosztály (Consumable) konstruktorát a kezdeti mennyiség és név beállítására.
     *
     * @param amount A kezdeti mennyiség.
     * @param name A zúzalék neve.
     */
    public Rubble(int amount, String name){
        super(amount, name);
    }

    /**
     * A zúzalék felhasználása a zúzalékszóró fej (CobblestoneHead) által.
     * Lépései:
     * 1. Ellenőrzi, hogy a mennyiség nagyobb-e mint 0.
     * 2. Ha igen, levon egy egységet és igazzal tér vissza.
     * 3. Egyébként hamissal tér vissza.
     *
     * @param s A zúzalékszóró fej, ami felhasználja.
     * @return true, jelezve a sikeres felhasználást.
     */
    public boolean consume(CobblestoneHead s){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Készletnövelés zúzalék esetén (pl. vásárláskor).
     * Lépései:
     * 1. Ellenőrzi a kapott zúzalék mennyiségét.
     * 2. Ha érvényes (nem esik nulla alá az összeg), hozzáadja a jelenlegi készlethez.
     * 3. Igazzal tér vissza a sikeres hozzáadás után.
     *
     * @param r A hozzáadandó másik zúzalék objektum.
     * @return true a sikeres hozzáadás után, false egyébként.
     */
    @Override
    public boolean addAmount(Rubble r){
        if(amount + r.getAmount() >= 0) {
            this.amount = this.getAmount() + r.getAmount();
            return true;
        }
        return false;
    }

    /**
     * Hozzáadja a zúzalékot az eszköztárhoz.
     * Lépései:
     * 1. Meghívja a paraméterként kapott inventory addConsumable metódusát, önmagát átadva.
     *
     * @param inventory Az eszköztár.
     */
    @Override
    public void addToInventory(Inventory inventory){
        inventory.addConsumable(this);
    }

    /**
     * Visszaadja a fogyóeszköz típusát stringként.
     * Lépései:
     * 1. Visszatér a "Rubble" szöveggel.
     *
     * @return A fogyóeszköz típusa.
     */
    @Override
    public String getType(){
        return "Rubble";
    }
}
