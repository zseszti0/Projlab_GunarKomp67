package model.vehicles;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;

/**
 * Hókotró jármű osztály, amelyet a takarító játékos irányít.
 */
public class SnowShovel extends Vehicle implements Purchasable {

    /** A hókotróra jelenleg felszerelt kotrófej. */
    private Attachment currentAttachment;

    /**
     * Konstruktor, amely létrehoz egy új hókotrót a megadott névvel és pozícióval.
     * @param name A hókotró neve
     * @param position A kezdő pozíció mező
     */
    public SnowShovel(String name, Tile position){
        super(name,position);
    }

    /**
     * Végrehajtja a tisztítási folyamatot az aktuális mezőn.
     * Felhasználja a szükséges fogyóeszközöket az eszköztárból, majd meghívja a felszerelt kotrófej tisztítási metódusát.
     * @param inventory A játékos eszköztára, amelyből a fogyóeszközök kerülnek felhasználásra
     * @return true, ha a tisztítás sikeres volt
     */
    public boolean clean(Inventory inventory){
        inventory.useHead(currentAttachment);
        return currentAttachment.cleansOn(position);
    }

    /**
     * Visszaadja a hókotróra jelenleg felszerelt kotrófejet.
     * @return Az aktuálisan felszerelt kotrófej
     */
    public Attachment getEquippedAttachment(){
        return currentAttachment;
    }

    /**
     * Beállítja a hókotróra felszerelendő kotrófejet.
     * @param attachment Az új kotrófej, amelyet fel szeretnénk szerelni
     */
    public void setEquippedAttachment(Attachment attachment){
        this.currentAttachment=attachment;
    }


    @Override
    public void getHitByCar(){}
}