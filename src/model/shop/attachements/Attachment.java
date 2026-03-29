package model.shop.attachements;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.map.Tile;

abstract public class Attachment implements Purchasable {
    int id;
    private SnowShovel snowShovel;

    public Attachment(final int id){
        super();
        this.id = id;
    }

    public boolean consume(Salt s){
        return true;
    }
    public boolean consume(Biokerosene b){
        return true;
    }
}
