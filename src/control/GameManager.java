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
    private List<Cleaner> cleaners;
    private List<BusChaffeur> busChauffeurs;
    private List<NPCDriver> npcDrivers;
    private List<Tile> tiles;

    // Config változók
    private boolean randomized;
    private String currentActorId;
    private int currentPlayerIndex = 0;
    private int currentActorRemainingAp;
    private List<String> actorQueue;

    // Üres konstruktor, ha esetleg új játékot kezdenétek fájl nélkül
    public GameManager() {}

    // FELTÉTELEZETT TELJES KONSTRUKTOR:
    public GameManager(boolean randomized, String currentActorId, int currentActorRemainingAp,
                       List<String> actorQueue, List<Tile> tiles, List<Cleaner> cleaners,
                       List<BusChaffeur> busChauffeurs, List<NPCDriver> npcDrivers) {
        this.randomized = randomized;
        this.currentActorId = currentActorId;
        this.currentActorRemainingAp = currentActorRemainingAp;
        this.actorQueue = actorQueue;

        this.tiles = tiles;
        this.cleaners = cleaners;
        this.busChauffeurs = busChauffeurs;
        this.npcDrivers = npcDrivers;

        // IAutomatic interfészt megvalósító objektumok regisztrálása az időzítőhöz
        if (tiles != null) this.automata.addAll(tiles);
        if (npcDrivers != null) this.automata.addAll(npcDrivers);
    }

    public void tickTimer(){
        automata.forEach(IAutomatic::update);
    }

    void startGame(){
        currentPlayerIndex = 0;
        //bla bla
    }
}