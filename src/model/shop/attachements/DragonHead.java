package model.shop.attachements;

import model.map.Tile;
import model.shop.consumables.Biokerosene;

public class DragonHead extends Attachment{
    public DragonHead(final String name, final int id) {
        super(name, id);
    }

    public boolean consume(Biokerosene b){
        b.use(this);
        return true;
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
