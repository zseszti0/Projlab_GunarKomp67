package model.map.tilestates;

import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;

/**
 * A mély havas mezőállapot. A nehéz járművek (autók, buszok) elakadhatnak benne,
 * csak a hókotrók tudják normálisan megtisztítani.
 * Singleton tervezési mintát használ.
 */
public class DeepSnowyTileState extends SnowyTileState {
    private static final String name = "DeepSnowyTileState";

    private static DeepSnowyTileState instance;

    private DeepSnowyTileState() {}

    /**
     * Visszaadja a DeepSnowyTileState egyetlen példányát.
     * Lépései:
     * 1. Ellenőrzi, ha nem létezik példány, létrehoz egyet.
     * 2. Visszatér az egyetlen példánnyal.
     *
     * @return A DeepSnowyTileState singleton példány.
     */
    public static DeepSnowyTileState getInstance() {
        return instance == null ? instance = new DeepSnowyTileState() : instance;
    }

    /**
     * Kezeli a hóesést. Mély havas mezőn a hóesés már nem változtat az állapoton (nem lesz még mélyebb).
     * @return Önmagával (DeepSnowyTileState) tér vissza.
     */
    @Override
    public TileState snowFall() {
        return this;
    }

    /**
     * Kezeli a hó olvadását. A mély hó elolvadásával a mező egy szinttel lejjebb, sekély havas állapotba kerül.
     * @return A sekély havas mezőállapot (ShallowSnowyTileState).
     */
    @Override
    public TileState snowMelt() {
        return ShallowSnowyTileState.getInstance();
    }

    /**
     * Félretolja a mély havat a szomszédokra.
     * @param tile A mező, amiről a havat tolják.
     */
    @Override
    public void sweepSnowToSide(Tile tile){
        tile.acceptSweptSnow(DeepSnowyTileState.getInstance());
    }


    /**
     * Kezeli a szomszédos mezőről átsöpört (vagy fújt) sekély hó érkezését.
     * @param ssts A beérkező sekély hó állapot példánya.
     * @return Az új mezőállapot a hó érkezése után.
     */
    @Override
    public TileState acceptSweptSnow(ShallowSnowyTileState ssts) {return DeepSnowyTileState.getInstance();}
    /**
     * Megakadályozza, hogy egy busz áthaladjon a mély havas mezőn.
     * @param v Az érkező busz.
     * @return false (a lépés érvénytelen/blokkolt).
     */
    @Override
    public boolean acceptVehicle(Bus v) {return false;}
}
