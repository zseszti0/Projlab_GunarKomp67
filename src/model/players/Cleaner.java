package model.players;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.base.Purchasable;
import model.shop.base.StoreListing;
import model.shop.consumables.Consumable;
import model.vehicles.SnowShovel;
import model.shop.attachements.Attachment;
import model.shop.base.Shop;
import model.vehicles.Vehicle;

import java.util.List;

public class Cleaner extends AbstractVehicleOwner<SnowShovel> implements ICleaner {
    private Inventory inventory;

    public Cleaner(String name, Inventory inventory){
        super(name);
        this.inventory=inventory;
    }


    @Override
    public boolean drive(SnowShovel vehicle, Tile position) {
        vehicle.moveTo(position);
        boolean success = vehicle.clean(inventory);
        if(success){
            inventory.addMoney(10);
        }
        return true;
    }

    @Override
    public void changeAttachment(SnowShovel ss, Attachment newAttachment) {
        Attachment oldA=ss.getEquippedAttachment();
        inventory.switchAttachment(oldA);
        ss.setEquippedAttachment(newAttachment);
    }

    @Override
    public void addToFleet(SnowShovel ss, Tile where) {
        vehicles.add(ss);
    }

    @Override
    public boolean shop(String item, Shop shop, Tile where) {
        StoreListing sl = shop.getListing(item);
        boolean hasMoney = inventory.addMoney(-sl.getPrice());
        if (hasMoney) {
            Purchasable p = sl.manufacture();
            if (p instanceof SnowShovel) {
                where.acceptVehicle((SnowShovel) p);
            }
        }
        return true;
    }

    public boolean shop(String item, Shop shop) {
        StoreListing sl = shop.getListing(item);
        boolean hasMoney = inventory.addMoney(-sl.getPrice());
        if (hasMoney) {
            Purchasable p = sl.manufacture();
            if (p instanceof Attachment) {
                inventory.addAttachment((Attachment) p);
            } else if (p instanceof Consumable) {
                inventory.addConsumable((Consumable) p);
            }
        }
        return true;
    }
}