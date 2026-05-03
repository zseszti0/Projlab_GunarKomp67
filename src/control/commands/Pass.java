package control.commands;

import control.GameManager;

import java.util.List;

public class Pass extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        gameManager.pass();
        return true;
    }
}
