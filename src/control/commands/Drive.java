package control.commands;

import control.GameManager;
import model.map.Tile;
import model.vehicles.Bus;
import model.vehicles.SnowShovel;
import model.players.Cleaner;
import model.players.BusChaffeur;

import java.util.List;

public class Drive extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<Tile> tiles = gameManager.getTiles();
        Tile pos = tiles.stream().filter(tile -> tile.getName().equals(args.get(1))).findFirst().orElse(null);
        if(pos == null) return false;

        List<Cleaner> cleaners = gameManager.getCleaners();
        SnowShovel snowShovel = null;
        Cleaner cleaner = null;
        for(Cleaner clnr : cleaners){
            for(SnowShovel ss : clnr.getVehicles()){
                if(ss.getName().equals(args.get(0))) {
                    snowShovel = ss;
                    cleaner = clnr;
                    break;
                }
            }
        }
        if(snowShovel == null){
            List<BusChaffeur> busChauffeurs = gameManager.getBusChauffeurs();
            Bus bus = null;
            BusChaffeur busChauffer = null;
            for(BusChaffeur bsch : busChauffeurs){
                for(Bus b : bsch.getVehicles()){
                    if(b.getName().equals(args.get(0))) {
                        bus = b;
                        busChauffer = bsch;
                        break;
                    }
                }
            }
            if(bus == null) return false;
            busChauffer.drive(bus,pos);
            return true;
        }
        else{
            cleaner.drive(snowShovel,pos);
            return true;
        }
    }
}
