package model.map.tilestates;

import model.shop.attachements.BlowerHead;
import model.shop.attachements.DragonHead;
import model.shop.attachements.SweeperHead;
import model.vehicles.SnowShovel;
import model.vehicles.Vehicle;

/**
 * A havas mezőállapotok (sekély és mély hó) közös absztrakt őse.
 * Felelőssége a közös takarítási események lekezelése, amelyek mindkét típusú havon működnek.
 */
public abstract class SnowyTileState extends TileState {
    private static final String name = "SnowyTileState";

    /**
     * Kezeli a söprőfej (SweeperHead) általi tisztítást.
     * A söprőfej a havas mezőket tisztává alakítja.
     * @param swh A tisztítást végző söprőfej.
     * @return A tiszta mezőállapot (CleanTileState).
     */
    @Override
    public TileState cleanedBy(SweeperHead swh) {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a hóhányófej (BlowerHead) általi tisztítást.
     * A hóhányófej szintén tisztává alakítja a havas mezőket.
     * @param bh A tisztítást végző hóhányófej.
     * @return A tiszta mezőállapot (CleanTileState).
     */
    @Override
    public TileState cleanedBy(BlowerHead bh) {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a sárkányfej (DragonHead) általi tisztítást (olvasztást).
     * A hő hatására a hó eltűnik, a mező tisztává válik.
     * @param dh A tisztítást végző sárkányfej.
     * @return A tiszta mezőállapot (CleanTileState).
     */
    @Override
    public TileState cleanedBy(DragonHead dh) {
        return CleanTileState.getInstance();
    }

}
