package model.shop.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * A bolt osztály, amely tárolja a takarító által megvásárolható árukat.
 * Felelőssége az elérhető termékek katalógusának fenntartása és a vásárlási folyamat kiszolgálása.
 * Asszociációk: StoreListing (az árucikkek factory reprezentációi).
 */
public class Shop {
    /**
     * A boltban elérhető áruk listáját tárolja név alapján.
     */
    private final Map<String, StoreListing> itemRegistry;

    /**
     * Konstruktor, amely létrehozza a bolt katalógusát a megadott nevek, áruk és árak alapján.
     * Lépései:
     * 1. Ellenőrzi, hogy a kapott három lista (nevek, factory-k, árak) mérete megegyezik-e.
     * 2. Ha nem egyezik, kivételt dob (IllegalArgumentException).
     * 3. Példányosítja az itemRegistry szótárat (HashMap).
     * 4. Végigiterál a listákon, és minden elemből létrehoz egy StoreListing objektumot,
     * majd a névvel mint kulccsal elmenti azt a szótárba.
     *
     * @param purchasablesNames Az árucikkek neveit tartalmazó lista.
     * @param purchasables Maguk az árucikkek legyártásáért felelős Supplier objektumok (factory-k).
     * @param purchasablesPrices Az árucikkek árai.
     * @throws IllegalArgumentException ha a listák méretei nem egyeznek meg.
     */
    public Shop(List<String> purchasablesNames, List<Supplier<Purchasable>> purchasables, List<Integer> purchasablesPrices) throws IllegalArgumentException {
        if(purchasablesNames.size() != purchasables.size() || purchasables.size() != purchasablesPrices.size())
            throw new IllegalArgumentException("Nem egyezik meg a nevek, árak és az árucikkek száma!");

        itemRegistry = new HashMap<>();

        for(int i = 0; i < purchasables.size(); i++){
            itemRegistry.put(purchasablesNames.get(i), new StoreListing(purchasablesPrices.get(i), purchasables.get(i)));
        }
    }

    /**
     * Visszaadja a boltban elérhető árucikket a neve alapján.
     * Lépései:
     * 1. Kikeresi a belső szótárból (itemRegistry) az adott névhez tartozó bolti bejegyzést (StoreListing).
     * 2. Visszatér ezzel az objektummal, ami tartalmazza az árat és a gyártási metódust.
     *
     * @param name A keresett árucikk neve.
     * @return A névhez tartozó StoreListing objektum, vagy null, ha a boltban nem található ilyen nevű árucikk.
     */
    public StoreListing getListing(String name){
        return itemRegistry.get(name);
    }
}
