package model.shop.attachements;

import model.map.Tile;

public class SweeperHead extends Attachment{
    public SweeperHead(final int id) {
        super(id);
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
