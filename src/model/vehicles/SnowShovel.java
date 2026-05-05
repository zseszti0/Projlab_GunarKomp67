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
     * Lépései:
     * 1. Beállítja a jármű nevét és kezdő pozícióját az ősosztály meghívásával.
     * 2. Felszerel egy alapértelmezett SweeperHead (seprőfej) kiegészítőt.
     *
     * @param name A hókotró neve
     * @param position A kezdő pozíció mező
     */
    public SnowShovel(String name, Tile position){

        super(name,position);
        currentAttachment = new SweeperHead("tempSweeperHead");
    }

    /**
     * Konstruktor, amit az XML parser használ: megadható a felszerelt fej is.
     * Lépései:
     * 1. Meghívja az ősosztályt a név és pozíció beállításához.
     * 2. Beállítja a paraméterben kapott kiegészítőt (equipped).
     * 3. Ha a pozíció nem null, beregisztrálja a járművet a mezőn (setVehicle).
     *
     * @param name A hókotró neve.
     * @param position A hókotró kezdőpozíciója.
     * @param equipped A hókotróra felszerelt kiegészítő.
     */
    public SnowShovel(String name, Tile position, Attachment equipped){
        super(name, position);
        this.currentAttachment = equipped;
        if(position != null) position.setVehicle(this);
    }

    /**
     * Egyszerűsített konstruktor, csak név megadásával.
     * Lépései:
     * 1. Beállítja a nevet.
     * 2. Felszerel egy alapértelmezett SweeperHead kiegészítőt.
     *
     * @param s A hókotró neve.
     */
    public SnowShovel(String s) {
        super(s);
        currentAttachment = new SweeperHead("tempSweeperHead");
    }

    /**
     * Végrehajtja a tisztítási folyamatot az aktuális mezőn.
     * Lépései:
     * 1. Megkísérli felhasználni az aktuális fejet (és az ahhoz szükséges fogyóeszközöket) az eszköztárból.
     * 2. Ha volt elég nyersanyag/üzemanyag, végrehajtja a tisztítást a jelenlegi pozíción.
     * 3. Ha a tisztítás sikeres volt (a fej ténylegesen tisztított a mezőn), jóváír 110 pénzt az eszköztárban.
     *
     * @param inventory A játékos eszköztára, amelyből a fogyóeszközök kerülnek felhasználásra
     * @return true, ha volt elég erőforrás és a tisztítási folyamat elindult (függetlenül attól, hogy volt-e mit tisztítani).
     */
    public boolean clean(Inventory inventory){
        boolean enoughFuel = inventory.useHead(currentAttachment);
        if(enoughFuel){
            if(currentAttachment.cleansOn(position)){
                inventory.addMoney(110);
            }
            return true;
        }
        return false;
    }

    /**
     * Visszaadja a hókotróra jelenleg felszerelt kotrófejet.
     *
     * @return Az aktuálisan felszerelt kotrófej (Attachment).
     */
    public Attachment getEquippedAttachment(){
        return currentAttachment;
    }

    /**
     * Beállítja a hókotróra felszerelendő kotrófejet.
     * Lépései:
     * 1. Lecseréli az eddigi kiegészítőt az új paraméterben kapottra.
     *
     * @param attachment Az új kotrófej, amelyet fel szeretnénk szerelni
     */
    public void setEquippedAttachment(Attachment attachment){
        this.currentAttachment=attachment;
    }

    /**
     * Visszaadja a hókotró aktuális pozícióját.
     *
     * @return Az a mező, amelyen a hókotró tartózkodik.
     */
    public Tile getPosition(){
        return position;
    }

    /**
     * Kezeli az autóval történő ütközést.
     * A hókotró esetén az ütközés nem jár következménnyel, a metódus üres.
     */
    @Override
    public void getHitByCar(){}

    /**
     * Hozzáadja a hókotrót egy adott eszköztárhoz.
     * Megjegyzés: A jelenlegi implementációban ez a metódus üres.
     *
     * @param inventory Az eszköztár, amihez hozzá kellene adni.
     */
    @Override
    public void addToInventory(Inventory inventory) {}

    /**
     * Hozzáadja a hókotrót a táblához a megadott mezőn.
     * Lépései:
     * 1. Beregisztrálja magát a kapott mezőre (setVehicle).
     * 2. Frissíti a saját pozícióját erre a mezőre.
     *
     * @param tile A mező, amelyre a hókotrót el szeretnénk helyezni.
     * @return Visszatér a hókotró saját példányával.
     */
    @Override
    public SnowShovel addToBoard(Tile tile) {
        tile.setVehicle(this);
        position=tile;
        return this;
    }

    /**
     * Megpróbál rálépni a megadott mezőre.
     * Lépései:
     * 1. Meghívja a célmező acceptVehicle metódusát a hókotróval paraméterként.
     *
     * @param tile A célmező.
     * @return true, ha a mező befogadta a járművet, egyébként false.
     */
    @Override
    public boolean goToTile(Tile tile){return tile.acceptVehicle(this);}
}