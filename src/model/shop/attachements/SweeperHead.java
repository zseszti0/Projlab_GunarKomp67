package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A söprőfej implementálása.
 * A havat megtisztítja a jelenlegi mezőről, de az összegyűjtött havat egy szomszédos mezőre tolja át.
 */
public class SweeperHead extends Attachment{
    /**
     * Konstruktor a söprőfej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public SweeperHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása söprőfejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát. Ez a hívás elvégzi a mező megtisztítását és a hó áttolását a szomszédra.
     *
     * @param tile A tisztítandó mező.
     * @return true, ha történt állapotváltozás (volt hó a mezőn, amit letolt).
     */
    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    /**
     * Megpróbálja felhasználni az átadott fogyóeszközt.
     * Lépései:
     * 1. Meghívja a fogyóeszköz consume() metódusát önmagát átadva.
     *
     * @param c A használni kívánt fogyóeszköz.
     * @return true, ha a használat sikeres (a söprőfej nem fogyaszt extra nyersanyagot, így alapvetően mindig igaz).
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér a "SweeperHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "SweeperHead";
    }
}
