package model.map;


import java.util.List;
import model.map.tilestates.*;
import model.players.IAutomatic;
import model.shop.attachements.*;
import model.vehicles.Vehicle;

public class Tile implements IAutomatic {

    private boolean isSalted;
    private int compressionIndex;
    private int saltMeltingIndex;

    private TileState state;

    private List<Tile> neighbors;
    private Lane lane;
    private Vehicle vehicle;

    public Tile(TileState state, Lane lane) {
        this.state = state;
        this.lane = lane;
        this.isSalted = false;
        this.compressionIndex = 0;
        this.saltMeltingIndex = 0;
    }

    public List<Tile> getNeighbors() {
        return neighbors;
    }

    public void closeLane() {
        //TODO
    }

    public void update() {
        if (isSalted) {
            state.snowMelt();
        } else {
            state.snowFall();
        }
    }

    public void acceptSweptSnow(ShallowSnowyTileState ssts) {
        state.acceptSweptSnow(ssts);
    }

    public void acceptSweptSnow(DeepSnowyTileState dsts) {
        state.acceptSweptSnow(dsts);
    }

    public Vehicle acceptVehicle(Vehicle v) {
        //TODO
        state = state.acceptVehicle(compressionIndex);
        return v;
    }

    public Vehicle moveToNeighbor(Tile t, Vehicle v) {
        //TODO
        if (v == null) return null;
        return v;
    }

    public boolean cleanTile(SweeperHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    public boolean cleanTile(BlowerHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    public boolean cleanTile(IcebreakerHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }

    public boolean cleanTile(SalterHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        this.isSalted = true;
        return true;
    }

    public boolean cleanTile(DragonHead a) {
        if (state == null) return false;
        this.state = state.cleanedBy(a);
        return true;
    }
}
