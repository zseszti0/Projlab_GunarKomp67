package model.players;

import model.map.Tile;
import model.vehicles.SnowShovel;
import model.shop.attachements.Attachment;
import model.shop.base.Shop;

/**
 * Interfész, amely a takarító játékos műveleteit definiálja.
 */
public interface ICleaner {

    /**
     * Vezeti a megadott hókotrót a kívánt pozícióra.
     * @param vehicle A vezetendő hókotró
     * @param position A célpozíció mező
     * @return true, ha a vezetés sikeres volt
     */
    boolean drive(SnowShovel vehicle, Tile position);

    /**
     * Lecseréli a hókotróra felszerelt kotrófejet egy újra.
     * @param ss A hókotró, amelyen a cserét végre kell hajtani
     * @param newAttachment Az új kotrófej, amelyet fel szeretnénk szerelni
     */
    void changeAttachment(SnowShovel ss, Attachment newAttachment);

    /**
     * Hozzáad egy új hókotrót a takarító flottájához a megadott helyen.
     * @param ss A hozzáadandó hókotró
     * @param where A mező, ahová a hókotrót le kell helyezni
     */
    void addToFleet(SnowShovel ss, Tile where);


    boolean shop(String item, Shop shop, Tile where);


    boolean shop(String item, Shop shop);
}