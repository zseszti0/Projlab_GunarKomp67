package model.shop.attachements;

import model.map.Tile;

public class BlowerHead extends Attachment{
    public BlowerHead(final int id, final String name) {
        super(id, name);
    }
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }

    public String getName(){
        return "BlowerHead";
    }
}
