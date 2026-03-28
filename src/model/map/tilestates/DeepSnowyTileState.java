package model.map.tilestates;


public class DeepSnowyTileState extends SnowyTileState {
    private static DeepSnowyTileState instance;
    public static DeepSnowyTileState getInstance() {
        return instance == null ? instance = new DeepSnowyTileState() : instance;
    }
    private DeepSnowyTileState() {}


    @Override
    public TileState snowFall() {
        return this;
    }

    @Override
    public TileState snowMelt() {
        return ShallowSnowyTileState.getInstance();
    }

}
