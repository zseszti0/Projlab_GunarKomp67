package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;

/**
 * A soszoro fej implementalasa.
 * A so megallitja a ho lerakodasat es beinditja a jeg olvadasat.
 */
public class SalterHead extends Attachment{
    /**
     * Konstruktor a soszoro fej letrehozasara.
     * @param name a fej neve
     */
    public SalterHead(final String name) {
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
        return "SalterHead";
    }
}
