package model.vehicles;

import model.inventory.Inventory;
import model.map.Tile;
import model.shop.attachements.Attachment;
import model.shop.attachements.SweeperHead;
import model.shop.base.Purchasable;

import java.util.List;

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
        currentAttachment = new SweeperHead("tempSweeperHead");
    }

    /**
     * Konstruktor, amit az XML parser használ: megadható a felszerelt fej is.
     */
    public SnowShovel(String name, Tile position, Attachment equipped){
        super(name, position);
        this.currentAttachment = equipped;
        if(position != null) position.setVehicle(this);
    }

    public SnowShovel(String s) {
        super(s);
        currentAttachment = new SweeperHead("tempSweeperHead");
    }

    /**
     * Végrehajtja a tisztítási folyamatot az aktuális mezőn.
     * Felhasználja a szükséges fogyóeszközöket az eszköztárból, majd meghívja a felszerelt kotrófej tisztítási metódusát.
     * @param inventory A játékos eszköztára, amelyből a fogyóeszközök kerülnek felhasználásra
     * @return true, ha a tisztítás sikeres volt
     */
    public boolean clean(Inventory inventory){
        boolean enoughFuel = inventory.useHead(currentAttachment);
        if(enoughFuel){
            if(currentAttachment.cleansOn(position)){
                inventory.addMoney(100);
            }
            return true;
        }
        return false;
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

    public Tile getPosition(){
        return position;
    }


    @Override
    public void getHitByCar(){}

    @Override
    public void addToInventory(Inventory inventory) {

    }
    @Override
    public SnowShovel addToBoard(Tile tile) {
        tile.setVehicle(this);
        position=tile;
        return this;
    }

    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}
}