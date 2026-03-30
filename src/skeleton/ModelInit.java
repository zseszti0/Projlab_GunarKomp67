package skeleton;

import model.inventory.Inventory;
import model.map.Lane;
import model.map.PathFinder;
import model.map.Tile;
import model.map.tilestates.*;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.players.NPCDriver;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.shop.base.Shop;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.Bus;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ModelInit {
    List<Cleaner> cleaners = new ArrayList<>();
    List<BusChaffeur> chaffeurs = new ArrayList<>();
    NPCDriver npcDriver;
    List<Tile> tiles;
    List<Lane> lanes;
    Shop shop;

    public ModelInit(String useCase) {
        switch (useCase) {
            case "MoveWithSweeperToCleanTile": initMoveWithSweeperToCleanTile();
                break;
            case "MoveWithSweeperToShallowSnowyTile": initMoveWithSweeperToShallowSnowyTile();
                break;
            case "MoveWithBlowerToShallowSnowyTile": initMoveWithBlowerToShallowSnowyTile();
                break;
            case "MoveWithIcebreakerToIcyTile": initMoveWithIcebreakerToIcyTile();
                break;
            case "MoveWithSalterToShallowSnowyTile": initMoveWithSalterToShallowSnowyTile();
                break;
            case "MoveWithDragonToShallowSnowyTile": initMoveWithDragonToShallowSnowyTile();
                break;
            case "BuySweeper": initBuySweeper();
                break;
            case "BuySalt": initBuySalt();
                break;
            case "BuySnowShovel": initBuySnowShovel();
                break;
            case "ChangeSweeperToBlower": initChangeSweeperToBlower();
                break;
            case "CarMoveWithoutCrash": initCarMoveWithoutCrash();
                break;
            case "CarMoveWithCrash": initCarMoveWithCrash();
                break;
            case "UnsaltedUpdate": initUnsaltedUpdate();
                break;
            case "SaltedUpdate": initSaltedUpdate();
                break;
        }
    }

    private void initMoveWithSweeperToCleanTile(){
        //map inicializálás
        mapInit("Clean");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", false);
    }

    private void initMoveWithSweeperToShallowSnowyTile(){
        //map inicializálás
        mapInit("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", false);
    }

    private void initMoveWithBlowerToShallowSnowyTile(){
        //map inicializálás
        mapInit("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit("Blower", false);
    }

    private void initMoveWithIcebreakerToIcyTile(){
        //map inicializálás
        mapInit("Icy");

        //cleaner játékos inicializálás
        cleanerInit("IcebreakerHead", false);
    }

    private void initMoveWithSalterToShallowSnowyTile(){
        //map inicializálás
        mapInit("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit("SalterHead", false);
    }

    private void initMoveWithDragonToShallowSnowyTile(){
        //map inicializálás
        mapInit("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit("DragonHead", false);
    }

    private void initBuySweeper(){
        //map inicializálás
        mapInit("Clean");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", false);

        //shop inicializálás
        shopInit("SweeperHead");
    }

    private void initBuySalt(){
        //map inicializálás
        mapInit("Clean");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", false);

        //shop inicializálás
        shopInit("Salt");
    }

    private void initBuySnowShovel(){
        //map inicializálás
        mapInit("Clean");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", false);

        //shop inicializálás
        shopInit("SnowShovel");
    }

    private void initChangeSweeperToBlower(){
        //map inicializálás
        mapInit("Clean");

        //cleaner játékos inicializálás
        cleanerInit("Sweeper", true);
    }

    private void initCarMoveWithoutCrash(){
        //map inicializálás
        mapInit2("Shallow Snowy");

        //npc játékos inicializálás
        npcDriverInitForNotSlipping(1);
    }

    private void initCarMoveWithCrash(){
        //map inicializálás
        mapInit2("Shallow Snowy");

        //npc játékos inicializálás
        npcDriverInit(2);
    }

    private void initUnsaltedUpdate(){
        //map inicializálás
        mapInit3(false);
    }

    private void initSaltedUpdate(){
        //map inicializálás
        mapInit3(true);
    }


    private void mapInit(String whichTileState){
        Lane lane1 = new Lane(new ArrayList<>(), "Lane1");
        Lane lane2 = new Lane(new ArrayList<>(), "Lane2");

        Tile tile1 = new Tile("Tile1", CleanTileState.getInstance(), lane1);
        TileState ts;
        switch (whichTileState) {
            case "Shallow Snowy" -> ts = ShallowSnowyTileState.getInstance();
            case "Icy" -> ts = IcyTileState.getInstance();
            default -> ts = CleanTileState.getInstance();
        }
        Tile tile2 = new Tile("Tile2", ts, lane1);
        Tile tile3 = new Tile("Tile3", CleanTileState.getInstance(), lane2);

        lane1.addTile(tile1); lane1.addTile(tile2); lane2.addTile(tile3);

        List<Tile> tile1Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile1Neighbours);

        List<Tile> tile2Neighbours = new ArrayList<>(List.of(tile1, tile3));
        tile2.setNeighbors(tile2Neighbours);

        List<Tile> tile3Neighbours = new ArrayList<>(List.of(tile2));
        tile3.setNeighbors(tile3Neighbours);

        tiles = new ArrayList<>(List.of(tile1, tile2, tile3));
        lanes = new ArrayList<>(List.of(lane1, lane2));
    }

    private void mapInit2(String whichTileState){
        Lane lane1 = new Lane(new ArrayList<>(), "Lane1");

        TileState ts1;
        if (whichTileState.equals("Icy")) {
            ts1 = IcyTileState.getInstance();
        }
        else if (whichTileState.equals("Shallow Snowy")) {
            ts1 = ShallowSnowyTileState.getInstance();
        }
        else {
            ts1 = CleanTileState.getInstance();
        }

        TileState ts2;
        if (whichTileState.equals("DeepSnow")) {
            ts2 = IcyTileState.getInstance();
        }
        else if (whichTileState.equals("Shallow Snowy")) {
            ts2 = ShallowSnowyTileState.getInstance();
        }
        else {
            ts2 = CleanTileState.getInstance();
        }

        Tile tile1 = new Tile("Tile1", ts1, lane1);
        Tile tile2 = new Tile("Tile2", ts2, lane1);
        Tile tile3 = new Tile("Tile3", CleanTileState.getInstance(), lane1);

        lane1.addTile(tile1); lane1.addTile(tile2); lane1.addTile(tile3);

        List<Tile> tile1Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile1Neighbours);

        List<Tile> tile2Neighbours = new ArrayList<>(List.of(tile1, tile3));
        tile2.setNeighbors(tile2Neighbours);

        List<Tile> tile3Neighbours = new ArrayList<>(List.of(tile2));
        tile3.setNeighbors(tile3Neighbours);

        tiles = new ArrayList<>(List.of(tile1, tile2, tile3));
        lanes = new ArrayList<>(List.of(lane1));
    }

    private void mapInit3(boolean salted){
        Lane lane1 = new Lane(new ArrayList<>(), "Lane1");

        Tile tile1 = new Tile("Tile1", CleanTileState.getInstance(), lane1); tile1.setSalted();
        Tile tile2 = new Tile("Tile2", ShallowSnowyTileState.getInstance(), lane1); tile2.setSalted();
        Tile tile3 = new Tile("Tile3", DeepSnowyTileState.getInstance(), lane1); tile3.setSalted();
        Tile tile4 = new Tile("Tile4", IcyTileState.getInstance(), lane1); tile4.setSalted();
        Tile tile5 = new Tile("Tile5", BlockedTileState.getInstance(), lane1); tile5.setSalted();

        lane1.addTile(tile1); lane1.addTile(tile2); lane1.addTile(tile3); lane1.addTile(tile4); lane1.addTile(tile5);

        List<Tile> tile1Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile1Neighbours);

        List<Tile> tile2Neighbours = new ArrayList<>(List.of(tile1, tile3));
        tile2.setNeighbors(tile2Neighbours);

        List<Tile> tile3Neighbours = new ArrayList<>(List.of(tile2, tile4));
        tile3.setNeighbors(tile3Neighbours);

        List<Tile> tile4Neighbours = new ArrayList<>(List.of(tile3, tile5));
        tile4.setNeighbors(tile4Neighbours);

        List<Tile> tile5Neighbours = new ArrayList<>(List.of(tile4));
        tile5.setNeighbors(tile5Neighbours);

        tiles = new ArrayList<>(List.of(tile1, tile2, tile3, tile4, tile5));
        lanes = new ArrayList<>(List.of(lane1));
    }

    private void cleanerInit(String whichAttachment, boolean notEmptyInventory){
        Inventory inventory = new Inventory("inventory");
        if(notEmptyInventory){
            inventory.addAttachment(new BlowerHead("Blower", 2));
        }

        SnowShovel ss = new SnowShovel("snowShovel", tiles.get(0));
        Attachment a;
        switch (whichAttachment) {
            case "BlowerHead": a = new BlowerHead("Blower", 1);
                break;
            case "IcebreakerHead": a = new IcebreakerHead("Icebreaker", 1);
                break;
            case "SalterHead": a = new SalterHead("Salter", 1); inventory.addConsumable(new Salt(1, 1,"só1"));
                break;
            case "DragonHead": a = new DragonHead("Dragon", 1); inventory.addConsumable(new Biokerosene(1, 1,"biokerosene1"));
                break;
            default: a = new SweeperHead("Sweeper", 1);
        }
        ss.setEquippedAttachment(a);
        a.setSnowShovel(ss);

        Cleaner cleaner = new Cleaner("Cleaner", inventory);

        cleaner.addToFleet(ss, tiles.get(0));

        cleaners = new ArrayList<>(List.of(cleaner));
    }

    //mapInit2-nél a legelsőre rakja a Car-t
    private void npcDriverInitForNotSlipping(int carAmount){
        NPCDriver npcDriver1 = new NPCDriver("NPCDriver", new PathFinder("PathFinder"));
        npcDriver1.addCar(new Car("Car1", tiles.get(0), List.of(tiles.get(0),tiles.get(tiles.size()-1))));
        if(carAmount > 1){
            npcDriver1.addCar(new Car("Car2", tiles.get(tiles.size()-1), List.of(tiles.get(tiles.size()-1),tiles.get(tiles.size()-1))));
        }

        npcDriver = npcDriver1;
    }

    private void npcDriverInit(int carAmount){
        NPCDriver npcDriver1 = new NPCDriver("NPCDriver", new PathFinder("PathFinder"));
        Car c1 = new Car("Car1", tiles.get(0), List.of(tiles.get(0),tiles.get(tiles.size()-1)));
        npcDriver1.addCar(c1);
        tiles.get(0).setVehicle(c1);
        if(carAmount > 1){
            Car c2 = new Car("Car2", tiles.get(tiles.size()-2), List.of(tiles.get(tiles.size()-1),tiles.get(tiles.size()-1)));
            npcDriver1.addCar(c2);
            tiles.get(tiles.size()-2).setVehicle(c2);
        }
        npcDriver = npcDriver1;
    }

    private void shopInit(String whichPurcahasable){
        List<String> purchasableNames = new ArrayList<>();
        List<Purchasable> purchasables = new ArrayList<>();
        List<Integer> purchasablePrices = new ArrayList<>();

        switch (whichPurcahasable) {
            case "Salt": purchasableNames.add("Salt"); purchasables.add(new Salt(1, 1, "só1")); purchasablePrices.add(1);
                break;
            case "SnowShovel": purchasableNames.add("Hókotró"); purchasables.add(new SnowShovel("NewSnowShovel", tiles.get(tiles.size()-1))); purchasablePrices.add(1);
                break;
            default: purchasableNames.add("SweeperHead"); purchasables.add(new SweeperHead("Sweeper", 1)); purchasablePrices.add(1);
        }
        shop = new Shop(purchasableNames, purchasables, purchasablePrices);
    }
}
