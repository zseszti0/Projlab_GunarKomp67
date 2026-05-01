package model.map.tilestates;

import model.vehicles.Bus;
import model.vehicles.SnowShovel;

/**
 * A blokkolt mezoallapot (blokad). Utkozes eseten jon letre a savban.
 * Ebben az allapotban semmilyen hoeses vagy olvadas nem valtoztatja meg az allapotot.
 * Singleton tervezesi mintat hasznal.
 */
public class BlockedTileState extends TileState {
    private static final String name = "BlockedTileState";

    private static BlockedTileState instance;

    private BlockedTileState() {}

    /**
     * Visszaadja a BlockedTileState egyetlen peldanyat.
     * @return a BlockedTileState peldany
     */
    public static BlockedTileState getInstance() {
        if (instance == null) {
            instance = new BlockedTileState();
        }
        return instance;
    }

    /**
     * Kezeli a hoeses esemenyet. Blokkolt mezonel nem tortenik allapotvaltozas.
     * @return onmaga (BlockedTileState)
     */
    @Override
    public TileState snowFall() {
        return this;
    }

    /**
     * Kezeli a ho olvadasat. Blokkolt mezonel nem tortenik allapotvaltozas.
     * @return onmaga (BlockedTileState)
     */
    @Override
    public TileState snowMelt() {
        return this;
    }

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    @Override
    public boolean acceptVehicle(SnowShovel v) {return false;}

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    @Override
    public boolean acceptVehicle(Bus v) {return false;}
}
