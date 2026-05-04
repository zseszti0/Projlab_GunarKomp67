package control.commands;

import control.GameManager;

import java.io.OutputStream;
import java.util.List;

/**
 * Parancs, amely a játék időzítőjének (tick) léptetéséért felelős.
 */
public class Tick extends GameCommand{

    /**
     * Végrehajtja a tick parancsot, előre léptetve az időt a játékban.
     * Lépései:
     * 1. Meghívja a GameManager tickTimer() metódusát, amely frissíti az aktív elemek (pl. hó, jég) állapotát,
     * és kezeli a játék körökre osztott mechanikáját.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args A parancs argumentumai (ennél a paracsnál üres lehet, nem használjuk fel).
     * @param output A kimeneti stream, ahová a lehetséges konzolos üzeneteket írjuk.
     * @return Mindig true értékkel tér vissza, jelezve a sikeres végrehajtást.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
       gameManager.tickTimer();
       return true;
    }
}
