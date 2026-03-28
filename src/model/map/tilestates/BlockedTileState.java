package model.map.tilestates;

public class BlockedTileState extends TileState {
    private static BlockedTileState instance;
    private BlockedTileState() {}
    public static BlockedTileState getInstance() {
        if (instance == null) {
            instance = new BlockedTileState();
        }
        return instance;
    }

    @Override
    public TileState snowFall() {
        return this;
    }

    @Override
    public TileState snowMelt() {
        return this;
    }
}
