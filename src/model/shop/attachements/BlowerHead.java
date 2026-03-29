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
     * @param id a fej azonositoja
     */
    public BlowerHead(final String name, final int id) {
        super(name, id);
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

    /**
     * Visszaadja a hanyofej tipusnevet.
     * @return "BlowerHead" string
     */
    public String getName(){
        return "BlowerHead";
    }
}
