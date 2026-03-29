package model.map.tilestates;

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

    /**
     * Kezeli a jarmu erkezeset. A jarmu athaladasa a sekely havas mezon jegesse alakitja azt.
     * @param compressionIndex a ho tomorodesenek merteke
     * @return jeges mezoallapot (IcyTileState)
     */
    @Override
    public TileState acceptVehicle(int compressionIndex) {
        return IcyTileState.getInstance();
    }
}
