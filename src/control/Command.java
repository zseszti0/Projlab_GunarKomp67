package control;

import java.io.OutputStream;
import java.util.List;

/**
 * Általános alaposztály a játékban kiadható parancsokhoz.
 */
public class Command {
    /**
     * A parancs végrehajtását indító metódus.
     * Mivel ez egy alaposztály, jelenleg egy üres vázat képvisel.
     * Lépései:
     * 1. Alapértelmezetten azonnal true (igaz) értékkel tér vissza.
     * Ezt a leszármazott osztályok (pl. Buy, Drive) fogják felülírni a valós logikával.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args A parancssori argumentumok listája.
     * @return Mindig true.
     */
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        return true;
    }
}
