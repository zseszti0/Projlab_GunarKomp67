package control.commands;

import control.GameManager;
import model.players.Cleaner;
import model.vehicles.SnowShovel;

import java.util.List;

/**
 * Játékparancs, amely lecseréli egy létező hókotró jelenlegi kiegészítőjét egy másikra.
 */
public class SwitchAttachment extends GameCommand {
    /**
     * Végrehajtja a kiegészítő cseréjét.
     * Lépései:
     * 1. Végigiterál az összes takarítón és azok járművein, hogy megkeresse a megadott nevű (args[0]) hókotrót.
     * 2. Ha nem talál ilyen hókotrót, megszakítja a műveletet (false).
     * 3. Ha megtalálta, a tulajdonos takarítón keresztül lecseréli a hókotró kiegészítőjét a kért típusra (args[1]).
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a hókotró neve, args[1]: az új kiegészítő azonosítója.
     * @return true, ha a hókotró létezik és a cserélés elindult, false, ha a jármű nem található.
     */
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
