package model.map.tilestates;

import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;

/**
 * A mely havas mezoallapot. A jarmuvek elakadhatnak benne.
 * Singleton tervezesi mintat hasznal.
 */
public class DeepSnowyTileState extends SnowyTileState {
    private static final String name = "DeepSnowyTileState";

    private static DeepSnowyTileState instance;

    private DeepSnowyTileState() {}

    /**
     * Visszaadja a DeepSnowyTileState egyetlen peldanyat.
     * @return a DeepSnowyTileState peldany
     */
    public static DeepSnowyTileState getInstance() {
        return instance == null ? instance = new DeepSnowyTileState() : instance;
    }

    /**
     * Kezeli a hoesest. Mely havas mezon a hoeses nem valtoztat az allapoton.
     * @return onmaga (DeepSnowyTileState)
     */
    @Override
    public TileState snowFall() {
        return this;
    }

    /**
     * Kezeli a ho olvadasat. A mely ho elolvadasaval a mezo sekely havas allapotba kerul.
     * @return sekely havas mezoallapot (ShallowSnowyTileState)
     */
    @Override
    public TileState snowMelt() {
        return ShallowSnowyTileState.getInstance();
    }

    @Override
    public void sweepSnowToSide(Tile tile){
        tile.acceptSweptSnow(DeepSnowyTileState.getInstance());
    }


    /**
     * Kezeli a szomszédos mezőről átsöpört (vagy fújt) sekély hó érkezését.
     * @param ssts A beérkező sekély hó állapot példánya.
     * @return Az új mezőállapot a hó érkezése után.
     */
    @Override
    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return DeepSnowyTileState.getInstance();}
    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    @Override
    public boolean acceptVehicle(Bus v) {return false;}
}
