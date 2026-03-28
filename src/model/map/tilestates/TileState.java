package model.map.tilestates;

import model.shop.attachements.*;

public abstract class TileState {

    public abstract TileState snowFall();
    public abstract TileState snowMelt();

    public TileState cleanTile(SweeperHead swh){return this;}
    public TileState cleanTile(BlowerHead bh){return this;}
    public TileState cleanTile(IcebreakerHead ih){return this;}
    public TileState cleanTile(SalterHead sh){return this;}
    public TileState cleanTile(DragonHead dh){return this;}

    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return this;}
    public TileState acceptSweptSnow(DeepSnowyTileState dsts){return this;}

    public abstract TileState acceptVehicle(int compressionIndex);
}
