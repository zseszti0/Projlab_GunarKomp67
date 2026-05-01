package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Consumable;

/**
 * A sarkanyfej takaritasanak implementalasa.
 * Képes barmilyen utallapotot azonnal tiszta utta alakitani.
 * Biokerozint fogyaszt.
 */
public class DragonHead extends Attachment{
    /**
     * Konstruktor a sarkanyfej letrehozasara.
     * @param name a fej neve
     */
    public DragonHead(final String name) {
        super(name);
    }

    /**
     * A latogatasi metodus felulirasa hanyofejre.
     * @param tile a tisztitando mezo
     * @return sikeres volt-e a takaritas
     */

    @Override
    public boolean cleansOn(Tile tile) {
        return tile.cleanTile(this);
    }

    @Override
    public boolean use(Consumable c){
        return c.consume(this);
    }

    @Override
    public String getType(){
        return "DragonHead";
    }
}
