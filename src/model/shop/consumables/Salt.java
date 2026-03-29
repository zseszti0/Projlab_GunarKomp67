package model.shop.consumables;

import model.shop.attachements.SalterHead;

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
    public boolean use(SalterHead s){
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
}
