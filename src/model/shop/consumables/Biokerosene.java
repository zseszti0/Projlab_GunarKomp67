package model.shop.consumables;

import model.shop.attachements.DragonHead;
import model.shop.attachements.SalterHead;
import skeleton.Skeleton;

/**
 * A rendelkezesre allo biokerozinmennyiseg nyilvantartasa es a felhasznalasi kiserletek validalasa.
 * Biztositja, hogy a sarkanyfej csak akkor fejthesse ki hatasat, ha van elerheto biokerozin.
 */
public class Biokerosene extends Consumable{
    /**
     * Konstruktor a biokerozin letrehozasahoz.
     * @param id a biokerozin azonositoja
     * @param amount a kezdeti mennyiseg
     */
    public Biokerosene(final int id, int amount, String name){
        super(id, amount,  name);
    }

    /**
     * A biokerozin felhasznalasa a sarkanyfej altal. Levon egy egyseget.
     * @param d a sarkanyfej, ami felhasznalja
     * @return true, jelezve a sikeres felhasznalast
     */
    public boolean consume(DragonHead d){
        boolean answer = Skeleton.askBoolQuestion("Van-e elég biokerozin?");
        if(!answer) return false;
        amount--;
        return true;
    }

    /**
     * Keszletnoveles biokerozin eseten.
     * @param b a hozzaadando masik biokerozin objektum
     * @return true a sikeres hozzaadas utan
     */
    @Override
    public boolean addAmount(Biokerosene b){
        this.amount = this.getAmount() + b.getAmount();
        return true;
    }
}
