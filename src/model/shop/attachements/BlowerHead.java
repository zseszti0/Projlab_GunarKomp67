package model.shop.attachements;

import model.map.Tile;

public class BlowerHead extends Attachment{
    public BlowerHead(final int id) {
        super(id);
    }
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
