package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A soszoro fej implementalasa.
 * A so megallitja a ho lerakodasat es beinditja a jeg olvadasat.
 */
public class CobblestoneHead extends Attachment{
    /**
     * Konstruktor a soszoro fej letrehozasara.
     * @param name a fej neve
     */
    public CobblestoneHead(final String name) {
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
        return "CobblestoneHead";
    }
}
