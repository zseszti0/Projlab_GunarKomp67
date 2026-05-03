package control.commands;

import control.GameManager;
import control.XMLParser;

import java.util.List;

public class Save extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        XMLParser parser = new XMLParser();
        parser.saveGame(gameManager, args.get(0));
        return true;
    }
}
