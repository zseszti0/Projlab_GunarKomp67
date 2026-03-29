package model.shop.attachements;

import model.map.Tile;

public class IcebreakerHead extends Attachment{
    public IcebreakerHead(final String name, final int id) {
        super(name, id);
    }

    public boolean cleanWith(Tile tile){
        return tile.cleanTile(this);
    }
}
