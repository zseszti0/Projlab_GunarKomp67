package model.map.tilestates;

import model.shop.attachements.IcebreakerHead;

/**
 * A jeges mezoallapot. Sekely hobol jarmu athaladasakor keletkezik, es csuszasveszelyes a rajta haladok szamara.
 * Singleton tervezesi mintat hasznal.
 */
public class IcyTileState extends TileState {

    private static IcyTileState instance;

    private IcyTileState() {}

    /**
     * Visszaadja a IcyTileState egyetlen peldanyat.
     * @return a IcyTileState peldany
     */
    public static IcyTileState getInstance() {
        if (instance == null) {
            instance = new IcyTileState();
        }
        return instance;
    }

    /**
     * Kezeli a hoesest. Jeges mezon a hoeses nem valtoztat az allapoton, jeges marad.
     * @return onmaga (IcyTileState)
     */
    @Override
    public TileState snowFall() {
        return this;
    }

    /**
     * Kezeli a ho (jeg) olvadasat. A jeg elolvadasaval a mezo tiszta allapotba kerul.
     * @return tiszta mezoallapot (CleanTileState)
     */
    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a jegtoro fej (IcebreakerHead) altali tisztitast.
     * A jegtoro feltori a jeget, ami igy sekely havassa valik.
     * @param ih a tisztitast vegzo jegtoro fej
     * @return sekely havas mezoallapot (ShallowSnowyTileState)
     */
    @Override
    public TileState cleanedBy(IcebreakerHead ih) {
        return ShallowSnowyTileState.getInstance();
    }
}