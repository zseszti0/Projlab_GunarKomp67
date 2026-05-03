package control;

import java.io.OutputStream;
import java.util.List;

public class Command {
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        return true;
    }
}
