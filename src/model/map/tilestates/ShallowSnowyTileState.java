package model.map.tilestates;


import model.map.Tile;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import java.util.List;

/**
 * A sekely havas mezoallapot. Ebben az allapotban a jarmuvek at tudnak haladni, de tomorithetik a havat.
 * Singleton tervezesi mintat hasznal.
 */
public class ShallowSnowyTileState extends SnowyTileState {
    private static final String name = "ShallowSnowyTileState";

    private static ShallowSnowyTileState instance;

    private ShallowSnowyTileState() {}

    /**
     * Visszaadja a ShallowSnowyTileState egyetlen peldanyat.
     * @return a ShallowSnowyTileState peldany
     */
    public static ShallowSnowyTileState getInstance() {return instance==null?instance=new ShallowSnowyTileState():instance;}

    /**
     * Kezeli a hoesest. Sekely hoval boritott mezore hullva a ho mely havas allapotba valt.
     * @return mely havas mezoallapot (DeepSnowyTileState)
     */
    @Override
    public TileState snowFall() {
        return DeepSnowyTileState.getInstance();
    }

    /**
     * Kezeli a ho olvadasat. A sekely ho elolvadasaval a mezo tiszta allapotba valt.
     * @return tiszta mezoallapot (CleanTileState)
     */
    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    @Override
    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){
        subGraph.add(tile);
    }

    @Override
    public void sweepSnowToSide(Tile tile){
        tile.acceptSweptSnow(ShallowSnowyTileState.getInstance());
    }

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    @Override
    public boolean acceptVehicle(Car v) {return true;}

    /**
     * Kezeli a jarmu mezore erkezeset, ami a ho tomorodeset eredmenyezheti.
     * @return az uj mezoallapot a jarmu athaladasa utan
     */
    @Override
    public TileState compressionReached(){return IcyTileState.getInstance();}

    @Override
    public int compressByOne(int compressionIndex){
        return compressionIndex + 1;
    }
}
