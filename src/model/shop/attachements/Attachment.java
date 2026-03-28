package model.shop.attachements;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;


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
