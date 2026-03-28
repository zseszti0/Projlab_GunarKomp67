package model.shop.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shop {
    //birtokolja a factoryket
    private Map<String, StoreListing> itemRegistry;

    public Shop(List<String> purchasablesNames, List<Purchasable> purchasables, List<Integer> purchasablesPrices) throws IllegalArgumentException {
        if(purchasablesNames.size() != purchasables.size() || purchasables.size() != purchasablesPrices.size())
            throw new IllegalArgumentException("Nem egyenlő a nevek, árak és az árucikkek száma!");

        itemRegistry = new HashMap<>();

        //birtokolja a factoryket, ezért ő hozza létre őket
        for(int i = 0; i < purchasables.size(); i++){
            itemRegistry.put(purchasablesNames.get(i), new StoreListing(purchasablesPrices.get(i), purchasables.get(i)));
        }
    }

    public StoreListing getListing(String name){
        return itemRegistry.get(name);
    }
}
