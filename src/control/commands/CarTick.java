package control.commands;

import control.GameManager;

import java.io.OutputStream;
import java.util.List;

public class CarTick  extends GameCommand{
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        gameManager.tickCarTimer();
        return true;
    }
}
