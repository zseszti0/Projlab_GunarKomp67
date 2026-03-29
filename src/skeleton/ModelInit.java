package skeleton;

import model.inventory.Inventory;
import model.map.Lane;
import model.map.PathFinder;
import model.map.Tile;
import model.map.tilestates.CleanTileState;
import model.map.tilestates.IcyTileState;
import model.map.tilestates.ShallowSnowyTileState;
import model.map.tilestates.TileState;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.players.NPCDriver;
import model.shop.attachements.*;
import model.shop.base.Purchasable;
import model.shop.base.Shop;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import java.util.ArrayList;
import java.util.List;

public class ModelInit {
    List<Cleaner> cleaners;
    List<BusChaffeur> chaffeurs;
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
            case "BuySnowShovel": initBuySnowShovel();initChangeSweeperToBlower()
                break;
            case "ChangeSweeperToBlower": ;initChangeSweeperToBlower();
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

    private void mapInit(String whichTileState){
        Lane lane1 = new Lane(new ArrayList<>());
        Lane lane2 = new Lane(new ArrayList<>());

        Tile tile1 = new Tile(CleanTileState.getInstance(), lane1);
        TileState ts;
        switch (whichTileState) {
            case "Shallow Snowy" -> ts = ShallowSnowyTileState.getInstance();
            case "Icy" -> ts = IcyTileState.getInstance();
            default -> ts = CleanTileState.getInstance();
        }
        Tile tile2 = new Tile(ts, lane1);
        Tile tile3 = new Tile(CleanTileState.getInstance(), lane2);

        lane1.addTile(tile1); lane1.addTile(tile2); lane2.addTile(tile3);

        List<Tile> tile1Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile1Neighbours);

        List<Tile> tile2Neighbours = new ArrayList<>(List.of(tile1, tile3));
        tile1.setNeighbors(tile2Neighbours);

        List<Tile> tile3Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile3Neighbours);

        tiles = new ArrayList<>(List.of(tile1, tile2, tile3));
        lanes = new ArrayList<>(List.of(lane1, lane2));
    }

    private void cleanerInit(String whichAttachment, boolean notEmptyInventory){
        Inventory inventory = new Inventory("Cleaner");
        if(notEmptyInventory){
            inventory.addAttachment(new BlowerHead("Blower", 2));
        }

        SnowShovel ss = new SnowShovel("Cleaner1", tiles.getFirst());
        Attachment a;
        switch (whichAttachment) {
            case "BlowerHead": a = new BlowerHead("Blower", 1);
                break;
            case "IcebreakerHead": a = new IcebreakerHead("Icebreaker", 1);
                break;
            case "SalterHead": a = new SalterHead("Salter", 1); inventory.addConsumable(new Salt(1, 1));
                break;
            case "DragonHead": a = new DragonHead("Dragon", 1); inventory.addConsumable(new Biokerosene(1, 1));
                break;
            default: a = new SweeperHead("Sweeper", 1);
        }
        ss.setEquippedAttachment(a);
        a.setSnowShovel(ss);

        Cleaner cleaner = new Cleaner("Cleaner", inventory);

        cleaner.addToFleet(ss, tiles.getFirst());

        cleaners = new ArrayList<>(List.of(cleaner));
    }


    private void npcDriverInit(String carAmount){
        npcDriver = new NPCDriver("NPCDriver", new PathFinder());

        npcDriver.addCar(new Car("Car1", ));
    }

    private void shopInit(String whichPurcahasable){
        List<String> purchasableNames = new ArrayList<>();
        List<Purchasable> purchasables = new ArrayList<>();
        List<Integer> purchasablePrices = new ArrayList<>();

        switch (whichPurcahasable) {
            case "Salt": purchasableNames.add("Salt"); purchasables.add(new Salt(1, 1)); purchasablePrices.add(1);
                break;
            case "SalterHead": purchasableNames.add("SnowShovel"); purchasables.add(new SnowShovel("NewSnowShovel", tiles.getLast())); purchasablePrices.add(1);
                break;
            default: purchasableNames.add("SweeperHead"); purchasables.add(new SweeperHead("Sweeper", 1)); purchasablePrices.add(1);
        }
        shop = new Shop(purchasableNames, purchasables, purchasablePrices);
    }
}
