package model.map.tilestates;

/**
 * A tiszta mezoallapot.
 * Singleton tervezesi mintat hasznal.
 */
public class CleanTileState extends TileState {
    private static final String name = "CleanTileState";

    private static CleanTileState instance;

    private CleanTileState() {}

    /**
     * Visszaadja a CleanTileState egyetlen peldanyat.
     * @return a CleanTileState peldany
     */
    public static CleanTileState getInstance() {
        if (instance == null) {
            instance = new CleanTileState();
        }
        return instance;
    }

    /**
     * Kezeli a hoesest. Tiszta mezore hullva a ho sekely havas allapotba valt.
     * @return sekely havas mezoallapot (ShallowSnowyTileState)
     */
    @Override
    public TileState snowFall() {
        return ShallowSnowyTileState.getInstance();
    }

    /**
     * Kezeli a ho olvadasat. Tiszta mezonel nincs ho, ami elolvadjon.
     * @return onmaga (CleanTileState)
     */
    @Override
    public TileState snowMelt() {
        return this;
    }

}
