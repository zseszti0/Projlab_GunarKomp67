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
     * Lépései:
     * 1. Beállítja a nevet, az állapotot és a sávokat.
     * 2. A sótlan állapotot (`isSalted = false`) és a tömörödési indexet (`compressionIndex = 0`) alaphelyzetbe állítja.
     *
     * @param name A mező azonosító neve.
     * @param state A mező kezdeti állapota.
     * @param lanes A sávok listája, amelyhez a mező tartozik.
     */
    public Tile(String name, TileState state, List<Lane> lanes) {
        this.name = name;
        this.state = state;
        this.lanes = lanes;
        this.isSalted = false;
        this.compressionIndex = 0;

    }

    /**
     * Teljes konstruktor, amit az XML parser használ játékállás betöltésekor.
     * Lépései:
     * 1. Az összes belső attribútumot közvetlenül inicializálja a paraméterek alapján.
     * 2. Ha a kapott `state` null, akkor automatikusan egy tiszta mezőállapotot (CleanTileState) állít be.
     *
     * @param name A mező neve.
     * @param state A mező állapota.
     * @param isSalted Sózott-e a mező.
     * @param isRubbled Kőzúzalékkal felszórt-e a mező.
     * @param compressionIndex A hó tömörödési szintje.
     * @param saltMeltingIndex A só olvasztási fázisa.
     * @param rubbleFadingIndex A zúzalék kopási fázisa.
     * @param neighbors A szomszédos mezők listája.
     * @param lanes A sávok listája, amelyekhez tartozik.
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
     * Lezárja a sávot, amelyhez a mező tartozik. Balesetnél hívódik.
     * Lépései:
     * 1. Végigmegy az összes sávon (Lane), amelynek ez a mező a része.
     * 2. Minden sávon meghívja a blockAllTilesInLane() metódust, ami a sáv összes mezőjét lezárja.
     */
    public void closeLane() {
        for(Lane lane : lanes)
            lane.blockAllTilesInLane();
    }

    /**
     * Lezárja az adott mezőt, állapotát BlockedTileState-re cserélve.
     * Lépései:
     * 1. A mező állapotát beállítja a BlockedTileState egyetlen (Singleton) példányára.
     */
    public void blockTile(){
        state = BlockedTileState.getInstance();
    }

    /**
     * A játékidő múlását (Tick) kezeli a mezőn.
     * Lépései:
     * 1. Ha a mező sózott, akkor hívja az állapot snowMelt() (olvadás) metódusát.
     * 2. Ha nem sózott, akkor hívja a snowFall() (hóesés) metódust.
     * 3. Ha a mező fel van szórva zúzalékkal (isRubbled), inkrementálja a kopási indexet (rubbleFadingIndex).
     * 4. Ha az index eléri a küszöböt, a zúzalék eltűnik (isRubbled = false).
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
     * A söprőfejtől érkező sekély havat kezeli.
     * Lépései:
     * 1. Továbbítja (delegálja) az eseményt a jelenlegi mezőállapot acceptSweptSnow metódusának.
     * 2. Beállítja az ez alapján visszakapott új állapotot.
     *
     * @param ssts A beérkező sekély hó állapot.
     */
    public void acceptSweptSnow(ShallowSnowyTileState ssts) {
        this.state = state.acceptSweptSnow(ssts);
    }

    /**
     * A söprőfejtől érkező mély havat kezeli.
     * Lépései:
     * 1. Továbbítja (delegálja) az eseményt a jelenlegi mezőállapot acceptSweptSnow metódusának.
     * 2. Beállítja az ez alapján visszakapott új állapotot.
     *
     * @param dsts A beérkező mély hó állapot.
     */
    public void acceptSweptSnow(DeepSnowyTileState dsts) {
        this.state = state.acceptSweptSnow(dsts);
    }

    /**
     * Kezeli egy érkező jármű fogadását a mezőn.
     * Lépései:
     * 1. Ellenőrzi, hogy van-e már a mezőn egy jármű. Ha igen, azonnal visszatér az ott lévő járművel.
     * 2. Meghívja a jármű goToTile() metódusát, ami ellenőrzi, hogy a jármű ténylegesen ráléphet-e (deleál vissza az acceptVehicle konkrét típusaira).
     * 3. Ha a lépés érvényes, beállítja az új járművet a mezőre, és null-t ad vissza (sikeres lépés).
     * 4. Ha nem érvényes, visszatér az eredeti járművel.
     *
     * @param v Az érkező jármű.
     * @return A rajta álló (vagy azzal ütköző) jármű, sikeres lépés esetén null.
     */
    public Vehicle acceptVehicle(Vehicle v) {
        if(vehicle != null)
            return vehicle;
        boolean isValid = v.goToTile(this);

        if(isValid) {
            vehicle = v;
            return null;
        }
        else {
            return v;
        }
    }

    /**
     * Delegálja a hókotró mezőre lépését az állapotnak.
     * @param v Az érkező hókotró.
     * @return true, ha a lépés érvényes.
     */
    public boolean acceptVehicle(SnowShovel v){
        return state.acceptVehicle(v);
    }

    /**
     * Delegálja a busz mezőre lépését az állapotnak.
     * @param v Az érkező busz.
     * @return true, ha a lépés érvényes.
     */
    public boolean acceptVehicle(Bus v){
        return state.acceptVehicle(v);
    }

    /**
     * Delegálja az autó mezőre lépését az állapotnak, és kezeli a hó tömörödését.
     * Lépései:
     * 1. Ellenőrzi, hogy az aktuális állapot engedi-e az autót.
     * 2. Ha igen, megnöveli a hó tömörödési indexét (compressByOne).
     * 3. Ha a mező nem sózott és az index elért egy kritikus szintet (>= 2), az állapot compressionReached() metódusa hívódik (pl. jéggé válik).
     * 4. Ha történt állapotváltás, lenullázza a kompressziós indexet.
     * 5. Mindig false-al tér vissza a specifikus NPC logika miatt, amit a hívó kezel le.
     *
     * @param v Az érkező autó.
     * @return Érvényességi állapot (jelenlegi logika szerint false-t ad, a hívás mellékhatásokat okoz).
     */
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
     * Kezeli, hogy a mezőn álló jármű egy szomszédos mezőre szeretne lépni.
     * Lépései:
     * 1. Ellenőrzi, hogy a célmező (t) ténylegesen a szomszédok között van-e.
     * 2. Ha érvényes szomszéd, meghívja a célmező acceptVehicle() metódusát a járművel.
     * 3. Ha a célmező elfogadta a járművet (null-t adott vissza, nem volt ütközés), akkor az aktuális mezőről eltávolítja a járművet (null).
     * 4. Visszatér a célmezőről kapott eredménnyel (ütközött jármű vagy null).
     *
     * @param t A célmező.
     * @param v A mozgó jármű.
     * @return A jármű, amivel ütközött, vagy null, ha a mozgás sikeres és ütközésmentes volt.
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
     * Takarítási válasz söprőfej (SweeperHead) használata esetén.
     * Lépései:
     * 1. Lekéri az új állapotot a jelenlegi állapot cleanedBy(SweeperHead) metódusától.
     * 2. Kicseréli az állapotot, és ha volt változás, a régi állapot sweepSnowToSide metódusával félresöpri a havat a szomszédos mezőre.
     *
     * @param a A használt söprőfej.
     * @return true, ha történt állapotváltozás, false egyébként.
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
     * Takarítási válasz hóhányófej (BlowerHead) használata esetén.
     * Lépései:
     * 1. Lekéri és beállítja az új állapotot a cleanedBy() alapján.
     *
     * @param a A használt hóhányófej.
     * @return true, ha történt állapotváltozás.
     */
    public boolean cleanTile(BlowerHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;
        isRubbled = false;

        return oldState != newState;
    }

    /**
     * Takarítási válasz jégtörő fej (IcebreakerHead) használata esetén.
     * Lépései:
     * 1. Lekéri és beállítja az új állapotot a cleanedBy() alapján.
     *
     * @param a A használt jégtörő fej.
     * @return true, ha történt állapotváltozás.
     */
    public boolean cleanTile(IcebreakerHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;

        return oldState != newState;
    }

    /**
     * Takarítási válasz sószóró fej (SalterHead) használata esetén.
     * Lépései:
     * 1. Beállítja az isSalted flaget igazra.
     *
     * @param a A használt sószóró fej.
     * @return false (állapotminta szinten nem változik azonnal).
     */
    public boolean cleanTile(SalterHead a) {
        this.isSalted = true;
        return false;
    }

    /**
     * Takarítási válasz kőzúzalék szóró fej (CobblestoneHead) használata esetén.
     * Lépései:
     * 1. Beállítja az isRubbled flaget igazra, és lenullázza a kopási indexet.
     *
     * @param a A használt zúzalék szóró fej.
     * @return false (állapotminta szinten nem változik azonnal).
     */
    public boolean cleanTile(CobblestoneHead a) {
        this.isRubbled = true;
        rubbleFadingIndex = 0;
        return false;
    }

    /**
     * Takarítási válasz sárkányfej (DragonHead) használata esetén.
     * Lépései:
     * 1. Lekéri és beállítja az új állapotot a cleanedBy() alapján.
     *
     * @param a A használt sárkányfej.
     * @return true, ha történt állapotváltozás.
     */
    public boolean cleanTile(DragonHead a) {
        TileState oldState = this.state;
        TileState newState = state.cleanedBy(a);
        state = newState;

        return oldState != newState;
    }

    /**
     * Teszteléshez használt segédfüggvény a mező besózására.
     */
    public void setSalted(){
        isSalted = true;
    }

    public void setVehicle(Vehicle c1) {
        vehicle = c1;
    }

    /**
     * Delegálja az útvonalkeresési kérelmet az aktuális TileState-nek.
     * Lépései:
     * 1. Meghívja a belső state objektum requestPath metódusát a szükséges paraméterekkel.
     *
     * @param position A jelenlegi mező.
     * @param destination A célmező.
     * @param pf A PathFinder objektum.
     * @return A következő mező az út során.
     */
    public Tile requestPath(Tile position, Tile destination, PathFinder pf){
        return state.requestPath(position, destination, pf, isSalted, isRubbled);
    }

    /**
     * Kiszámítja, hogy egy csúszó jármű hova érkezik.
     * Lépései:
     * 1. Vesz egy szomszédot kiindulási lépésnek (step1).
     * 2. Végigiterál a szomszédokon és a sávokon, hogy megtalálja azt a szomszédot, amelyik azonos sávban folytatódik előre.
     * 3. Ugyanezt az iterációt elvégzi mégegyszer (step2), hogy 2 mezőnyi távolságra csússzon a jármű az eredeti sávban.
     * 4. Visszatér a megtalált második szomszéddal.
     *
     * @return A mező, amire a jármű érkezik a megcsúszás után.
     */
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

    /**
     * Hozzáadja a mezőt egy gráf (BFS) lehetséges csomópontjai közé.
     * Lépései:
     * 1. Továbbítja a hívást a mező állapotának, mivel az állapot dönti el, hogy járható-e a mező.
     *
     * @param subGraph A bejárható mezők listája.
     */
    public void addToBFSSubGraph(List<Tile> subGraph){
        state.addToBFSSubGraph(subGraph, this);
    }
}
