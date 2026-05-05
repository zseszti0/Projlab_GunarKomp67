package control.commands;

import control.GameManager;

import java.io.OutputStream;
import java.util.List;

public class SnowTick extends GameCommand{
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        gameManager.tickSnowTimer();
        return true;
    }
}
