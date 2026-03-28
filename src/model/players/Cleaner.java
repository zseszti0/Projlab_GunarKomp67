package model.players;

import vehicles.SnowShovel;
import shop.attachements.Attachment;
import shop.base.Shop;
import java.util.List;

public class Cleaner extends AbstractVehicleOwner<SnowShovel> implements ICleaner {
    private Inventory inventory;


    @Override
    public boolean drive(SnowShovel vehicle, Tile position) {
    }

    @Override
    public void changeAttachment(SnowShovel ss, Attachment newAttachment) {
    }

    @Override
    public void addToFleet(SnowShovel ss, Tile where) {
    }

    @Override
    public boolean shop(String item, Shop shop, Tile where) {
    }
}