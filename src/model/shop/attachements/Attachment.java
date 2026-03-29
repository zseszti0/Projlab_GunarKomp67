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

    public Attachment(final String name, final int id) {
        this.id = id;
        this.name = name;
    }

    public abstract boolean cleanWith(Tile tile);

    public boolean consume(Salt s){
        return true;
    }
    public boolean consume(Biokerosene b){
        return true;
    }

    public String getName(){
        return name;
    }

    public void setSnowShovel(SnowShovel snowShovel){
        this.snowShovel = snowShovel;
    }
}
