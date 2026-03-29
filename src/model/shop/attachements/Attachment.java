package model.shop.attachements;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.map.Tile;

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
}
