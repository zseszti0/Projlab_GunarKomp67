package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Konfigurációs parancs egy új takarító (Cleaner) létrehozására és kezdőmezőn való elhelyezésére.
 */
public class Cleaner extends ConfigCommand {
    /**
     * Inicializálja a takarítót és leteszi a játéktérre.
     * Lépései:
     * 1. Megkeresi a játéktér mezői (Tiles) között a megadott nevű (args[1]) célmezőt.
     * 2. Példányosít egy új Cleaner objektumot a kapott névvel (args[0]) és az előbb megtalált kezdőmezővel.
     * 3. Hozzáadja a friss takarítót a GameManager aktív takarítóinak listájához.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: az új takarító neve, args[1]: a kezdőmező (Tile) neve.
     * @return Mindig true értékkel tér vissza.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<model.players.Cleaner> cleaners = gameManager.getCleaners();
        cleaners.add(new model.players.Cleaner(args.get(0)));
        gameManager.setCleaners(cleaners);
        try {
            output.write(("A(z)" + args.get(0) + "nevű takarító szerepű játékos sikeresen hozzáadva a játékkonfigurációhoz.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
