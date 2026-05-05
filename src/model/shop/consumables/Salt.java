package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.SalterHead;

/**
 * Feladata a rendelkezésre álló sómennyiség nyilvántartása és a felhasználási kísérletek validálása.
 * Biztosítja, hogy a sószórófej csak akkor működhessen, ha van só az eszköztárban.
 */
public class Salt extends Consumable {
    /**
     * Konstruktor a só létrehozásához.
     * Lépései:
     * 1. Meghívja az ősosztály (Consumable) konstruktorát a kezdeti mennyiség és név beállítására.
     *
     * @param amount A kezdeti mennyiség.
     * @param name A só neve.
     */
    public Salt(int amount, String name){
        super(amount, name);
    }

    /**
     * A só felhasználása a sószórófej (SalterHead) által.
     * Lépései:
     * 1. Ellenőrzi, hogy a rendelkezésre álló mennyiség nagyobb-e mint 0.
     * 2. Ha igen, levon egy egységet és igazzal tér vissza.
     * 3. Ha nincs elég só, hamissal tér vissza.
     *
     * @param s A sószórófej, ami felhasználja a sót.
     * @return true, jelezve a sikeres felhasználást.
     */
    public boolean consume(SalterHead s){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Készletnövelés só esetén (pl. vásárláskor).
     * Lépései:
     * 1. Ellenőrzi, hogy a kapott mennyiséggel növelve a készlet érvényes marad-e.
     * 2. Hozzáadja a mennyiséget.
     * 3. Igazzal tér vissza a sikeres összevonás után.
     *
     * @param s A hozzáadandó másik só objektum.
     * @return true a sikeres hozzáadás után, false egyébként.
     */
    @Override
    public boolean addAmount(Salt s){
        if(amount + s.getAmount() >= 0) {
            this.amount = this.getAmount() + s.getAmount();
            return true;
        }
        return false;
    }

    /**
     * Hozzáadja a sót az eszköztárhoz.
     * Lépései:
     * 1. Meghívja az inventory addConsumable metódusát, átadva önmagát.
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
     * 1. Visszatér a "Salt" szöveggel.
     *
     * @return A fogyóeszköz típusa.
     */
    @Override
    public String getType(){
        return "Salt";
    }
}
