package model.map.tilestates;

import model.shop.attachements.BlowerHead;
import model.shop.attachements.DragonHead;
import model.shop.attachements.SweeperHead;

public abstract class SnowyTileState extends TileState {
    @Override
    public TileState cleanedBy(SweeperHead swh) {
        return CleanTileState.getInstance();
    }

    @Override
    public TileState cleanedBy(BlowerHead bh) {
        return CleanTileState.getInstance();
    }

    @Override
    public TileState cleanedBy(DragonHead dh) {
        return CleanTileState.getInstance();
    }
}
