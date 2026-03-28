package model.shop.attachements;
import shop.base.Purchasable;
import shop.consumables.Biokerosene;
import shop.consumables.Salt;

abstract public class Attachment extends Purchasable {
    private SnowShovel snowShovel;

    public Attachment(final int id){
        super(id);
    }

    public boolean consume(Salt s){
        return true;
    }
    public boolean consume(Biokerosene b){
        return true;
    }
    public boolean Tile cleanWith(Tile tile){
        tile.cleanTile(this);
    }
}
