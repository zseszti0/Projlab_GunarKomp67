package model.map.tilestates;

import model.map.Tile;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import java.util.List;

/**
 * A tiszta mezőállapot. Sem hó, sem jég nincs a felületen.
 * Singleton tervezési mintát használ.
 */
public class CleanTileState extends TileState {
    private static final String name = "CleanTileState";

    private static CleanTileState instance;

    private CleanTileState() {}

    /**
     * Visszaadja a CleanTileState egyetlen példányát.
     * @return A CleanTileState singleton példány.
     */
    public static CleanTileState getInstance() {
        if (instance == null) {
            instance = new CleanTileState();
        }
        return instance;
    }

    /**
     * Kezeli a hóesést. Tiszta mezőre hullva a hó sekély havas állapotba vált.
     * @return A sekély havas mezőállapot (ShallowSnowyTileState).
     */
    @Override
    public TileState snowFall() {
        return ShallowSnowyTileState.getInstance();
    }

    /**
     * Kezeli a hó olvadását. Tiszta mezőnél nincs hó, ami elolvadjon, így nem változik.
     * @return Önmagával (CleanTileState) tér vissza.
     */
    @Override
    public TileState snowMelt() {
        return this;
    }

    /**
     * Hozzáadja a tiszta mezőt az útvonalkereső gráfhoz, mivel a tiszta felületen minden jármű áthaladhat.
     * @param subGraph A bejárható mezők listája.
     * @param tile A gráfhoz hozzáadandó mező.
     */
    @Override
    public void addToBFSSubGraph(List<Tile> subGraph, Tile tile){
        subGraph.add(tile);
    }

    /**
     * Engedélyezi egy normál autó (Car) rálépését a tiszta mezőre.
     * @param v Az érkező autó.
     * @return true (a lépés érvényes).
     */
    @Override
    public boolean acceptVehicle(Car v) {return true;}
}
