package control;

import model.map.Tile;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.shop.attachements.Attachment;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;

import java.util.List;

/**
 * Segédosztály, amely különböző entitások (mezők, járművek, kiegészítők) azonosító
 * (ID) alapján történő visszakereséséért lenne felelős.
 * Jelenleg minden metódusa befejezetlen, mely alapértelmezetten null értékkel tér vissza.
 */
public class ControlModelConnector {

    /**
     * Megkeresne egy mezőt a megadott azonosító alapján. Jelenleg null-t ad vissza.
     */
    public Tile getTileById(List<Tile> tiles, int id){
        return null;
    }

    /**
     * Megkeresne egy hótolót a takarítók járművei között. Jelenleg null-t ad vissza.
     */
    public SnowShovel getSnowShovelById(List<Cleaner> cleaners, int id){
        return null;
    }

    /**
     * Megkeresne egy buszt a buszsofőrök járművei között. Jelenleg null-t ad vissza.
     */
    public Bus getBusById(List<BusChaffeur> buses, int id){
        return null;
    }

    /**
     * Megkeresne egy kiegészítőt az azonosítója alapján. Jelenleg null-t ad vissza.
     */
    public Attachment getAttachmentById(List<Attachment> attachments, int id){
        return null;
    }
}
