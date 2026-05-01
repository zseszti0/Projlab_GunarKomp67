package model.players;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.base.Purchasable;
import model.shop.base.StoreListing;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.shop.attachements.Attachment;
import model.shop.base.Shop;
import model.vehicles.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * Takarító játékos osztály, amely a hókotrók irányításáért és az utak takarításáért felelős.
 */
public class Cleaner extends AbstractVehicleOwner<SnowShovel> implements ICleaner {

    /** A takarító eszköztára, amely tartalmazza a takarítófejeket és fogyóeszközöket. */
    private Inventory inventory;

    /**
     * Konstruktor, amely létrehoz egy új takarítót a megadott névvel és eszköztárral.
     * @param name A takarító neve
     * @param inventory A takarító eszköztára
     */
    public Cleaner(String name, Inventory inventory){
        super(name);
        this.inventory=inventory;
    }

    /**
     * Vezeti a megadott hókotrót a kívánt pozícióra, majd végrehajtja a tisztítást. Sikeres tisztítás esetén pénzt ír jóvá.
     * @param vehicle A vezetendő hókotró
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt
     */
    @Override
    public boolean drive(SnowShovel vehicle, Tile position) {
        Vehicle collided = vehicle.moveTo(position);
        //érvénytelen lépési kísérlet
        if(collided!=null){
            return false;
        }
        if(vehicle.clean(inventory)){
            inventory.addMoney(10);
        }
        return true;
    }

    /**
     * Lecseréli a hókotróra felszerelt kotrófejet egy újra.
     * A régi fej az eszköztárba kerül.
     * @param ss A hókotró, amelyen a cserét végre kell hajtani
     * @param newAttachment Az új kotrófej, amelyet fel szeretnénk szerelni
     */
    @Override
    public void changeAttachment(SnowShovel ss, Attachment newAttachment) {
        Attachment oldA=ss.getEquippedAttachment();
        inventory.switchAttachment(oldA);
        ss.setEquippedAttachment(newAttachment);
    }

    /**
     * Hozzáad egy új hókotrót a takarító flottájához a megadott helyen.
     * @param ss A hozzáadandó hókotró
     * @param where A mező, ahová a hókotrót le kell helyezni
     */
    @Override
    public void addToFleet(SnowShovel ss, Tile where) {
        where.acceptVehicle(ss);
        vehicles.add(ss);
    }

    @Override
    public boolean shop(String item, Shop shop, Tile where) {
        StoreListing purchase = shop.getListing(item);
        if(inventory.addMoney(-purchase.getPrice())){
            Purchasable newItem = purchase.manufacture();
            SnowShovel newSs = newItem.addToBoard(where);
            addToFleet(newSs, where);
            return true;
        }
        else return false;
    }

    @Override
    public boolean shop(String item, Shop shop) {

        StoreListing purchase = shop.getListing(item);
        if(inventory.addMoney(-purchase.getPrice())){
            Purchasable newItem = purchase.manufacture();
            newItem.addToInventory(inventory);
            return true;
        }
        else return false;
    }

    public Inventory getInventory() {
        return inventory;
    }
}