package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Biokerosene;

/**
 * A sarkanyfej takaritasanak implementalasa.
 * Képes barmilyen utallapotot azonnal tiszta utta alakitani.
 * Biokerozint fogyaszt.
 */
public class DragonHead extends Attachment{
    /**
     * Konstruktor a sarkanyfej letrehozasara.
     * @param name a fej neve
     * @param id a fej azonositoja
     */
    public DragonHead(final String name, final int id) {
        super(name, id);
    }

    /**
     * Felulirja az alapertelmezett metodust, meghivja a biokerozin egysegcsokkenteset.
     * @param b a felhasznalando biokerozin
     * @return true, ha sikeres volt a fogyasztas
     */
    public boolean consume(Biokerosene b){
        b.use(this);
        return true;
    }

    /**
     * A latogatasi metodus felulirasa sarkanyfejre.
     * @param tile a tisztitando mezo
     * @return sikeres volt-e a takaritas
     */
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
