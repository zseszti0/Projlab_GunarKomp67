package model.map;

import java.util.List;

public class Lane {
    private List<Tile> tiles;

    public List<Tile> getTiles() {
        return tiles;
    }

    public void addTile(Tile tile){
        tiles.add(tile);
    }

    public Lane(List<Tile> tiles) {this.tiles = tiles;}


    void blockAllTilesInLane(){
        for(Tile tile : tiles){
            //TODO
        }
    }
}
