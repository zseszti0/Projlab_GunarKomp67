package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Salt;

public class SalterHead extends Attachment{
    public SalterHead(final int id) {
        super(id);
    }

    public boolean consume(Salt s){
        s.use(this);
        return true;
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
