package model.map.tilestates;

public class ShallowSnowyTileState extends SnowyTileState {
    private static ShallowSnowyTileState instance;

    private ShallowSnowyTileState() {}

    public static ShallowSnowyTileState getInstance() {return instance==null?instance=new ShallowSnowyTileState():instance;}

    @Override
    public TileState snowFall() {
        return DeepSnowyTileState.getInstance();
    }

    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    @Override
    public TileState acceptVehicle(int compressionIndex) {
        return IcyTileState.getInstance();
    }
}
