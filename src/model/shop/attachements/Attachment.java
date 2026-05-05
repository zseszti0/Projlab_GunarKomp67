package model.shop.attachements;
import model.inventory.Inventory;
import model.shop.base.Purchasable;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;
import model.map.Tile;

/**
 * A látogató (Visitor) tervezési minta „látogató" szerepkörét valósítja meg.
 * Egységes felületet biztosít a különböző fejek nyersanyag fogyasztásának kezelésére,
 * illetve a mezőkkel (Tile) való interakcióra.
 * Asszociációk: Tile, Consumable, SnowShovel.
 */
abstract public class Attachment implements Purchasable {
    /**
     * A kotrófej azonosító neve.
     */
    private final String name;

    /**
     * Konstruktor az absztrakt kotrófejhez.
     * Lépései:
     * 1. Beállítja a kotrófej nevét a paraméterben kapott értékre.
     *
     * @param name A fej neve.
     */
    public Attachment(final String name) {
        this.name = name;
    }

    /**
     * Absztrakt látogatási metódus, amely megvalósítja a tisztítási folyamat polimorfizmusát (Double Dispatch).
     *
     * @param tile Az útfelület, amellyel interakcióba lép.
     * @return true, ha sikeres volt a tisztítás (történt állapotváltozás), false egyébként.
     */
    public abstract boolean cleansOn(Tile tile);

    /**
     * Absztrakt metódus, amely lekezeli a fogyóeszközök használatát az adott fejjel.
     *
     * @param c A vizsgálandó fogyóeszköz (Consumable).
     * @return true, ha a fej tudta használni és volt elég a fogyóeszközből, false egyébként.
     */
    public abstract boolean use(Consumable c);

    /**
     * Visszaadja a fej nevét.
     * Lépései:
     * 1. Visszatér a név attribútummal.
     *
     * @return A fej neve.
     */
    public String getName(){
        return name;
    }

    /**
     * Absztrakt metódus, amely visszaadja a kotrófej konkrét típusát stringként (pl. XML mentéshez).
     *
     * @return A kotrófej típusának neve.
     */
    public abstract String getType();

    /**
     * Hozzáadja ezt a kotrófejet az átadott eszköztárhoz (Inventory).
     * Lépései:
     * 1. Meghívja az inventory addAttachment() metódusát, átadva önmagát (this).
     *
     * @param inventory Az eszköztár, amibe a fej bekerül.
     */
    public void addToInventory(Inventory inventory){
        inventory.addAttachment(this);
    }

    /**
     * A Purchasable interfészből származó metódus, amely járművek esetén a pályára helyezést intézi.
     * Kotrófejek esetén alapértelmezetten null-t ad vissza, mivel a fej önmagában nem helyezhető le a pályára.
     * Lépései:
     * 1. Visszatér null értékkel.
     *
     * @param tile A célmező.
     * @return Mindig null.
     */
    public SnowShovel addToBoard(Tile tile){return null;}

}
