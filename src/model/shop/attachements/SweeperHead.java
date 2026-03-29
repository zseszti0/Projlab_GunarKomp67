package model.shop.attachements;

import model.map.Tile;

/**
 * A soprofej implementalasa.
 * A havat a szomszedos mezore tolja.
 */
public class SweeperHead extends Attachment{
    /**
     * Konstruktor a soprofej letrehozasara.
     * @param name a fej neve
     * @param id a fej azonositoja
     */
    public SweeperHead(final String name, final int id) {
        super(name, id);
    }

    /**
     * A latogatasi metodus felulirasa soprofejre.
     * @param tile a tisztitando mezo
     * @return sikeres volt-e a takaritas
     */
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
