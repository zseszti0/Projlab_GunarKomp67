package model.map;

import skeleton.Skeleton;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    public Tile findNextStep(Tile position, Tile destination){
        List<Object> options = new ArrayList<>(position.getNeighbors());

        int answerIndex = Skeleton.askListQuestion("Hova szeretnél lépni?", options);

        return (Tile) options.get(answerIndex);
    }
}
