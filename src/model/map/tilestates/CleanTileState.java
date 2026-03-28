package model.map.tilestates;


public class CleanTileState extends TileState {

    private static CleanTileState instance;

    private CleanTileState() {}

    public static CleanTileState getInstance() {
        if (instance == null) {
            instance = new CleanTileState();
        }
        return instance;
    }

    @Override
    public TileState snowFall() {
        return ShallowSnowyTileState.getInstance();
    }

    @Override
    public TileState snowMelt() {
        return this;
    }

}
