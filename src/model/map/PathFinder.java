package model.map;

import java.util.*;

/**
 * Legrövidebb útkeresés megvalósításáért felelős algoritmus segédosztály.
 * Az automatikus mozgást végző entitások (jelen esetben NPCDriver) használják a navigáláshoz.
 */
public class PathFinder {
    /**
     * A logger számára használatos név, egyelőre csak a skeleton fázisban használatos.
     */
    private final String name;

    /**
     * Visszaadja az objektum nevét.
     * Lépései:
     * 1. Visszatér az útkereső nevével.
     *
     * @return Az útkereső azonosítója.
     */
    public String getName() {
        return name;
    }

    /**
     * Konstruktor a PathFinder osztályhoz.
     * Lépései:
     * 1. Beállítja az objektum nevét.
     *
     * @param name Az objektum nevét adó string.
     */
    public PathFinder(String name) {
        this.name = name;
    }

    /**
     * Kiszámítja a következő lépést a jelenlegi pozícióból a cél felé.
     * Lépései:
     * 1. Meghívja az aktuális mező requestPath() metódusát, átadva neki a célt és önmagát (a PathFinder-t).
     * 2. A mező állapota (pl. tiszta vagy jeges) alapján vagy a BFS algoritmus, vagy egy csúszási logika fut le.
     *
     * @param position A jelenlegi mező, ahonnan a jármű lépni szeretne.
     * @param destination A célmező, ahová a jármű el akar jutni.
     * @return A kiválasztott (szomszédos) mező, ahová a járműnek lépnie kell.
     */
    public Tile findNextStep(Tile position, Tile destination) {
        return position.requestPath(position, destination, this);
    }

    /**
     * BFS (Szélességi bejárás) algoritmus, amely megkeresi a legrövidebb érvényes utat a start és end mezők között.
     * Lépései:
     * 1. Inicializálja a bejáráshoz szükséges adatszerkezeteket: frontier (várakozási sor), visited (látogatott halmaz), cameFrom (szülők leképezése).
     * 2. Hozzáadja a kezdőmezőt a sorhoz és a látogatottakhoz.
     * 3. Amíg a sor nem üres, kiveszi az első elemet (current).
     * 4. Ha elérte a célmezőt (end), megszakítja a keresést.
     * 5. Begyűjti az aktuális mező összes olyan szomszédját, ahová a jármű ráléphet (validNeighbors).
     * 6. Bejárja az érvényes szomszédokat, és ha még nem lettek meglátogatva, rögzíti, hogy honnan érkeztünk beléjük (cameFrom), majd hozzáadja őket a sorhoz.
     * 7. Ha befejeződött a keresés és nem található út a célhoz, visszatér a kezdőmezővel (helyben marad).
     * 8. Visszaköveti az utat a céltól (end) egészen a kezdőmező (start) utáni első lépésig, majd ezzel a mezővel visszatér.
     *
     * @param start A kiindulási mező.
     * @param end A célmező.
     * @return Az útvonal első lépéseként szolgáló szomszédos mező.
     */
    public Tile calculateBFS(Tile start, Tile end){
        Queue<Tile> frontier = new LinkedList<>();
        Map<Tile, Tile> cameFrom = new HashMap<>();
        Set<Tile> visited = new HashSet<>();

        frontier.add(start);
        visited.add(start);

        while (!frontier.isEmpty()) {
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

        if (!cameFrom.containsKey(end)) {
            return start;
        }

        Tile step = end;
        while (!cameFrom.get(step).equals(start)) {
            step = cameFrom.get(step);
        }

        return step;
    }
}
