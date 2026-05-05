package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A hóhányófej takarításának implementálása.
 * A látogató minta része a takarítási algoritmusban. Messzire szórja a havat (amit elfúj a szél),
 * így az nem kerül át a szomszédos mezőkre.
 */
public class BlowerHead extends Attachment{

    /**
     * Konstruktor a hóhányófej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public BlowerHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása hóhányófejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát (BlowerHead), így a mező a megfelelő állapotváltozást hajtja végre.
     *
     * @param tile A tisztítandó mező.
     * @return true, ha történt állapotváltozás a mezőn.
     */
    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    /**
     * Megpróbálja felhasználni az átadott fogyóeszközt.
     * Lépései:
     * 1. Meghívja a fogyóeszköz (Consumable) consume() metódusát a BlowerHead típussal.
     *
     * @param c A vizsgálandó fogyóeszköz.
     * @return true, ha a használat sikeres volt.
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér a "BlowerHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "BlowerHead";
    }
}
