package model.map.tilestates;

import model.map.PathFinder;
import model.map.Tile;
import model.shop.attachements.*;
import model.vehicles.Bus;
import model.vehicles.Car;
import model.vehicles.SnowShovel;
import model.vehicles.Vehicle;

import java.util.List;

/**
 * A modellbeli mezőállapotok közös absztrakt őse (State tervezési minta).
 * Felelőssége a mező aktuális állapotának tárolása és az állapotváltozások (események) kezelése.
 * Az állapotváltozások lehetnek: hóesés, olvadás, a hó összenyomódása (jármű áthaladásakor),
 * és a különböző típusú hókotró fejekkel történő tisztítás.
 * Alapértelmezésben minden metódus önmagával tér vissza (nem történik állapotváltozás),
 * a konkrét leszármazottak csak a rájuk jellemző és érvényes változásokat definiálják felül.
 */
public abstract class TileState {

    /**
     * Kezeli a hóesés eseményét az adott mezőn.
     * @return Az új mezőállapot a hóesés után.
     */
    public abstract TileState snowFall();

    /**
     * Kezeli a hó/jég olvadását (pl. ha a mező be van sózva, vagy a játék olvadást vált ki).
     * @return Az új mezőállapot az olvadás után.
     */
    public abstract TileState snowMelt();


    /**
     * Kezeli a söprőfej (SweeperHead) általi tisztítást.
     * @param swh A tisztítást végző söprőfej.
     * @return Az új mezőállapot a tisztítás után.
     */
    public TileState cleanedBy(SweeperHead swh) {return this;}

    /**
     * Kezeli a hóhányófej (BlowerHead) általi tisztítást.
     * @param bh A tisztítást végző hóhányófej.
     * @return Az új mezőállapot a tisztítás után.
     */
    public TileState cleanedBy(BlowerHead bh) {return this;}

    /**
     * Kezeli a jégtörő fej (IcebreakerHead) általi tisztítást.
     * @param ih A tisztítást végző jégtörő fej.
     * @return Az új mezőállapot a tisztítás után.
     */
    public TileState cleanedBy(IcebreakerHead ih) {return this;}


    /**
     * Kezeli a sárkányfej (DragonHead) általi tisztítást (olvasztást).
     * @param dh A tisztítást végző sárkányfej.
     * @return Az új mezőállapot a tisztítás után.
     */
    public TileState cleanedBy(DragonHead dh) {return CleanTileState.getInstance();}


    /**
     * Kezeli a szomszédos mezőről átsöpört (vagy fújt) sekély hó érkezését.
     * @param ssts A beérkező sekély hó állapot példánya.
     * @return Az új mezőállapot a hó érkezése után.
     */
    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return ShallowSnowyTileState.getInstance();}

    /**
     * Kezeli a szomszédos mezőről átsöpört (vagy fújt) mély hó érkezését.
     * @param dsts A beérkező mély hó állapot példánya.
     * @return Az új mezőállapot a hó érkezése után.
     */
    public TileState acceptSweptSnow(DeepSnowyTileState dsts) {return DeepSnowyTileState.getInstance();}

    /**
     * Kezeli egy jármű mezőre érkezését, ami a hó tömörödését eredményezheti (pl. sekély hóból jég).
     * @return Az új mezőállapot a jármű áthaladása után.
     */
    public TileState compressionReached(){return this;}

    /**
     * Növeli a mező összenyomási indexét egy jármű áthaladásakor.
     * @param compressionIndex Az eddigi kompressziós index.
     * @return A frissített (megnövelt) kompressziós index.
     */
    public int compressByOne(int compressionIndex){
        return compressionIndex;
    }

    /**
     * Ellenőrzi, hogy egy hókotró (SnowShovel) ráléphet-e erre a mezőállapotra.
     * @param v Az érkező hókotró.
     * @return true, ha a lépés érvényes, false egyébként.
     */
    public boolean acceptVehicle(SnowShovel v) {return true;}

    /**
     * Ellenőrzi, hogy egy busz (Bus) ráléphet-e erre a mezőállapotra.
     * @param v Az érkező busz.
     * @return true, ha a lépés érvényes, false egyébként.
     */
    public boolean acceptVehicle(Bus v) {return true;}

    /**
     * Ellenőrzi, hogy egy autó ráléphet-e erre a mezőállapotra.
     * Alapértelmezetten false, csak a tiszta/sekély havas mezőkre tudnak menni.
     * @param v Az érkező autó.
     * @return true, ha a lépés érvényes, false egyébként.
     */
    public boolean acceptVehicle(Car v) {return false;}

    /**
     * Útvonalkeresési kérés az entitások számára. Meghatározza a következő lépést egy cél felé.
     * @param position A kiindulási mező.
     * @param destination A célmező.
     * @param pf Az útvonalkereső (PathFinder) objektum.
     * @param isSalted Meg van-e sózva az aktuális mező.
     * @param isRubbled Van-e kőtörmelék az aktuális mezőn.
     * @return A következő mező (Tile), ahová lépni kell a cél felé.
     */
    public Tile requestPath(Tile position, Tile destination, PathFinder pf, boolean isSalted, boolean isRubbled){ return pf.calculateBFS(position, destination);}

    /**
     * Hozzáadja a mezőt az útvonalkereső (BFS) bejárási gráfjához.
     * Csak azok a mezők kerülnek be a gráfba, amiken az adott jármű át tud haladni.
     * @param subGraph A bejárható mezők listája.
     * @param tile A gráfhoz hozzáadni kívánt mező.
     */
    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){   }

    /**
     * Félresöpri a havat a jelenlegi mezőről a szomszédokra.
     * @param tile A mező, amiről a havat félre kell tolni.
     */
    public void sweepSnowToSide(Tile tile){    }

}
