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
 * A modellbeli mezoallapotok kozos absztrakt ose (State tervezesi minta).
 * Felelossege a mezo aktualis allapotanak tarolasa es az allapotvaltozasok kezelese.
 * Az allapotvaltozasok a kovetkezok: hoeses, olvadas, a sekely ho osszenyomodasa (jarmu altal),
 * es a kulonbozo hokotrok (fejek) altali tisztitas.
 * Alapertelmezesben minden metodus onmagaval ter vissza (nincs allapotvaltozas),
 * a leszarmazottak csak a rajuk jellemzo valtozasokat definialjak felul.
 */
public abstract class TileState {

    /**
     * Kezeli a hoeses esemenyet.
     * @return az uj mezoallapot hoeses utan
     */
    public abstract TileState snowFall();

    /**
     * Kezeli a ho olvadasat (pl.: ha a mezo sozva van vagy olvadast valt ki a jatek).
     * @return az uj mezoallapot olvadas utan
     */
    public abstract TileState snowMelt();


    /**
     * Kezeli a soprofej (SweeperHead) altali tisztitast.
     * @param swh a tisztitast vegzo soprofej
     * @return az uj mezoallapot tisztitas utan
     */
    public TileState cleanedBy(SweeperHead swh) {return this;}

    /**
     * Kezeli a hanyofej (BlowerHead) altali tisztitast.
     * @param bh a tisztitast vegzo hanyofej
     * @return az uj mezoallapot tisztitas utan
     */
    public TileState cleanedBy(BlowerHead bh) {return this;}

    /**
     * Kezeli a jegtoro fej (IcebreakerHead) altali tisztitast.
     * @param ih a tisztitast vegzo jegtoro fej
     * @return az uj mezoallapot tisztitas utan
     */
    public TileState cleanedBy(IcebreakerHead ih) {return this;}


    /**
     * Kezeli a sarkany fej (DragonHead) altali tisztitast.
     * @param dh a tisztitast vegzo sarkany fej
     * @return az uj mezoallapot tisztitas utan
     */
    public TileState cleanedBy(DragonHead dh) {return this;}


    /**
     * Kezeli a soprofejtol erkezo sekely havat (ha a szomszedbol atfujtak).
     * @param ssts a kapott sekely ho allapot
     * @return az uj mezoallapot a ho erkezese utan
     */
    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return this;}

    /**
     * Kezeli a soprofejtol erkezo mely havat (ha a szomszedbol atfujtak).
     * @param dsts a kapott mely ho allapot
     * @return az uj mezoallapot a ho erkezese utan
     */
    public TileState acceptSweptSnow(DeepSnowyTileState dsts) {return this;}

    /**
     * Kezeli a jarmu mezore erkezeset, ami a ho tomorodeset eredmenyezheti.
     * @return az uj mezoallapot a jarmu athaladasa utan
     */
    public TileState compressionReached(){return this;}

    public int compressByOne(int compressionIndex){
        return compressionIndex;
    }

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    public boolean acceptVehicle(SnowShovel v) {return true;}

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    public boolean acceptVehicle(Bus v) {return true;}

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    public boolean acceptVehicle(Car v) {return false;}

    public Tile requestPath(Tile position, Tile destination, PathFinder pf, boolean isSalted, boolean isRubbled){ return pf.calculateBFS(position, destination);}

    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){   }

    public void sweepSnowToSide(Tile tile){    }

}
