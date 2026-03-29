package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Salt;

/**
 * A soszoro fej implementalasa.
 * A so megallitja a ho lerakodasat es beinditja a jeg olvadasat.
 */
public class SalterHead extends Attachment{
    /**
     * Konstruktor a soszoro fej letrehozasara.
     * @param name a fej neve
     * @param id a fej azonositoja
     */
    public SalterHead(final String name, final int id) {
        super(name, id);
    }

    /**
     * Felulirja az alapertelmezett metodust, sokiurites tortenik.
     * @param s a felhasznalando so
     * @return true, ha sikeres volt a fogyasztas
     */
    public boolean consume(Salt s){
        s.use(this);
        return true;
    }

    /**
     * A latogatasi metodus felulirasa soszoro fejre.
     * @param tile a tisztitando (sozando) mezo
     * @return sikeres volt-e a sozas
     */
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
