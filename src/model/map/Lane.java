package model.map;

import model.map.tilestates.BlockedTileState;

import java.util.List;

/**
 * A modellbeli mezőket (Tile) fogja össze.
 * Felelőssége a mező pusztítások és hozzáadások végrehajtása az adatokon.
 * Asszociációk: Tile (a sávban lévő mezők). Egy sávnak legalább egy darab mezője kell, hogy legyen.
 */
public class Lane {
    /**
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos*/
    private final String name;

    /**
     * Visszaadja az objektum nevét.
     * Lépései:
     * 1. Visszatér a sáv nevével.
     *
     * @return A sáv azonosítója (neve).
     */
    public String getName() {
        return name;
    }

    /**
     * A sávhoz tartozó mezők listája.
     */
    private List<Tile> tiles;

    /**
     * Visszaadja a sávban lévő mezőket.
     * Lépései:
     * 1. Visszatér a sávot alkotó Tile objektumokat tartalmazó listával.
     *
     * @return A sávhoz tartozó Tile objektumok listája.
     */
    public List<Tile> getTiles() {
        return tiles;
    }

    /**
     * Hozzáad egy új mezőt a sávhoz, ezzel kiterjeszti azt.
     * Lépései:
     * 1. A paraméterként kapott mezőt (Tile) hozzáadja a belső tiles listához.
     *
     * @param tile A hozzáadandó mező.
     */
    public void addTile(Tile tile){
        tiles.add(tile);
    }

    /**
     * Konstruktor a Lane osztályhoz.
     * Lépései:
     * 1. Beállítja a sávhoz tartozó mezők listáját.
     * 2. Beállítja a sáv nevét.
     *
     * @param tiles A sávot alkotó mezők listája.
     * @param name A sáv azonosító neve.
     */
    public Lane(List<Tile> tiles, String name) {
        this.tiles = tiles;
        this.name = name;
    }

    /**
     * Lezárja az összes mezőt a sávon.
     * Karambol (ütközés) esetén hívódik.
     * Lépései:
     * 1. Végigiterál a sávhoz tartozó összes mezőn (Tile).
     * 2. Minden mezőn meghívja a blockTile() metódust, amely BlockedTileState-re állítja azok állapotát.
     */
    void blockAllTilesInLane(){
        for(Tile tile : tiles){
            tile.blockTile();
        }
    }
}
