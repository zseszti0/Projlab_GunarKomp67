package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A kőzúzalék szóró fej implementálása.
 * Felszórja a pályát zúzalékkal (Rubble), ami megakadályozza a csúszást a jeges utakon.
 */
public class CobblestoneHead extends Attachment{
    /**
     * Konstruktor a kőzúzalék szóró fej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public CobblestoneHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása kőzúzalék szóró fejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát, hogy a mező felszórt állapotba kerüljön.
     *
     * @param tile A tisztítandó/felszórandó mező.
     * @return true, ha történt állapotváltozás (bár a specifikus implementáció alapján false-t adhat).
     */
    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    /**
     * Megpróbálja felhasználni az átadott fogyóeszközt (pl. Rubble).
     * Lépései:
     * 1. Meghívja a fogyóeszköz consume() metódusát önmagát átadva.
     *
     * @param c A használni kívánt fogyóeszköz.
     * @return true, ha a fogyóeszközből volt elég és a használat sikeres.
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér a "CobblestoneHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "CobblestoneHead";
    }
}
