package model.players;

import vehicles.SnowShovel;
import shop.attachements.Attachment;
import shop.base.Shop;

public interface ICleaner {
    boolean drive(SnowShovel vehicle, Tile position);
    void changeAttachment(SnowShovel ss, Attachment newAttachment);
    void addToFleet(SnowShovel ss, Tile where);
    boolean shop(String item, Shop shop, Tile where);
}