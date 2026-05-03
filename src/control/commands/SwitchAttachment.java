package control.commands;

import control.GameManager;
import model.players.Cleaner;
import model.vehicles.SnowShovel;

import java.util.List;

public class SwitchAttachment extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        List<model.players.Cleaner> cleaners = gameManager.getCleaners();
        model.vehicles.SnowShovel snowShovel = null;
        model.players.Cleaner cleaner = null;
        for(Cleaner clnr : cleaners){
            for(SnowShovel ss : clnr.getVehicles()){
                if(ss.getName().equals(args.get(0))) {
                    snowShovel = ss;
                    cleaner = clnr;
                    break;
                }
            }
        }
        if(snowShovel == null) return false;
        cleaner.changeAttachment(snowShovel,args.get(1));
        return true;
    }
}
