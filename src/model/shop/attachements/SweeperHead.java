package model.shop.attachements;

import model.map.Tile;

public class SweeperHead extends Attachment{
    public SweeperHead(final String name, final int id) {
        super(name, id);
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
