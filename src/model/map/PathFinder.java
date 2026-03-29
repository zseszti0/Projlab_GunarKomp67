package model.map;

import skeleton.Skeleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Legrovidebb utkereses megvalositasaert felelos algoritmus segedoszetaly.
 * Az automatikus mozgast vegzo entitasok (jelen esetben NPCDriver) hasznaljak a navigalashoz.
 */
public class PathFinder {

    /**
     * Legrovidebb utkereso algoritmussal megtalalja a legrovidebb utat a poziciobol a celba,
     * beleszamolva a csuszast es kulonbozo jarmu lephetoseget. A szkeleton fazisban a tesztelotol keri be az iranyt.
     * @param position a jelenlegi mezo, ahonnan a jarmu lepni szeretne
     * @param destination a cel mezo, ahova el akar jutni.
     *                    a szkeleton implementacio jelenleg nem hasznalja
     *                    ezt a parametert a tesztelo altali kivalasztas miatt
     * @return a kivalasztott szomszedos mezo, ahova a lepes tortenik
     */
    public Tile findNextStep(Tile position, Tile destination){
        List<Object> options = new ArrayList<>(position.getNeighbors());

        int answerIndex = Skeleton.askListQuestion("Hova szeretnél lépni?", options);

        return (Tile) options.get(answerIndex);
    }
}
