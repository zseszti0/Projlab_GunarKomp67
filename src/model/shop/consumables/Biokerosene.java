package model.shop.consumables;

import model.inventory.Inventory;
import model.shop.attachements.DragonHead;

/**
 * A rendelkezésre álló biokerozinmennyiség nyilvántartása és a felhasználási kísérletek validálása.
 * Biztosítja, hogy a sárkányfej csak akkor fejthesse ki hatását, ha van elérhető biokerozin az eszköztárban.
 */
public class Biokerosene extends Consumable{
    /**
     * Konstruktor a biokerozin létrehozásához.
     * Lépései:
     * 1. Meghívja az ősosztály (Consumable) konstruktorát a kezdőmennyiség és a név beállítására.
     *
     * @param amount A kezdeti mennyiség.
     * @param name A biokerozin neve.
     */
    public Biokerosene(int amount, String name){
        super(amount, name);
    }

    /**
     * A biokerozin felhasználása a sárkányfej (DragonHead) által.
     * Lépései:
     * 1. Ellenőrzi, hogy a rendelkezésre álló mennyiség (amount) nagyobb-e mint 0.
     * 2. Ha igen, levon egy egységet és igazzal tér vissza (sikeres felhasználás).
     * 3. Egyébként hamissal tér vissza, ami jelzi, hogy nincs elég kerozin.
     *
     * @param d A sárkányfej, ami felhasználná az üzemanyagot.
     * @return true, jelezve a sikeres felhasználást, false egyébként.
     */
    public boolean consume(DragonHead d){
        if(amount > 0){
            amount--;
            return true;
        }
        return false;
    }

    /**
     * Készletnövelés biokerozin esetén (pl. vásárláskor).
     * Lépései:
     * 1. Ellenőrzi, hogy a hozzáadás után a mennyiség nem lesz-e negatív.
     * 2. Ha érvényes, hozzáadja a paraméterként kapott objektum mennyiségét a jelenlegihez.
     * 3. Igazzal tér vissza a sikeres hozzáadás után.
     *
     * @param b A hozzáadandó másik biokerozin objektum.
     * @return true a sikeres hozzáadás után, false egyébként.
     */
    @Override
    public boolean addAmount(Biokerosene b){
        if(amount + b.getAmount() >= 0) {
            this.amount = this.getAmount() + b.getAmount();
            return true;
        }
        return false;
    }

    /**
     * Hozzáadja a biokerozint a takarító eszköztárához.
     * Lépései:
     * 1. Meghívja az inventory addConsumable metódusát, átadva önmagát.
     *
     * @param inventory Az eszköztár, amibe bekerül a fogyóeszköz.
     */
    @Override
    public void addToInventory(Inventory inventory){
        inventory.addConsumable(this);
    }

    /**
     * Visszaadja a fogyóeszköz típusát stringként.
     * Lépései:
     * 1. Visszatér a "Biokerosene" szöveggel.
     *
     * @return A fogyóeszköz típusa.
     */
    @Override
    public String getType(){
        return "Biokerosene";
    }
}
