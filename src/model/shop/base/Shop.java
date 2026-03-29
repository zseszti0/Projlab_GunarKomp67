package model.shop.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A bolt osztaly, amely tarolja a takarito altal megvasarolhato arukat.
 * Felelossege az elerheto termekek katalogusanak fenntartasa.
 * Asszociaciok: StoreListing (az arucikkek factory reprezentacioi).
 */
public class Shop {
    /**
     * A boltban elerheto aruk listajat tarolja nev alapjan.
     */
    private final Map<String, StoreListing> itemRegistry;

    /**
     * Konstruktor, amely letrehozza a bolt katalogusat a megadott nevek, aruk es arak alapjan.
     * @param purchasablesNames az arucikkek neveit tartalmazo lista
     * @param purchasables maguk az arucikkek (Purchasable objektumok)
     * @param purchasablesPrices az arucikkek arai
     * @throws IllegalArgumentException ha a listak meretei nem egyeznek meg
     */
    public Shop(List<String> purchasablesNames, List<Purchasable> purchasables, List<Integer> purchasablesPrices) throws IllegalArgumentException {
        if(purchasablesNames.size() != purchasables.size() || purchasables.size() != purchasablesPrices.size())
            throw new IllegalArgumentException("Nem egyezik meg a nevek, árak és az árucikkek száma!");

        itemRegistry = new HashMap<>();

        for(int i = 0; i < purchasables.size(); i++){
            itemRegistry.put(purchasablesNames.get(i), new StoreListing(purchasablesPrices.get(i), purchasables.get(i)));
        }
    }

    /**
     * Visszaadja a boltban elerheto arucikket a neve alapjan.
     * @param name a keresett arucikk neve
     * @return a nevhez tartozo StoreListing objektum, ami majd legyartja a kert targyat
     */
    public StoreListing getListing(String name){
        return itemRegistry.get(name);
    }
}
