package model.map;

import java.util.List;

import model.map.tilestates.*;
import model.players.IAutomatic;
import model.shop.attachements.*;
import model.vehicles.Bus;
import model.vehicles.Car;
import model.vehicles.SnowShovel;
import model.vehicles.Vehicle;

/**
 * A modellbeli mező, a város úthálózatának egy egysége.
 * Felelőssége tárolni az útállapotokat, szomszédokat és végrehajtani az állapotváltozásokat.
 * Asszociációk: TileState (a mező állapota), Tile (szomszédos mezők), Lane (sáv, amihez tartozik), Vehicle (a rajta álló jármű)
 */
public class Tile implements IAutomatic {

    /**
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos*/
    private final String name;

    /**
     * Jelzi, hogy a mezo fel lett-e sozva, ami megakadalyozza a ho megmaradasat, es olvasztja azt
     */
    private boolean isSalted;

    private boolean isRubbled;

    /**
     * A sekely ho jegge valo tomorodesenek elorehaladtat tartja szamon
     */
    private int compressionIndex;

    /**
     * A ho so altali olvadasanak elorehaladtat tartja szamon
     */
    private int saltMeltingIndex;

    /**
     * A ho zuzalek fade index (rubble fading)
     */
    private int rubbleFadingIndex;

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
    private List<Lane> lanes;

    /**
     * A mezon tartozkodo jarmu
     */
    private Vehicle vehicle;


    /**
     * Konstruktor a Tile osztályhoz.
     * Beállítja a nevet, az állapotot és a sávokat.
     * A sótlan állapotot (`isSalted = false`) és a tömörödési indexet (`compressionIndex = 0`) alaphelyzetbe állítja.
     * @param name a mező azonosító neve
     * @param state a mező kezdeti állapota
     * @param lanes a sávok listája, amelyhez a mező tartozik
     */
    public Tile(String name, TileState state, List<Lane> lanes) {
        this.name = name;
        this.state = state;
        this.lanes = lanes;
        this.isSalted = false;
        this.compressionIndex = 0;

    }

    /**
     * Teljes konstruktor, amit az XML parser használ.
     * Az összes belső attribútumot közvetlenül inicializálja a paraméterek alapján.
     * Ha a kapott `state` null, akkor automatikusan a `CleanTileState.getInstance()`-t állítja be.
     */
    public Tile(String name, TileState state, boolean isSalted, boolean isRubbled,
                int compressionIndex, int saltMeltingIndex, int rubbleFadingIndex,
                List<Tile> neighbors, List<Lane> lanes) {
        this.name = name;
        this.state = state != null ? state : CleanTileState.getInstance();
        this.isSalted = isSalted;
        this.isRubbled = isRubbled;
        this.compressionIndex = compressionIndex;
        this.saltMeltingIndex = saltMeltingIndex;
        this.rubbleFadingIndex = rubbleFadingIndex;
        this.neighbors = neighbors;
        this.lanes = lanes;
    }

    /**
     * Visszaadja a mezo nevet.
     * Elorelathatolag csak a skeletonban kell
     * */
    public String getName() {
        return name;
    }

    public boolean isSalted(){
        return isSalted;
    }

    public boolean isRubbled(){
        return isRubbled;
    }

    public int getCompressionIndex(){
        return compressionIndex;
    }

    public int getSaltMeltingIndex(){
        return saltMeltingIndex;
    }

    public int getRubbleFadingIndex(){
        return rubbleFadingIndex;
    }

    public TileState getState(){
        return state;
    }

    public List<Lane> getLanes(){
        return lanes;
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
        for(Lane lane : lanes)
            lane.blockAllTilesInLane();
    }
    public void blockTile(){
        state = BlockedTileState.getInstance();
    }

    /**
     * A hoeses generalasakor vagy olvadasakor hivja a rendszer.
     * Ha a mezo sozott, olvadast (snowMelt) kezdemenyez, ha nem, akkor hoesest (snowFall).
     */
    public void update() {
        if (isSalted) {
            state = state.snowMelt();
        } else {
            state = state.snowFall();

            if(isRubbled) {
                rubbleFadingIndex = (rubbleFadingIndex + 1) % 2;
                if (rubbleFadingIndex == 0)
                    isRubbled = false;
            }
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
    public Vehicle acceptVehicle(Vehicle v) {
        if(vehicle != null)
            return vehicle;
        boolean isValid = v.goToTile(this);

        if(isValid) {
            vehicle = v;
            return null;
        }
        else{
            return v;
        }
    }
    public boolean acceptVehicle(SnowShovel v){
        return state.acceptVehicle(v);
    }
    public boolean acceptVehicle(Bus v){
        return state.acceptVehicle(v);
    }
    public boolean acceptVehicle(Car v){
        if(state.acceptVehicle(v)){
            compressionIndex = state.compressByOne(compressionIndex);
            if(!isSalted && compressionIndex >= 2){
                TileState oldState = this.state;
                TileState newState = state.compressionReached();
                if(oldState != newState){
                    compressionIndex = 0;
                }
            }
        }
        return false;
    }

    /**
     * Kezeli, hogy a mozgo jarmu a szomszedos mezore szeretne lepni.
     * @param t a celmezo
     * @param v a mozgo jarmu
     * @return a jarmu, amivel utkozott (null, ha nem tortent utkozes)
     */
    public Vehicle moveToNeighbor(Tile t, Vehicle v) {
        boolean valid = neighbors.contains(t);
        if(valid){
            Vehicle collided = t.acceptVehicle(v);
            if(collided == null){
                vehicle = null;
            }
            return collided;
        }
        return v;
    }

    /**
     * Alapertelmezett takaritasi valasz sopresre.
     * @param a a hasznalt soprofej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(SweeperHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;

        boolean stateChanged = oldState != newState;
        if(stateChanged){
            oldState.sweepSnowToSide(neighbors.get(0));
        }
        if(isRubbled){
            isRubbled = false;
            rubbleFadingIndex = 0;
            neighbors.get(0).sweepRubble();
        }

        return stateChanged;
    }

    private void sweepRubble(){
        isRubbled = true;
        rubbleFadingIndex = 0;
    }

    /**
     * Alapertelmezett takaritasi valasz hanyasra.
     * @param a a hasznalt hanyofej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(BlowerHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;
        isRubbled = false;

        return oldState != newState;
    }

    /**
     * Alapertelmezett takaritasi valasz jegtoresre.
     * @param a a hasznalt jegtoro fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(IcebreakerHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;

        return oldState != newState;
    }

    /**
     * Alapertelmezett takaritasi valasz soszorasra. Felsolzza a mezot.
     * @param a a hasznalt soszoro fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(SalterHead a) {
        this.isSalted = true;
        return false;
    }

    /**
     * Alapertelmezett takaritasi valasz soszorasra. Felzúzottkövezi a mezot.
     * @param a a hasznalt zuzalék szóró fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(CobblestoneHead a) {
        this.isRubbled = true;
        rubbleFadingIndex = 0;
        return false;
    }

    /**
     * Alapertelmezett takaritasi valasz sarkany fejre.
     * @param a a hasznalt sarkany fej
     * @return true, ha tortent allapotvaltozas
     */
    public boolean cleanTile(DragonHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;

        return oldState != newState;
    }

    //tesztelési inithez segédfüggvény
    public void setSalted(){
        isSalted = true;
    }

    public void setVehicle(Vehicle c1) {
        vehicle = c1;
    }

    public Tile requestPath(Tile position, Tile destination, PathFinder pf){
        return state.requestPath(position, destination, pf, isSalted, isRubbled);
    }

    public Tile getSlipTarget(){
        Tile step1 = neighbors.get(0);

        for (Tile neighbor : neighbors) {
            step1 = neighbor;
            for(Lane lane : lanes) {
                for(Lane neighborLane : neighbor.lanes) {
                    if (neighborLane == lane)
                        break;
                }
            }
        }
        Tile step2 = step1.neighbors.get(0);
        for (Tile neighbor : step1.neighbors) {
            step2 = neighbor;
            for(Lane lane : lanes) {
                for(Lane neighborLane : neighbor.lanes) {
                    if (neighborLane == lane)
                        break;
                }
            }
        }
        return step2;
    }

    public void addToBFSSubGraph(List<Tile> subGraph){
        state.addToBFSSubGraph(subGraph, this);
    }
}
