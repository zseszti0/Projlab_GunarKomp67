package model.shop.attachements;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.map.Tile;

abstract public class Attachment implements Purchasable {
    private final String name;
    int id;
    private SnowShovel snowShovel;

    public Attachment(final int id, String name) {
        this.id = id;
        this.name = name;
    }

    public boolean consume(Salt s){
        return true;
    }
    public boolean consume(Biokerosene b){
        return true;
    }

    public String getName(){
        return name;
    }
}
