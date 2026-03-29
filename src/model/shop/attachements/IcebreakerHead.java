package model.shop.attachements;

import model.map.Tile;

public class IcebreakerHead extends Attachment{
    public IcebreakerHead(final int id, final String name) {
        super(id, name);
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
