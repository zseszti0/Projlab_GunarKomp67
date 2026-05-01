package model.map;

import java.util.*;

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
        return position.requestPath(position, destination, this);
    }

    public Tile calculateBFS(Tile start, Tile end){
        Queue<Tile> frontier = new LinkedList<>();
        Map<Tile, Tile> cameFrom = new HashMap<>();
        Set<Tile> visited = new HashSet<>();

        frontier.add(start);
        visited.add(start);

        // BFS ciklus a legrövidebb út megkeresésére
        while (!frontier.isEmpty()) {
            // A poll() kiveszi és visszaadja a sor első elemét
            Tile current = frontier.poll();

            if (current.equals(end)) {
                break;
            }

            List<Tile> validNeighbors = new ArrayList<>();
            for (Tile neighbor : current.getNeighbors()) {
                neighbor.addToBFSSubGraph(validNeighbors);
            }

            for (Tile validNeighbor : validNeighbors) {
                if (!visited.contains(validNeighbor)) {
                    visited.add(validNeighbor);
                    cameFrom.put(validNeighbor, current);
                    frontier.add(validNeighbor);
                }
            }
        }

        // Ha az úticél nincs a cameFrom kulcsai között, nem találtunk utat
        if (!cameFrom.containsKey(end)) {
            return start;
        }

        // Visszalépkedés a start mezőig, hogy megkapjuk a legelső lépést
        Tile step = end;
        while (!cameFrom.get(step).equals(start)) {
            step = cameFrom.get(step);
        }

        return step;
    }
}
