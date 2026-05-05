package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Consumable;

/**
 * A sárkányfej takarításának implementálása.
 * Képes bármilyen útállapotot azonnal tiszta úttá alakítani a hó/jég elolvasztásával.
 * Működéséhez biokerozint (Biokerosene) fogyaszt.
 */
public class DragonHead extends Attachment{
    /**
     * Konstruktor a sárkányfej létrehozására.
     * Lépései:
     * 1. Meghívja az ősosztály konstruktorát a név beállításához.
     *
     * @param name A fej neve.
     */
    public DragonHead(final String name) {
        super(name);
    }

    /**
     * A látogatási metódus felülírása sárkányfejre.
     * Lépései:
     * 1. Visszahívja a mező (Tile) cleanTile() metódusát, átadva önmagát, hogy a mező tisztává váljon.
     *
     * @param tile A tisztítandó mező.
     * @return true, ha történt tényleges állapotváltozás.
     */
    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    /**
     * Megpróbálja felhasználni az átadott fogyóeszközt (kerozint).
     * Lépései:
     * 1. Meghívja a fogyóeszköz consume() metódusát önmagát átadva.
     *
     * @param c A használni kívánt fogyóeszköz.
     * @return true, ha a fogyóeszköz (Biokerosene) rendelkezésre állt és fogyasztása sikeres volt.
     */
    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    /**
     * Visszaadja a kotrófej típusát stringként.
     * Lépései:
     * 1. Visszatér a "DragonHead" stringgel.
     *
     * @return A kotrófej típusa.
     */
    @Override
    public String getType(){
        return "DragonHead";
    }
}
