package model.map.tilestates;

import model.shop.attachements.IcebreakerHead;

public class IcyTileState extends TileState {

    private static IcyTileState instance;
    private IcyTileState() {}
    public static IcyTileState getInstance() {
        if (instance == null) {
            instance = new IcyTileState();
        }
        return instance;
    }

    @Override
    public TileState snowFall() {
        return this;
    }

    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    @Override
    public TileState cleanedBy(IcebreakerHead ih) {
        return ShallowSnowyTileState.getInstance();
    }
}
