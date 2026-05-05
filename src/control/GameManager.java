package control;

import model.map.Tile;
import model.players.*;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.shop.base.Shop;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Rubble;
import model.shop.consumables.Salt;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * A játék központi vezérlő osztálya.
 * Feladata a játékosok, a pálya, a körök és a bolt állapotának nyilvántartása és menedzselése.
 */
public class GameManager {
    private static Shop shop;

    /**
     * Inicializálja és feltölti a játék boltját (Shop).
     * Lépései:
     * 1. Létrehoz három listát: a tárgyak neveinek, a tényleges objektumoknak és az áraknak.
     * 2. Feltölti a listákat a különböző kiegészítőkkel (pl. seprű, hótoló, sárkányfej) és nyersanyagokkal (üzemanyag, só, kavics).
     * 3. Beállítja minden tárgy egységárát (jelenleg fixen 100).
     * 4. Példányosítja a Shop objektumot ezekkel a listákkal.
     */
    public static void setUpShop() {
        List<String> items = new ArrayList<>();
        List<Supplier<Purchasable>> purchasables = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();

        items.add("BlowerHead");
        items.add("CobbleStoneHead");
        items.add("DragonHead");
        items.add("IcebreakerHead");
        items.add("SalterHead");
        items.add("SweeperHead");
        items.add("Biokerosene");
        items.add("Salt");
        items.add("Rubble");
        items.add("SnowShovel");

        purchasables.add(() -> new BlowerHead("BlowerHead"));
        purchasables.add(() -> new CobblestoneHead("CobbleStoneHead"));
        purchasables.add(() -> new DragonHead("DragonHead"));
        purchasables.add(() -> new IcebreakerHead("IcebreakerHead"));
        purchasables.add(() -> new SalterHead("SalterHead"));
        purchasables.add(() -> new SweeperHead("SweeperHead"));
        purchasables.add(() -> new Biokerosene(1, "Biokerosene"));
        purchasables.add(() -> new Salt(1, "Salt"));
        purchasables.add(() -> new Rubble(1, "Rubble"));
        purchasables.add(() -> new SnowShovel("SnowShovel"));

        for(int i = 0; i < items.size(); i++)
            prices.add(10);
        prices.set(0,60);
        prices.set(2,90);
        prices.set(3,70);
        prices.set(4,80);
        prices.set(1,80);
        prices.set(5,50);
        prices.set(9,100);

        shop = new Shop(items, purchasables, prices);
    }

    private List<IAutomatic> automata = new ArrayList<>();

    // A résztvevők
    private List<Cleaner> cleaners = new ArrayList<>();
    private List<BusChaffeur> busChauffeurs = new ArrayList<>();
    private List<NPCDriver> npcDrivers = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    // Config változók
    private boolean randomized = true;
    private String currentActorId = "";
    private int currentActorRemainingAp = 2;
    private List<String> actorQueue = new ArrayList<>();

    /**
     * Alapértelmezett konstruktor új játék indításához.
     * Csupán felépíti a boltot a setUpShop() meghívásával.
     */
    public GameManager() {
        setUpShop();
    }

    /**
     * Paraméteres konstruktor a fájlból (XML) történő betöltéshez.
     * Lépései:
     * 1. Inicializálja az összes belső listát és állapotváltozót a kapott paraméterek alapján (null ellenőrzésekkel).
     * 2. Hozzáadja az NPC-ket és a mezőket (Tile) az automata (önmagát frissítő) elemek listájához.
     * 3. Felépíti a boltot a setUpShop() hívásával.
     */
    public GameManager(boolean randomized, String currentActorId, int currentActorAp,
                       List<String> actorQueue, List<Tile> tiles, List<Cleaner> cleaners,
                       List<BusChaffeur> busChauffeurs, List<NPCDriver> npcDrivers) {
        this.randomized = randomized;
        this.currentActorId = currentActorId != null ? currentActorId : "";
        this.currentActorRemainingAp = currentActorAp;
        this.actorQueue = actorQueue != null ? actorQueue : new ArrayList<>();
        this.tiles = tiles != null ? tiles : new ArrayList<>();
        this.cleaners = cleaners != null ? cleaners : new ArrayList<>();
        this.busChauffeurs = busChauffeurs != null ? busChauffeurs : new ArrayList<>();
        this.npcDrivers = npcDrivers != null ? npcDrivers : new ArrayList<>();

        // NPC-ket és Tile-okat az automaták közé
        automata.addAll(npcDrivers);
        automata.addAll(tiles);

        setUpShop();
    }

    /**
     * A játékidő léptetése.
     * Lépései: Végigiterál az összes automata elemen (NPC-k, mezők) és meghívja a frissítő (update) metódusukat.
     */
    public void tickTimer() {
        automata.forEach(IAutomatic::update);
    }

    public void tickSnowTimer(){
        tiles.forEach(Tile :: update);
    }

    public void tickCarTimer(){
        npcDrivers.forEach(NPCDriver::update);
    }

    /**
     * Elindítja a játékot.
     * Lépései: Beállítja a lépési sorrend (actorQueue) első elemét aktuális cselekvőnek (currentActorId).
     */
    public void startGame() {
        currentActorId = actorQueue.get(0);
    }

    // --- SETTEREK A PARSERHEZ ---

    /**
     * Beállítja a takarítók listáját.
     * Lépései: Eltárolja a listát, majd frissíti a cselekvők sorrendjét (actorQueue) a takarítók neveivel.
     */
    public void setCleaners(List<Cleaner> cleaners) {

        this.cleaners = cleaners;
        List<Cleaner> cleanersCopy = new ArrayList<>(cleaners);
        cleaners.clear();
        actorQueue.addAll(cleanersCopy.stream().map(Cleaner::getName).toList());
    }

    /**
     * Beállítja a buszsofőrök listáját.
     * Lépései: Eltárolja a listát, majd felülírja a cselekvők sorrendjét a sofőrök neveivel.
     */
    public void setBusChauffeurs(List<BusChaffeur> busChauffeurs) {

        this.busChauffeurs = busChauffeurs;
        List<BusChaffeur> busChauffeursCopy = new ArrayList<>(busChauffeurs);
        busChauffeurs.clear();
        actorQueue.addAll(busChauffeursCopy.stream().map(BusChaffeur::getName).toList());
    }

    /**
     * Beállítja az NPC sofőrök listáját.
     * Lépései: Eltárolja a listát, majd hozzáadja őket az önműködő automatákhoz.
     */
    public void setNpcDrivers(List<NPCDriver> npcDrivers) {
        this.npcDrivers = npcDrivers;
        // NPC-ket érdemes betenni az automaták közé
        automata.addAll(npcDrivers);
    }

    /**
     * Beállítja a pálya mezőit.
     * Lépései: Eltárolja a listát, majd az időjárás miatt a mezőket is beteszi az önműködő automaták közé.
     */
    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
        // Tile-okat is betesszük az automaták közé az időjárás miatt
        automata.addAll(tiles);
    }

    public void setRandomized(boolean randomized) {
        this.randomized = randomized;
    }

    public void setCurrentActorId(String currentActorId) {
        this.currentActorId = currentActorId;
    }

    public void setCurrentActorRemainingAp(int currentActorRemainingAp) {
        this.currentActorRemainingAp = currentActorRemainingAp;
    }

    public void setActorQueue(List<String> actorQueue) {
        this.actorQueue = actorQueue;
    }

    // --- GETTEREK A SAVE FUNKCIÓHOZ ---
    // (A getterek értelemszerűen csak visszatérnek a kért belső változókkal.)

    public boolean isRandomized() {
        return randomized;
    }

    public String getCurrentActorId() {
        return currentActorId;
    }

    public int getCurrentActorRemainingAp() {
        return currentActorRemainingAp;
    }

    public List<String> getActorQueue() {
        return actorQueue;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Cleaner> getCleaners() {
        return cleaners;
    }

    public List<BusChaffeur> getBusChauffeurs() {
        return busChauffeurs;
    }

    public List<NPCDriver> getNpcDrivers() {
        return npcDrivers;
    }

    /**
     * Vásárlási parancs végrehajtása egy kiegészítőre/tárgyra.
     * Lépései:
     * 1. Megkeresi az éppen soron lévő játékost (takarítót) a currentActorId alapján. Ha nincs ilyen, kilép (false).
     * 2. Meghívja a megtalált takarító shop() metódusát a kapott tárgynévvel és a globális bolttal.
     * @return true, ha a takarító létezik és elindult a vásárlás, egyébként false.
     */
    public void setMap(List<Tile> tiles){
        this.tiles = tiles;
    }

    /**
     * Vásárlási parancs végrehajtása egy hókotrót.
     * Lépései:
     * 1. Megkeresi a soron lévő takarítót. Ha nem találja, kilép.
     * 2. Meghívja a takarító "hókotró-specifikus" vásárló metódusát, megadva a boltot és a lehelyezési célmezőt.
     * @return true, ha a takarító megvan és a folyamat elindult.
     */
    public boolean orderItem(String s, int amount) {
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null)
            return false;

        for(int i = 0; i < amount; i++)
            currentActor.shop(s, shop);

        turnEnd();
        return true;
    }

    /**
     * Vásárlási parancs végrehajtása egy hótolóra.
     * Lépései:
     * 1. Megkeresi a soron lévő takarítót. Ha nem találja, kilép.
     * 2. Meghívja a takarító "hótoló-specifikus" vásárló metódusát, megadva a boltot és a lehelyezési célmezőt.
     * @return true, ha a takarító megvan és a folyamat elindult.
     */
    public boolean orderSnowShovel(Tile pos) {
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null)
            return false;
        currentActor.shop("SnowShovel", shop,pos);

        turnEnd();
        return true;
    }

    /**
     * Befejezi a jelenlegi cselekvő körét, és átadja a stafétát a következőnek.
     * Lépései:
     * 1. Megkeresi a jelenlegi szereplő indexét az actorQueue-ban.
     * 2. Kiszámolja a következő indexet (ha a végére ért, elölről kezdi a modulo (%) művelet miatt).
     * 3. Frissíti a currentActorId-t a következő játékosra.
     */
    public void pass() {
        currentActorId = actorQueue.get((1 + actorQueue.indexOf(currentActorId)) % actorQueue.size());
    }

    /**
     * Végrehajtja a soron lévő játékos lépés (drive) akcióját.
     * Lépései:
     * 1. Megkeresi a jelenlegi cselekvőt a takarítók között.
     * 2. Ha nem találja a takarítók közt, megkeresi a buszsofőrök között. Ha ott sincs, kilép (false).
     * 3. Ha buszsofőr a cselekvő, megkeresi a kért járművet a flottájában, és elvezeti a célmezőre.
     * 4. Ha takarító a cselekvő, megkeresi a kért hókotrót a járművei közt, és azzal hajt a célmezőre.
     * 5. Mindkét sikeres esetben adminisztrálja az akciópont-levonást (turnEnd()), majd visszatér.
     *
     * @param snowShovelName A vezetni kívánt jármű (hókotró vagy busz) neve.
     * @param pos A célmező, ahová a járművet vezetni kell.
     * @return true, ha a vezetés végrehajtódott, false, ha a karakter vagy a jármű nem található.
     */
    public boolean drive(String snowShovelName, Tile pos){
        boolean success = false;
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null){
            BusChaffeur busChaffeur = busChauffeurs.stream().filter(b -> b.getName().equals(currentActorId)).findFirst().orElse(null);
            if(busChaffeur == null)
                return false;
            Bus bus = busChaffeur.getVehicles().stream().filter(b -> b.getName().equals(snowShovelName)).findFirst().orElse(null);
            if(bus == null)
                return false;
            success = busChaffeur.drive(bus,pos);
        }
        else{
            SnowShovel snowShovel = currentActor.getVehicles().stream().filter(s -> s.getName().equals(snowShovelName)).findFirst().orElse(null);
            if(snowShovel == null)
                return false;
            success = currentActor.drive(snowShovel,pos);
        }

        turnEnd();
        return success;
    }

    /**
     * Lecseréli egy adott hókotró kiegészítőjét az aktuális játékos körében.
     * Lépései:
     * 1. Megkeresi a jelenleg soron lévő takarítót.
     * 2. Megkeresi a takarító járművei között a megadott nevű (ss) hókotrót, és lecseréli a kiegészítőjét.
     * (Megjegyzés: a kódban lévő 'if(currentActor == null)' feltétel valószínűleg elírás, és != lenne a helyes).
     * 3. Levonja az akciópontot (turnEnd()), majd sikeresen visszatér.
     *
     * @param ss A módosítandó hókotró neve.
     * @param newAttachment Az új kiegészítő azonosítója/neve.
     * @return Mindig true értékkel tér vissza.
     */
    public boolean switchAttachment(String ss, String newAttachment){
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null){
            SnowShovel snowShovel = currentActor.getVehicles().stream().filter(s -> s.getName().equals(ss)).findFirst().orElse(null);
            currentActor.changeAttachment(snowShovel,newAttachment);
        }
        turnEnd();
        return true;
    }

    /**
     * Adminisztrálja a játékos cselekvésének (akciójának) a végét.
     * Lépései:
     * 1. Csökkenti a jelenlegi játékos hátralévő akciópontjait (AP) eggyel.
     * 2. Ha az akciópontok száma eléri a 0-t, automatikusan passzol a következő játékosnak,
     * és visszaállítja az új játékos akciópontjait az alapértelmezett 2-re.
     */
    private void turnEnd(){
        currentActorRemainingAp--;
        if(currentActorRemainingAp == 0){
            pass();
            currentActorRemainingAp = 2;
            System.out.println(currentActorId+" lépése következik.");

        }
    }
}