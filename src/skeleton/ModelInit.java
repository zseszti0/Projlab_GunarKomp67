package skeleton;

import model.inventory.Inventory;
import model.map.Lane;
import model.map.Tile;
import model.map.tilestates.CleanTileState;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.players.NPCDriver;
import model.shop.base.Shop;
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

        }
    }

    private void initMoveWithSweeperToCleanTile(){
        //map inicializálás
        mapInit1();

        //cleaner játékos inicializálás
        cleanerInitSweeper();
    }
    private void initMoveWithSweeperToShallowSnowyTile(){

    }
    private void initMoveWithBlowerToShallowSnowyTile(){

    }
    private void initMoveWithIcebreakerToIcyTile(){

    }

    private void mapInit1(){
        List<Tile> tileList1 = new ArrayList<>();
        Lane lane1 = new Lane(tileList1);
        List<Tile> tileList2 = new ArrayList<>();
        Lane lane2 = new Lane(tileList2);

        Tile tile1 = new Tile(CleanTileState.getInstance(), lane1);
        Tile tile2 = new Tile(CleanTileState.getInstance(), lane1);
        Tile tile3 = new Tile(CleanTileState.getInstance(), lane2);

        tileList1.add(tile1); tileList1.add(tile2); tileList2.add(tile3);

        List<Tile> tile1Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile1Neighbours);

        List<Tile> tile2Neighbours = new ArrayList<>(List.of(tile1, tile3));
        tile1.setNeighbors(tile2Neighbours);

        List<Tile> tile3Neighbours = new ArrayList<>(List.of(tile2));
        tile1.setNeighbors(tile3Neighbours);

        tiles = new ArrayList<>(List.of(tile1, tile2, tile3));
        lanes = new ArrayList<>(List.of(lane1, lane2));
    }

    private void cleanerInitSweeper(){
        Inventory inventory = new Inventory("Cleaner");
        Cleaner cleaner = new Cleaner("Cleaner", inventory);

        cleaner.addToFleet(new SnowShovel("Cleaner1", tiles.getFirst()), tiles.getFirst());
    }


}
