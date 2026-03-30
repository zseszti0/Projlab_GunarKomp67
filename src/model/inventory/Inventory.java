package model.inventory;

import model.shop.attachements.Attachment;
import model.shop.base.Purchasable;
import model.shop.consumables.Consumable;
import model.shop.consumables.Salt;
import model.shop.consumables.Biokerosene;
import skeleton.Skeleton;

import java.util.ArrayList;
import java.util.List;


/**
 * Eszköztár osztály, amely a takarító játékos tárgyait kezeli.
 */
public class Inventory {

    /** Az eszköztár neve. */
    private String name;

    /** A tárolt kotrófejek listája. */
    private List<Attachment> attachments= new ArrayList<>();

    /** A tárolt fogyóeszközök listája. */
    private List<Consumable> consumables = new ArrayList<>();

    /**
     * Konstruktor, amely létrehoz egy új eszköztárat a megadott névvel.
     * @param name Az eszköztár neve
     */
    public Inventory(String name){
        this.name=name;
    }


    /**
     * Visszaadja az eszköztár nevét.
     * @return Az eszköztár neve
     */
    public String getName(){
        return name;
    }


    /**
     * Hozzáad pénzt az eszköztárhoz.
     * Negatív összeg esetén ellenőrzi, hogy van-e elég pénz.
     * @param amount A hozzáadandó pénzmennyiség (negatív esetén levonás)
     * @return true, ha a művelet sikeres volt
     */
    public boolean addMoney(int amount){
        return Skeleton.askBoolQuestion("Van elég pénz?");
    }


    /**
     * Kicseréli a hókotróról levetett kotrófejet az eszköztárban.
     * A régi fej az eszköztárba kerül.
     * @param oldA A levetett kotrófej
     */
    public void switchAttachment(Attachment oldA){
            attachments.add(oldA);
    }

    /**
     * Hozzáad egy új kotrófejet az eszköztárhoz.
     * @param a A hozzáadandó kotrófej
     */
    public void addAttachment(Attachment a){
            attachments.add(a);
    }

    /**
     * Hozzáad sót az eszköztárhoz.
     * @param s A hozzáadandó só
     */
    public void addConsumable(Salt s){
        for(Consumable c : consumables){
            if(c.addAmount(s)){
                return;
            }
        }
        consumables.add(s);
    }


    /**
     * Hozzáad biokerozint az eszköztárhoz.
     * @param b A hozzáadandó biokerozin
     */
    public void addConsumable(Biokerosene b){
        for(Consumable c : consumables){
            if(c.addAmount(b)){
                return;
            }
        }
        consumables.add(b);    }


    /**
     * Felhasználja a szükséges fogyóeszközöket a megadott kotrófejhez.
     * Végigmegy az összes fogyóeszközön és meghívja azok használati metódusát.
     * @param head A kotrófej, amelyhez a fogyóeszközöket használni kell
     * @return true, ha a felhasználás sikeres volt
     */
    public boolean useHead(Attachment head){
        for(Consumable c : consumables){
            if(!head.use(c))
                return false;
        }
        return true;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }
}
