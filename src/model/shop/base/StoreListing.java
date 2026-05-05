package model.shop.base;

import model.inventory.Inventory;
import model.vehicles.SnowShovel;

import java.util.function.Supplier;

/**
 * A bolt egy adott árucikkére vonatkozó bejegyzést reprezentáló osztály.
 * Felelőssége tárolni a termék árát, valamint új példányok létrehozása (Factory módszer)
 * a takarító játékos számára vásárláskor.
 */
public class StoreListing {
    /**
     * Az adott árucikk ára.
     */
    private final int price;

    /**
     * A factory metódust tartalmazó supplier az új árucikkek gyártásához.
     */
    private final Supplier<Purchasable> factory;

    /**
     * Konstruktor a boltban listázott tárgy létrehozására.
     * Lépései:
     * 1. Beállítja az árucikk árát a kapott paraméter alapján.
     * 2. Beállítja a factory-t (Supplier), ami majd képes lesz új példányokat gyártani a tárgyból.
     *
     * @param price A tárgy ára.
     * @param purchasable A tárgyat legyártó metódus/factory (Supplier).
     */
    public StoreListing(int price, Supplier<Purchasable> purchasable) {
        this.price = price;
        this.factory = purchasable;
    }

    /**
     * Létrehoz egy új megvásárolható árucikket.
     * Ez hívódik a takarító tényleges vásárlásakor.
     * Lépései:
     * 1. Meghívja a belső factory (Supplier) get() metódusát.
     * 2. Visszatér a frissen legyártott Purchasable példánnyal.
     *
     * @return A létrehozott új Purchasable objektum.
     */
    public Purchasable manufacture(){
        return factory.get();
    }

    /**
     * Visszaadja a tárgy aktuális árát a tranzakciókhoz.
     * Lépései:
     * 1. Visszatér a bejegyzéshez rendelt price értékkel.
     *
     * @return A beállított ár.
     */
    public int getPrice() {
        return price;
    }

}
