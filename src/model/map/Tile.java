package model.map;

import java.util.List;

import model.map.tilestates.*;
import model.players.IAutomatic;
import model.shop.attachements.*;
import model.vehicles.Bus;
import model.vehicles.Car;
import model.vehicles.SnowShovel;
import model.vehicles.Vehicle;
import skeleton.Skeleton;
import javax.xml.transform.Templates;

/**
 * A modellbeli mezo, a varos uthalozatanak egy egysege.
 * Felelossege tarolni az utallapotokat, szomszedokat es vegrehajtani az allapotvaltozasokat.
 * Asszociaciok: TileState (a mezo allapota), Tile (szomszedos mezok), Lane (sav, amihez tartozik), Vehicle (a rajta allo jarmu)
 */
public class Tile implements IAutomatic {

    /**
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos*/
    private final String name;

    /**
     * Jelzi, hogy a mezo fel lett-e sozva, ami megakadalyozza a ho megmaradasat, es olvasztja azt
     */
    private boolean isSalted;

    /**
     * A sekely ho jegge valo tomorodesenek elorehaladtat tartja szamon
     */
    private int compressionIndex;

    /**
     * A ho so altali olvadasanak elorehaladtat tartja szamon
     */
    private int saltMeltingIndex;

    /**
     * A mezo aktualis allapota
     */
    private TileState state;

    /**
     * A mezovel szomszedos mezok listaja
     */
    private List<Tile> neighbors;

    /**
     * A sav, amelyhez a mezo tartozik
     */
    private Lane lane;

    /**
     * A mezon tartozkodo jarmu
     */
    private Vehicle vehicle;


    /**
     * Konstruktor a Tile osztalyhoz.
     * @param state a mezo kezdeti allapota
     * @param lane a sav, amelyhez a mezo tartozik
     */
    public Tile(String name, TileState state, Lane lane) {
        this.name = name;
        this.state = state;
        this.lane = lane;
        this.isSalted = false;
        this.compressionIndex = 0;

    }

    /**
     * Visszaadja a mezo nevet.
     * Elorelathatolag csak a skeletonban kell
     * */
    public String getName() {
        return name;
    }

    /**
     * Beallitja a mezo allapotat.
     * @param state az uj TileState allapot
     */
    public void setState(TileState state) {
        this.state = state;
    }

    /**
     * Beallitja a mezo szomszedjait.
     * @param neighbors egy mezokbol allo lista
     */
    public void setNeighbors(List<Tile> neighbors) {
        this.neighbors = neighbors;
    }

    /**
     * Visszaadja a szomszedos mezoket.
     * @return a szomszedos Tile objektumok listaja
     */
    public List<Tile> getNeighbors() {
        return neighbors;
    }

    /**
     * Lezarja a savot, amelyhez a mezo tartozik. Balesetnel hivodik.
     * TODO
     */
    public void closeLane() {
        state = BlockedTileState.getInstance();
        //kell még a sávbezárás
    }

    /**
     * A hoeses generalasakor vagy olvadasakor hivja a rendszer.
     * Ha a mezo sozott, olvadast (snowMelt) kezdemenyez, ha nem, akkor hoesest (snowFall).
     */
    public void update() {
        if (isSalted) {
            state.snowMelt();
        } else {
            state.snowFall();
        }
    }

    /**
     * A soprofejtol erkezo sekely havat kezeli es delegalja az aktualis allapotnak.
     * @param ssts a kapott sekely ho allapot
     */
    public void acceptSweptSnow(ShallowSnowyTileState ssts) {
        this.state = state.acceptSweptSnow(ssts);
    }

    /**
     * A soprofejtol erkezo mely havat kezeli es delegalja az aktualis allapotnak.
     * @param dsts a kapott mely ho allapot
     */
    public void acceptSweptSnow(DeepSnowyTileState dsts) {
        this.state = state.acceptSweptSnow(dsts);
    }

    /**
     * Kezeli a parameterkent kapott jarmu mezore erkezeset, es delegalja a ho tomorodeset az allapotnak.
     * @param v az erkezo jarmu
     * @return a rajta allo jarmu (vagy null, ha nincs rajta)
     */

    public <T extends Vehicle> Vehicle acceptVehicle(T v) {
        if(vehicle != null)
            return vehicle;
        boolean isValid = state.acceptVehicle(v);

        if(isValid) {
            vehicle = v;
            state = state.acceptVehicle(compressionIndex);
            vehicle = v;
            return null;
        }
        else{
            return vehicle;
        }
    }

    /**
     * Kezeli, hogy a mozgo jarmu a szomszedos mezore szeretne lepni.
     * @param t a celmezo
     * @param v a mozgo jarmu
     * @return a jarmu, amivel utkozott (null, ha nem tortent utkozes)
     */
    public Vehicle moveToNeighbor(Tile t, Vehicle v) {
        boolean valid = Skeleton.askBoolQuestion("Érvényes-e a lépés?");
        if(valid){
            return t.acceptVehicle(v);
        }
        return v;
    }

    /**
     * Alapertelmezett takaritasi valasz sopresre.
     * @param a a hasznalt soprofej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(SweeperHead a) {
        if (state == null) return false;
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        this.state = newState;

        if (oldState != newState) {
            String[] options = new String[neighbors.size()];
            for (int i = 0; i < neighbors.size(); i++) {
                options[i] = neighbors.get(i).getName();
            }

            int listNumber = Skeleton.askListQuestion("Hova toljam a havat?", options);

            Tile targetTile = neighbors.get(listNumber - 1);

            if (oldState instanceof DeepSnowyTileState) {
                targetTile.acceptSweptSnow((DeepSnowyTileState) oldState);
            } else if (oldState instanceof ShallowSnowyTileState) {
                targetTile.acceptSweptSnow((ShallowSnowyTileState) oldState);
            }
        }

        this.state = newState;

        return oldState != newState;
    }

    /**
     * Alapertelmezett takaritasi valasz hanyasra.
     * @param a a hasznalt hanyofej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(BlowerHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    /**
     * Alapertelmezett takaritasi valasz jegtoresre.
     * @param a a hasznalt jegtoro fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(IcebreakerHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    /**
     * Alapertelmezett takaritasi valasz soszorasra. Felsolzza a mezot.
     * @param a a hasznalt soszoro fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(SalterHead a) {
        this.isSalted = true;
        return true;
    }

    /**
     * Alapertelmezett takaritasi valasz sarkany fejre.
     * @param a a hasznalt sarkany fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(DragonHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    //tesztelési inithez segédfüggvény
    public void setSalted(){
        isSalted = true;
    }

    public void setVehicle(Vehicle c1) {
        vehicle = c1;
    }
}
