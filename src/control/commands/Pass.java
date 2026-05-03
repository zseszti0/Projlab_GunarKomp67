package control.commands;

import control.GameManager;

import java.util.List;

/**
 * Játékparancs az aktuális játékos körének kihagyására (passzolás).
 */
public class Pass extends GameCommand {
    /**
     * Végrehajtja a passzolást.
     * Lépései:
     * 1. Meghívja a GameManager pass() metódusát, ami átadja a kört a következő entitásnak.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args A parancs nem használ argumentumokat.
     * @return Mindig true értékkel tér vissza.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        gameManager.pass();
        return true;
    }
}
