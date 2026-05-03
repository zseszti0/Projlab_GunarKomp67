package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Parancs, mely egy új buszsofőr (BusChaffeur) létrehozásához.
 */
public class BusChaffeur extends ConfigCommand {
    /**
     * Létrehozza és hozzáadja a buszsofőrt a játékhoz.
     * Lépései:
     * 1. Példányosít egy új BusChaffeur objektumot az első argumentumban (args[0]) kapott névvel.
     * 2. Hozzáadja az új sofőrt a GameManager buszsofőröket tároló listájához.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: az új buszsofőr neve.
     * @return Mindig true értékkel tér vissza.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        List<model.players.BusChaffeur> busSs = gameManager.getBusChauffeurs();
        busSs.add(new model.players.BusChaffeur(args.get(0)));
        gameManager.setBusChauffeurs(busSs);

        /// /CONSOL OUT
        try {
            output.write(("A(z)"+ args.get(0)+" nevű buszsofőr szerepű játékos sikeresen hozzáadva a játékkonfigurációhoz.\n").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
