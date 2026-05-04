package model.inventory;

import model.shop.attachements.Attachment;
import model.shop.consumables.Consumable;
import model.shop.consumables.Rubble;
import model.shop.consumables.Salt;
import model.shop.consumables.Biokerosene;

import java.util.ArrayList;
import java.util.List;


/**
 * Eszköztár osztály, amely a takarító játékos tárgyait kezeli.
 */
public class Inventory {

    /** Az eszköztár neve. */
    private String name;
    private int money = 0;

    /** A tárolt kotrófejek listája. */
    private List<Attachment> attachments= new ArrayList<>();

    /** A tárolt fogyóeszközök listája. */
    private List<Consumable> consumables = new ArrayList<>();

    /**
     * Konstruktor, amely létrehoz egy új eszköztárat a megadott névvel és kezdeti pénzzel.
     * @param name Az eszköztár azonosítója/neve.
     * @param money A kezdeti pénzösszeg.
     */
    public Inventory(String name, int money){
        this.name=name;
        this.money=money;
    }

    /**
     * Teljes konstruktor, amely lehetővé teszi a kezdeti tartalom (pénz, felszerelések, fogyóeszközök) beállítását.
     * Elsősorban az XML parser használja játékállás betöltésekor.
     * @param name Az eszköztár neve.
     * @param money A kezdeti pénzmennyiség.
     * @param attachments A már birtokolt kotrófejek listája.
     * @param consumables A már birtokolt fogyóeszközök listája.
     */
    public Inventory(String name, int money, List<Attachment> attachments, List<Consumable> consumables){
        this.name = name;
        this.money = money;
        if(attachments != null) this.attachments = attachments;
        if(consumables != null) this.consumables = consumables;
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
        if (amount + money >= 0) {
            money += amount;
            return true;
        }
        return false;
    }

    /**
     * Visszaadja az aktuális pénzmennyiséget.
     * * @return A rendelkezésre álló pénzösszeg.
     */
    public int getMoney(){
        return money;
    }

    /**
     * Kicseréli a hókotróról levetett kotrófejet.
     * A járműről leszedett, régi fejet eltárolja az eszköztárban.
     * @param oldA A levetett kotrófej, amit el kell rakni az Inventory-ba.
     */
    public void switchAttachment(Attachment oldA){
            attachments.add(oldA);
    }

    /**
     * Hozzáad egy újonnan szerzett (pl. vásárolt) kotrófejet az eszköztárhoz.
     * @param a A hozzáadandó kotrófej.
     */
    public void addAttachment(Attachment a){
            attachments.add(a);
    }

    /**
     * Hozzáad sót az eszköztárhoz.
     * Ha már létezik só a készletben, megnöveli annak a mennyiségét, egyébként új elemként adja hozzá.
     * @param s A hozzáadandó só.
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
     * Ha már létezik biokerozin a készletben, megnöveli annak mennyiségét, egyébként újként hozzáadja.
     * @param b A hozzáadandó biokerozin.
     */
    public void addConsumable(Biokerosene b){
        for(Consumable c : consumables){
            if(c.addAmount(b)){
                return;
            }
        }
        consumables.add(b);
    }

    /**
     * Hozzáad biokerozint az eszköztárhoz.
     * @param r A hozzáadandó biokerozin
     */
    public void addConsumable(Rubble r){
        for(Consumable c : consumables){
            if(c.addAmount(r)){
                return;
            }
        }
        consumables.add(r);
    }

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

    /**
     * Visszaadja az eszköztárban lévő kotrófejek listáját.
     * @return A birtokolt kotrófejek (Attachment) listája.
     */
    public List<Attachment> getAttachments() {
        return attachments;
    }

    /**
     * Visszaadja az eszköztárban lévő fogyóeszközök listáját.
     * @return A birtokolt fogyóeszközök (Consumable) listája.
     */
    public List<Consumable> getConsumables(){
        return consumables;
    }

    /**
     * Név alapján kikeres és kivesz egy kotrófejet az eszköztárból (pl. felszereléshez).
     * @param newAttachment A kivenni kívánt kotrófej neve (azonosítója).
     * @return A megtalált {@link Attachment} objektum (amely ezután kikerül a listából), vagy null, ha nincs ilyen.
     */
    public Attachment getAttachment(String newAttachment) {
        for(Attachment a : attachments){
            if(a.getName().equals(newAttachment)){
                attachments.remove(a);
                return a;
            }
        }
        return null;
    }
}
