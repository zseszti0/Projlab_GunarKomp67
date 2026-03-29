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
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos*/
    private final String name;

    /**
     * Visszaadja az objektum nevet.
     * Elorelathatolag csak a skeletonban kell
     * */
    public String getName() {
        return name;
    }

    /**
     * Konstruktor a PathFinder osztályhoz.
     * @param name az objektum nevet ado string*/
    public PathFinder(String name) {
        this.name = name;
    }



    /**
     * Legrovidebb utkereso algoritmussal megtalalja a legrovidebb utat a poziciobol a celba,
     * beleszamolva a csuszast es kulonbozo jarmu lephetoseget. A szkeleton fazisban a tesztelotol keri be az iranyt.
     * @param position a jelenlegi mezo, ahonnan a jarmu lepni szeretne
     * @param destination a cel mezo, ahova el akar jutni.
     *                    a szkeleton implementacio jelenleg nem hasznalja
     *                    ezt a parametert a tesztelo altali kivalasztas miatt
     * @return a kivalasztott szomszedos mezo, ahova a lepes tortenik
     */
    public Tile findNextStep(Tile position, Tile destination) {
        List<Tile> neighbors = position.getNeighbors();

        String[] options = new String[neighbors.size()];

        for (int i = 0; i < neighbors.size(); i++) {
            options[i] = neighbors.get(i).getName();
        }

        String[] optionsArray = options.toArray(new String[0]);

        int answerIndex = Skeleton.askListQuestion("Hova szeretnél lépni?", optionsArray);

        return neighbors.get(answerIndex);
    }
}
