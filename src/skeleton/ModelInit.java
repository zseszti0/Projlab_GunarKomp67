package skeleton;

import model.inventory.Inventory;
import model.map.Lane;
import model.map.Tile;
import model.map.tilestates.CleanTileState;
import model.map.tilestates.IcyTileState;
import model.map.tilestates.ShallowSnowyTileState;
import model.map.tilestates.TileState;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.players.NPCDriver;
import model.shop.attachements.*;
import model.shop.base.Shop;
import model.shop.consumables.Biokerosene;
import model.shop.consumables.Salt;
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

        }
    }

    private void initMoveWithSweeperToCleanTile(){
        //map inicializálás
        mapInit1("Clean");

        //cleaner játékos inicializálás
        cleanerInit1("Sweeper");
    }

    private void initMoveWithSweeperToShallowSnowyTile(){
        //map inicializálás
        mapInit1("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit1("Sweeper");
    }

    private void initMoveWithBlowerToShallowSnowyTile(){
        //map inicializálás
        mapInit1("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit1("Blower");
    }

    private void initMoveWithIcebreakerToIcyTile(){
        //map inicializálás
        mapInit1("Icy");

        //cleaner játékos inicializálás
        cleanerInit1("IcebreakerHead");
    }

    private void initMoveWithSalterToShallowSnowyTile(){
        //map inicializálás
        mapInit1("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit1("SalterHead");
    }

    private void initMoveWithDragonToShallowSnowyTile(){
        //map inicializálás
        mapInit1("Shallow Snowy");

        //cleaner játékos inicializálás
        cleanerInit1("DragonHead");
    }

    private void mapInit1(String whichTileState){
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

    private void cleanerInit1(String whichAttachment){
        Inventory inventory = new Inventory("Cleaner");

        SnowShovel ss = new SnowShovel("Cleaner1", tiles.getFirst());
        Attachment a;
        switch (whichAttachment) {
            case "BlowerHead": a = new BlowerHead(1, "Blower");
                break;
            case "IcebreakerHead": a = new IcebreakerHead(1, "Icebreaker");
                break;
            case "SalterHead": a = new SalterHead(1, "Salter"); inventory.addConsumable(new Salt(1, 1));
                break;
            case "DragonHead": a = new DragonHead(1, "Dragon"); inventory.addConsumable(new Biokerosene(1, 1));
                break;
            default: a = new SweeperHead(1, "Sweeper");
        }
        ss.setEquippedAttachment(a);

        Cleaner cleaner = new Cleaner("Cleaner", inventory);

        cleaner.addToFleet(ss, tiles.getFirst());

        cleaners = new ArrayList<>(List.of(cleaner));
    }


}
