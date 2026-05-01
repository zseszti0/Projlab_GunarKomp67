package control;

import model.map.Tile;
import model.players.*;
import model.shop.base.Shop;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static Shop shop;
    private List<IAutomatic> automata = new ArrayList<>();

    // A résztvevők
    private List<Cleaner> cleaners = new ArrayList<>();
    private List<BusChaffeur> busChauffeurs = new ArrayList<>();
    private List<NPCDriver> npcDrivers = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();

    // Config változók
    private boolean randomized = false;
    private String currentActorId = "";
    private int currentPlayerIndex = 0;
    private int currentActorRemainingAp = 0;
    private List<String> actorQueue = new ArrayList<>();

    // Üres konstruktor, ha esetleg új játékot kezdenétek fájl nélkül
    public GameManager() {}

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
    }

    public void tickTimer(){
        automata.forEach(IAutomatic::update);
    }

    void startGame(){
        currentPlayerIndex = 0;
        //bla bla
    }

    // --- SETTEREK A PARSERHEZ ---

    public void setCleaners(List<Cleaner> cleaners) {
        this.cleaners = cleaners;
    }

    public void setBusChauffeurs(List<BusChaffeur> busChauffeurs) {
        this.busChauffeurs = busChauffeurs;
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
}