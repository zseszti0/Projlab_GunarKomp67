package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A jégtörő fej implementálása.
 * Feltöri a jeget (Icy), ami ezáltal sekély hóvá (ShallowSnowy) változik.
 * A többi útállapot (pl. mély hó, tiszta) ellen hatástalan.
 */
public class IcebreakerHead extends Attachment{
    /**
     * Konstruktor a jégtörő fej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public IcebreakerHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása jégtörő fejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát, a jég feltöréséhez.
     *
     * @param tile A tisztítandó (jégmentesítendő) mező.
     * @return true, ha történt állapotváltozás (volt jég, ami feltört).
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
     * @return true, ha a használat sikeres.
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér az "IcebreakerHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "IcebreakerHead";
    }
}
