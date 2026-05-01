package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A soprofej implementalasa.
 * A havat a szomszedos mezore tolja.
 */
public class SweeperHead extends Attachment{
    /**
     * Konstruktor a soprofej letrehozasara.
     * @param name a fej neve
     */
    public SweeperHead(final String name) {
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
        return "SweeperHead";
    }
}
