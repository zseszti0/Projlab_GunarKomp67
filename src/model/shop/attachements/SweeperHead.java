package model.shop.attachements;

import model.map.Tile;

public class SweeperHead extends Attachment{
    public SweeperHead(final int id, final String name) {
        super(id, name);
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
