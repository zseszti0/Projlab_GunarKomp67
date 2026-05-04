package model.map.tilestates;


import model.map.Tile;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import java.util.List;

/**
 * A sekély havas mezőállapot. Ebben az állapotban a járművek át tudnak haladni, de tömöríthetik a havat, amiből így jég (Icy) lehet.
 * Singleton tervezési mintát használ.
 */
public class ShallowSnowyTileState extends SnowyTileState {
    private static final String name = "ShallowSnowyTileState";

    private static ShallowSnowyTileState instance;

    private ShallowSnowyTileState() {}

    /**
     * Visszaadja a ShallowSnowyTileState egyetlen példányát.
     * @return A ShallowSnowyTileState singleton példány.
     */
    public static ShallowSnowyTileState getInstance() {return instance==null?instance=new ShallowSnowyTileState():instance;}


    /**
     * Kezeli a szomszédos mezőről átsöpört (vagy fújt) sekély hó érkezését.
     * @param ssts A beérkező sekély hó állapot példánya.
     * @return Az új mezőállapot a hó érkezése után.
     */
    @Override
    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return DeepSnowyTileState.getInstance();}
    /**
     * Kezeli a hóesést. Sekély hóval borított mezőre hullva a hó mély havas (DeepSnowy) állapotba vált.
     * @return A mély havas mezőállapot (DeepSnowyTileState).
     */
    @Override
    public TileState snowFall() {
        return DeepSnowyTileState.getInstance();
    }

    /**
     * Kezeli a hó olvadását. A sekély hó elolvadásával a mező tiszta állapotba vált.
     * @return A tiszta mezőállapot (CleanTileState).
     */
    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    /**
     * Hozzáadja a sekély havas mezőt az útvonalkereső gráfhoz.
     * @param subGraph A bejárható mezők listája.
     * @param tile A gráfhoz hozzáadandó mező.
     */
    @Override
    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){
        subGraph.add(tile);
    }

    /**
     * Félretolja a havat a szomszédos mezőkre.
     * @param tile Az a mező, amiről a havat letolják.
     */
    @Override
    public void sweepSnowToSide(Tile tile){
        tile.acceptSweptSnow(ShallowSnowyTileState.getInstance());
    }

    /**
     * Engedélyezi egy autó rálépését a sekély havas mezőre.
     * @param v Az érkező autó.
     * @return true (a lépés érvényes).
     */
    @Override
    public boolean acceptVehicle(Car v) {return true;}

    /**
     * Kezeli a jármű áthaladását, ami a hó tömörödését (jégesedést) eredményezheti.
     * @return A jeges mezőállapot (IcyTileState) a jármű áthaladása után.
     */
    @Override
    public TileState compressionReached(){return IcyTileState.getInstance();}

    /**
     * Növeli a kompressziós (tömörödési) indexet a jármű áthaladásakor.
     * @param compressionIndex Az eddigi kompressziós érték.
     * @return A megnövelt kompressziós érték.
     */
    @Override
    public int compressByOne(int compressionIndex){
        return compressionIndex + 1;
    }
}
