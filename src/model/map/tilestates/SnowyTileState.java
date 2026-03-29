package model.map.tilestates;

import model.shop.attachements.BlowerHead;
import model.shop.attachements.DragonHead;
import model.shop.attachements.SweeperHead;

/**
 * A havas mezoallapotok (sekely es mely ho) kozos ose.
 * Felelossege a kozos takaritasi esemenyek lekezelese.
 */
public abstract class SnowyTileState extends TileState {
    /**
     * Kezeli a soprofej (SweeperHead) altali tisztitast.
     * A soprofej a havas mezoket tisztava alakitja.
     * @param swh a tisztitast vegzo soprofej
     * @return a tiszta mezoallapot (CleanTileState)
     */
    @Override
    public TileState cleanedBy(SweeperHead swh) {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a hanyofej (BlowerHead) altali tisztitast.
     * A hanyofej a havas mezoket tisztava alakitja.
     * @param bh a tisztitast vegzo hanyofej
     * @return a tiszta mezoallapot (CleanTileState)
     */
    @Override
    public TileState cleanedBy(BlowerHead bh) {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a sarkany fej (DragonHead) altali tisztitast.
     * A sarkany fej a havas mezoket tisztava alakitja.
     * @param dh a tisztitast vegzo sarkany fej
     * @return a tiszta mezoallapot (CleanTileState)
     */
    @Override
    public TileState cleanedBy(DragonHead dh) {
        return CleanTileState.getInstance();
    }
}
