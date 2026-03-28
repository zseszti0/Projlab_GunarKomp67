package model.players;

import model.map.Tile;
import model.vehicles.SnowShovel;
import model.shop.attachements.Attachment;
import model.shop.base.Shop;

public interface ICleaner {
    boolean drive(SnowShovel vehicle, Tile position);
    void changeAttachment(SnowShovel ss, Attachment newAttachment);
    void addToFleet(SnowShovel ss, Tile where);
    boolean shop(String item, Shop shop, Tile where);
}