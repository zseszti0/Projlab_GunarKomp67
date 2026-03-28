package model.map.tilestates;

import model.shop.attachements.SweeperHead;

public class CleanTileState extends TileState {

    public TileState cleanTile(SweeperHead swh){return new CleanTileState();}
}
