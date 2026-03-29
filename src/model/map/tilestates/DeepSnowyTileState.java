package model.map.tilestates;

/**
 * A mely havas mezoallapot. A jarmuvek elakadhatnak benne.
 * Singleton tervezesi mintat hasznal.
 */
public class DeepSnowyTileState extends SnowyTileState {

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

}
