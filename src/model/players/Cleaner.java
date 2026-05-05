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
     * Lépései:
     * 1. Meghívja az ősosztályt a név beállítására.
     * 2. Elmenti a paraméterben kapott inventory-t.
     * * @param name A takarító neve
     * @param inventory A takarító eszköztára
     */
    public Cleaner(String name, Inventory inventory){
        super(name);
        this.inventory=inventory;
    }

    /**
     * Konstruktor, amely a játék betöltéséhez szükséges (név, inventory, meglévő flotta).
     * Lépései:
     * 1. Meghívja az ősosztályt a név és a flotta beállítására.
     * 2. Elmenti az eszköztárat.
     * * @param id A takarító azonosítója.
     * @param inventory A takarító eszköztára.
     * @param fleet A meglévő hókotrók listája.
     */
    public Cleaner(String id, Inventory inventory, List<SnowShovel> fleet) {
        super(id, fleet);
        this.inventory = inventory;
    }

    /**
     * Egyszerűsített konstruktor csak névvel.
     * Lépései:
     * 1. Meghívja az ősosztályt.
     * * @param s A takarító neve.
     */
    public Cleaner(String s) {
        super(s);
    }

    /**
     * Vezeti a megadott hókotrót a kívánt pozícióra, majd végrehajtja a tisztítást. Sikeres tisztítás esetén pénzt ír jóvá.
     * Lépései:
     * 1. Megpróbálja léptetni a hókotrót a moveTo() segítségével.
     * 2. Ha ütközés történt (a visszatérési érték nem null), azonnal hamis (false) értékkel tér vissza.
     * 3. Ha sikeres volt a lépés, a hókotró végrehajtja a tisztítást a clean() metódussal (amely kezeli az inventory-t és a bevételeket is).
     * 4. Igazzal tér vissza.
     * * @param vehicle A vezetendő hókotró
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt
     */
    @Override
    public boolean drive(SnowShovel vehicle, Tile position) {
        Vehicle collided = vehicle.moveTo(position);
        if(collided!=null){
            return false;
        }
        vehicle.clean(inventory);

        return true;
    }

    /**
     * Lecseréli a hókotróra felszerelt kotrófejet egy újra (objektum referencia alapján).
     * Lépései:
     * 1. Lekéri az aktuálisan felszerelt kotrófejet a járműről.
     * 2. Ezt a régi fejet beteszi a takarító eszköztárába (inventory.switchAttachment).
     * 3. Felszereli az új kotrófejet a hókotróra.
     * * @param ss A hókotró, amelyen a cserét végre kell hajtani
     * @param newAttachment Az új kotrófej, amelyet fel szeretnénk szerelni
     */
    @Override
    public void changeAttachment(SnowShovel ss, Attachment newAttachment) {
        Attachment oldA = ss.getEquippedAttachment();
        inventory.switchAttachment(oldA);
        ss.setEquippedAttachment(newAttachment);
    }

    /**
     * Lecseréli a hókotróra felszerelt kotrófejet egy újra a név (string azonosító) alapján.
     * Lépései:
     * 1. Lekéri a régi kotrófejet.
     * 2. Átteszi a régi fejet az inventory-ba.
     * 3. Kiveszi az új nevű kotrófejet az inventory-ból, és felszereli a járműre.
     * * @param ss A hókotró.
     * @param newAttachment Az új kotrófej azonosító neve, ami már az inventory-ban van.
     */
    public void changeAttachment(SnowShovel ss, String newAttachment) {
        Attachment oldA = ss.getEquippedAttachment();
        inventory.switchAttachment(oldA);
        ss.setEquippedAttachment(inventory.getAttachment(newAttachment));
    }

    /**
     * Hozzáad egy új hókotrót a takarító flottájához a megadott helyen.
     * Lépései:
     * 1. A megadott mező (Tile) acceptVehicle metódusával ráhelyezi a járművet a táblára.
     * 2. Hozzáadja a hókotrót a takarító belső listájához (vehicles).
     * * @param ss A hozzáadandó hókotró
     * @param where A mező, ahová a hókotrót le kell helyezni
     */
    @Override
    public void addToFleet(SnowShovel ss, Tile where) {
        where.acceptVehicle(ss);
        vehicles.add(ss);
    }

    /**
     * Vásárol egy járművet (hókotrót) a boltból, és elhelyezi a táblán.
     * Lépései:
     * 1. Lekéri az adott termék bolti adatlapját (StoreListing).
     * 2. Megpróbálja levonni az árat az inventory-ból.
     * 3. Ha van elég pénz (true), legyártatja a tárgyat (manufacture).
     * 4. Ráhelyezi a legyártott hókotrót a megadott mezőre (addToBoard).
     * 5. Hozzáadja az új hókotrót a takarító saját flottájához (addToFleet).
     * 6. Igazzal tér vissza a sikeres vásárlás után, egyébként hamissal.
     * * @param item A vásárolni kívánt árucikk neve.
     * @param shop A bolt objektum, ahonnan vásárol.
     * @param where A mező, ahová az új járművet le kell helyezni.
     * @return true, ha volt elég pénz és a vásárlás sikeres.
     */
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

    /**
     * Vásárol egy tárgyat (fogyóeszközt vagy kotrófejet) a boltból, ami az eszköztárba kerül.
     * Lépései:
     * 1. Lekéri az adott termék bolti adatlapját.
     * 2. Megpróbálja levonni az árat az inventory-ból.
     * 3. Ha van elég pénz, legyártatja a tárgyat.
     * 4. Hozzáadja az újonnan vett tárgyat az inventory-hoz.
     * 5. Igazzal tér vissza, ha a tranzakció sikeres volt.
     * * @param item A vásárolni kívánt árucikk neve.
     * @param shop A bolt objektum.
     * @return true, ha a vásárlás megtörtént.
     */
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

    /**
     * Visszaadja a takarító eszköztárát (Inventory).
     * @return Az inventory példánya.
     */
    public Inventory getInventory() {
        return inventory;
    }
}