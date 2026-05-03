package control;

import control.commands.Save;
import model.map.Tile;
import model.players.*;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.shop.base.Shop;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Rubble;
import model.shop.consumables.Salt;
import model.vehicles.SnowShovel;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static Shop shop;
    public static void setUpShop(){
        List<String> items = new ArrayList<>();
        List<Purchasable> purchasables = new ArrayList<>();
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

        purchasables.add(new BlowerHead("BlowerHead"));
        purchasables.add(new CobblestoneHead("CobbleStoneHead"));
        purchasables.add(new DragonHead("DragonHead"));
        purchasables.add(new IcebreakerHead("IcebreakerHead"));
        purchasables.add(new SalterHead("SalterHead"));
        purchasables.add(new SweeperHead("SweeperHead"));
        purchasables.add(new Biokerosene(1, "Biokerosene"));
        purchasables.add(new Salt(1, "Salt"));
        purchasables.add(new Rubble(1, "Rubble"));
        purchasables.add(new SnowShovel("SnowShovel"));

        for(int i = 0; i < items.size(); i++)
            prices.add(100);


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
    private int currentActorRemainingAp = 0;
    private List<String> actorQueue = new ArrayList<>();

    // Üres konstruktor, ha esetleg új játékot kezdenétek fájl nélkül
    public GameManager() {
        setUpShop();
    }

    /**
     * Teljes konstruktor XML parserhez.
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

    public void tickTimer(){
        automata.forEach(IAutomatic::update);
    }

    public void startGame(){
        currentActorId = actorQueue.get(0);
    }

    // --- SETTEREK A PARSERHEZ ---

    public void setCleaners(List<Cleaner> cleaners) {

        this.cleaners = cleaners;
        actorQueue.clear();
        actorQueue.addAll(cleaners.stream().map(Cleaner::getName).toList());
    }

    public void setBusChauffeurs(List<BusChaffeur> busChauffeurs) {

        this.busChauffeurs = busChauffeurs;
        actorQueue.clear();
        actorQueue.addAll(busChauffeurs.stream().map(BusChaffeur::getName).toList());
    }

    public void setNpcDrivers(List<NPCDriver> npcDrivers) {
        this.npcDrivers = npcDrivers;
        // NPC-ket érdemes betenni az automaták közé
        automata.addAll(npcDrivers);
    }

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

    public void setMap(List<Tile> tiles){
        this.tiles = tiles;
    }

    public boolean orderItem(String s) {
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null)
            return false;
        currentActor.shop(s, shop);
        return true;
    }

    public boolean orderSnowShovel(Tile pos) {
        Cleaner currentActor = cleaners.stream().filter(cleaner -> cleaner.getName().equals(currentActorId)).findFirst().orElse(null);
        if(currentActor == null)
            return false;
        currentActor.shop("snowShovel", shop,pos);
        return true;
    }

    public void pass() {
        currentActorId = actorQueue.get((1 + actorQueue.indexOf(currentActorId)) % actorQueue.size());
    }
}