package model.shop.base;

import model.inventory.Inventory;
import model.vehicles.SnowShovel;

import java.util.function.Supplier;

/**
 * A bolt egy adott arucikkere vonatkozo bejegyzest reprezentalo osztaly.
 * Felelossege uj peldanyok letrehozasa a takarito jatekos szamara vasarlaskor.
 */
public class StoreListing {
    /**
     * Az adott arucikk ara.
     */
    private final int price;

    /**
     * A factory metodust tartalmazo supplier az uj arucikkek gyartasahoz.
     */
    private final Supplier<Purchasable> factory;


    /**
     * Konstruktor a boltban listazott targy letrehozasara.
     * @param price a targy ara
     * @param purchasable maga a targy (ami a peldanyositaskor alapul szolgal)
     */
    public StoreListing(int price, Supplier<Purchasable> purchasable) {
        this.price = price;
        this.factory = purchasable;
    }


    /**
     * Letrehoz egy uj megvasarolhato arucikket.
     * Ez hívódik a takarito vasarlasakor.
     * @return a letrehozott Purchasable objektum
     */
    public Purchasable manufacture(){
        return factory.get();
    }

    /**
     * Visszaadja a targy aktualis arat a tranzakciokhoz.
     * @return a beallitott ar
     */
    public int getPrice() {
        return price;
    }

}
