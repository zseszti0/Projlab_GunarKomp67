package model.shop.base;

import model.inventory.Inventory;
import model.map.Tile;
import model.vehicles.SnowShovel;

/**
 * A megvásárolható árucikkek közös interfésze.
 * A kontroller és a modell közötti érintkezési felület része, biztosítja az egységes névlekérdezést,
 * illetve a megvásárolt tárgyak megfelelő helyre (eszköztárba vagy pályára) kerülését.
 */
public interface Purchasable {
    /**
     * Visszaadja a megvásárolható árucikk nevét.
     * Lépései:
     * 1. Az implementáló osztály visszatér az árucikk azonosító nevével.
     *
     * @return Az árucikk neve.
     */
    String getName();

    /**
     * Hozzáadja a megvásárolt árucikket a takarító eszköztárához.
     * Lépései:
     * 1. Az adott árucikk típusától függően (pl. Consumable vagy Attachment)
     * meghívja az inventory megfelelő hozzáadó metódusát önmagát átadva.
     *
     * @param inventory Az eszköztár, amibe az árucikket be kell tenni.
     */
    public void addToInventory(Inventory inventory);

    /**
     * Lehelyezi a megvásárolt árucikket (jellemzően járművet) a pályára.
     * Lépései:
     * 1. Jármű esetén példányosítja/lehelyezi önmagát az adott mezőre.
     * 2. Nem lehelyezhető tárgyak (pl. fejek, fogyóeszközök) esetén null-t ad vissza.
     *
     * @param tile A célmező, ahová az árucikket le kell helyezni.
     * @return A lehelyezett hókotró (SnowShovel) referenciája, vagy null, ha a tárgy nem lehelyezhető.
     */
    public SnowShovel addToBoard(Tile tile);
}