package control;

import model.map.Tile;
import model.players.*;
import model.shop.base.Shop;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static Shop shop;

    private List<IAutomatic> automata = new ArrayList<>();

    private List<Cleaner> cleaners = new ArrayList<>();
    private List<BusChaffeur> busChauffeurs = new ArrayList<>();
    private List<Tile> tiles = new ArrayList<>();


    private int currentPlayerIndex = 0;
    private int currentActorRemainingAp = 0;

    public void tickTimer(){
        automata.forEach(IAutomatic::update);
    }

    void startGame(){
        currentPlayerIndex = 0;
        //bla bla
    }






}
