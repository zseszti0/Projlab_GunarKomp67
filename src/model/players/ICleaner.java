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
     * Lépései:
     * 1. Elhelyezi a táblán és felveszi a birtokolt járművek listájára.
     * @param ss A hozzáadandó hókotró
     * @param where A mező, ahová a hókotrót le kell helyezni
     */
    void addToFleet(SnowShovel ss, Tile where);

    /**
     * Lehetővé teszi járművek vásárlását a boltból, megadott célmezőre helyezve.
     * @param item A vásárolandó tárgy (jármű) neve.
     * @param shop A bolt referencia.
     * @param where A mező, ahová a vásárolt jármű kerül.
     * @return true, ha a tranzakció sikeres volt.
     */
    boolean shop(String item, Shop shop, Tile where);

    /**
     * Lehetővé teszi tárgyak (kotrófejek, fogyóeszközök) vásárlását a boltból az eszköztárba.
     * @param item A vásárolandó tárgy neve.
     * @param shop A bolt referencia.
     * @return true, ha a tranzakció sikeres volt.
     */
    boolean shop(String item, Shop shop);
}