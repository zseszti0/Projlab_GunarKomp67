package model.map;

import model.map.tilestates.BlockedTileState;

import java.util.List;

/**
 * A modellbeli mezoket (Tile) fogja ossze.
 * Felelossege a mezo pusztitasok es hozzaadasok vegrehajtasa az adatokon.
 * Asszociaciok: Tile (a savban levo mezok). Egy savnak legalabb egy darab mezoje kell, hogy legyen.
 */
public class Lane {
    /**
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos*/
    private final String name;

    /**
     * Visszaadja az objektum nevet.
     * Elorelathatolag csak a skeletonban kell
     * */
    public String getName() {
        return name;
    }

    /**
     * A savhoz tartozo mezok listaja.
     */
    private List<Tile> tiles;

    /**
     * Visszaadja a savban levo mezoket.
     * @return a savhoz tartozo Tile objektumok listaja
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Hozzaad egsy uj mezot a savhoz e kiterjeszti a savot.
     * @param tile a hozzaadando mezo
     */
    public void addTile(Tile tile){
        tiles.add(tile);
    }

    /**
     * Konstruktor a Lane osztalyhoz.
     * @param tiles a savot alkoto mezok listaja
     */
    public Lane(List<Tile> tiles, String name) {
        this.tiles = tiles;
        this.name = name;
    }

    /**
     * Lezarja az osszes mezot a savon.
     * Karambol (utkozes) eseten hivodik, a mezok allapotat BlockedTileState-re allitja.
     */
    void blockAllTilesInLane(){
        for(Tile tile : tiles){
            tile.setState(BlockedTileState.getInstance());
        }
    }
}
