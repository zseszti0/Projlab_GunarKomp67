package model.map.tilestates;

import model.shop.attachements.*;

public abstract class TileState {

    public abstract TileState snowFall();
    public abstract TileState snowMelt();

    public TileState cleanedBy(SweeperHead swh){return this;}
    public TileState cleanedBy(BlowerHead bh){return this;}
    public TileState cleanedBy(IcebreakerHead ih){return this;}
    public TileState cleanedBy(SalterHead sh){return this;}
    public TileState cleanedBy(DragonHead dh){return this;}

    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return this;}
    public TileState acceptSweptSnow(DeepSnowyTileState dsts){return this;}

    public TileState acceptVehicle(int compressionIndex){return this;}
}
