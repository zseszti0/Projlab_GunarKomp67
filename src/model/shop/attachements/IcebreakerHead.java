package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Consumable;

/**
 * A jegtoro fej implementalasa.
 * Feltori a jeget, ami ezaltal sekely hova valtozik.
 * A tobbi utallapot ellen hatastalan.
 */
public class IcebreakerHead extends Attachment{
    /**
     * Konstruktor a jegtoro fej letrehozasara.
     * @param name a fej neve
     * @param id a fej azonositoja
     */
    public IcebreakerHead(final String name, final int id) {
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
}
