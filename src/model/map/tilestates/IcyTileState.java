package model.map.tilestates;

import model.map.PathFinder;
import model.map.Tile;
import model.shop.attachements.IcebreakerHead;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import java.util.List;


/**
 * A jeges mezoallapot. Sekely hobol jarmu athaladasakor keletkezik, es csuszasveszelyes a rajta haladok szamara.
 * Singleton tervezesi mintat hasznal.
 */
public class IcyTileState extends TileState {
    private static final String name = "IcyTileState";

    private static IcyTileState instance;

    private IcyTileState() {}

    /**
     * Visszaadja a IcyTileState egyetlen példányát.
     * Lépései:
     * 1. Ha az instance null, létrehoz egy új IcyTileState-et.
     * 2. Visszatér a singleton példánnyal.
     *
     * @return A IcyTileState példány.
     */
    public static IcyTileState getInstance() {
        if (instance == null) {
            instance = new IcyTileState();
        }
        return instance;
    }

    /**
     * Kezeli a hóesést.
     * Lépései:
     * 1. Jeges mezőn a hóesés nem változtat az állapoton, az jég marad.
     * 2. Visszatér önmagával.
     *
     * @return Önmagával (IcyTileState) tér vissza.
     */
    @Override
    public TileState snowFall() {
        return this;
    }

    /**
     * Kezeli a jég olvadását.
     * Lépései:
     * 1. A jég elolvadásával a mező egyből tiszta állapotba kerül.
     * 2. Visszaadja a CleanTileState példányt.
     *
     * @return Tiszta mezőállapot (CleanTileState).
     */
    @Override
    public TileState snowMelt() {
        return CleanTileState.getInstance();
    }

    /**
     * Kezeli a jégtörő fej (IcebreakerHead) általi tisztítást.
     * Lépései:
     * 1. A jégtörő fej feltöri a jeget.
     * 2. A mező állapota sekély havassá (ShallowSnowy) válik.
     *
     * @param ih A tisztítást végző jégtörő fej.
     * @return Sekély havas mezőállapot (ShallowSnowyTileState).
     */
    @Override
    public TileState cleanedBy(IcebreakerHead ih) {
        return ShallowSnowyTileState.getInstance();
    }

    /**
     * Kiszámítja az útvonalat vagy lekezeli a csúszást.
     * Lépései:
     * 1. Ellenőrzi, hogy a mező le van-e sózva vagy fel van-e szórva törmelékkel.
     * 2. Ha igen, akkor nincs csúszásveszély, és a PathFinder segítségével kikeresi a következő érvényes mezőt.
     * 3. Ha nincs sem sózva, sem szórva, akkor a jármű megcsúszik, és a mező getSlipTarget() metódusával visszaadott célmezőt adja vissza.
     *
     * @param position A kiindulási mező.
     * @param destination A célmező.
     * @param pf Az útvonalkereső (PathFinder) objektum.
     * @param isSalted Meg van-e sózva a mező.
     * @param isRubbled Van-e kőtörmelék a mezőn.
     * @return A következő mező, ahová a jármű kerül (irányított haladás vagy csúszás eredményeként).
     */
    @Override
    public Tile requestPath(Tile position, Tile destination, PathFinder pf, boolean isSalted, boolean isRubbled){
        if(isSalted || isRubbled)
            return pf.calculateBFS(position, destination);
        return position.getSlipTarget();
    }

    /**
     * Hozzáadja a jeges mezőt az útvonalkereső gráfhoz.
     * Lépései:
     * 1. Hozzáadja az aktuális mezőt a bejárható gráf listájához.
     *
     * @param subGraph A bejárható mezők listája.
     * @param tile A gráfhoz hozzáadandó mező.
     */
    @Override
    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){
        subGraph.add(tile);
    }

    /**
     * Kezeli a jarmu mezore erkezeset, jelez ha érvénytelen lépés
     * @param v az érkező jármű
     * @return elfogadta-e a járművet
     */
    @Override
    public boolean acceptVehicle(Car v) {return false;}
}