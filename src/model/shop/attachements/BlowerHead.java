package model.shop.attachements;

import model.map.Tile;

public class BlowerHead extends Attachment{
    public BlowerHead(final String name, final int id) {
        super(name, id);
    }
    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }

    public String getName(){
        return "BlowerHead";
    }
}
