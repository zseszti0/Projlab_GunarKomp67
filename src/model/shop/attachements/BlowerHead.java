package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A hanyofej takaritasanak implementalasa.
 * A latogato minta resze a takaritasi algoritmusban. Messzire szorja a havat (ami elfuj a szel).
 */
public class BlowerHead extends Attachment{
    /**
     * Konstruktor a hanyofej letrehozasara.
     * @param name a fej neve
     */
    public BlowerHead(final String name) {
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
        return "BlowerHead";
    }
}
