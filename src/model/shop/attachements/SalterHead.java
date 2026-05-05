package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;

/**
 * A sószóró fej implementálása.
 * Felsózza a mezőt (isSalted flag beállítása), amely így megállítja a hó lerakódását
 * és beindítja a jég (és a hó) olvadását.
 */
public class SalterHead extends Attachment{
    /**
     * Konstruktor a sószóró fej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public SalterHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása sószóró fejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát, hogy a mező sózott állapotba kerüljön.
     *
     * @param tile A tisztítandó/sózandó mező.
     * @return true, ha történt állapotváltozás.
     */
    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    /**
     * Megpróbálja felhasználni az átadott fogyóeszközt (sót).
     * Lépései:
     * 1. Meghívja a fogyóeszköz consume() metódusát önmagát átadva.
     *
     * @param c A használni kívánt fogyóeszköz.
     * @return true, ha a fogyóeszköz (Salt) rendelkezésre állt és használata sikeres volt.
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér a "SalterHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "SalterHead";
    }
}
