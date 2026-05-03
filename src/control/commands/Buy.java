package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class Buy extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        int iterate = 1;
        if(args.size() > 1){
            iterate = Integer.parseInt(args.get(1));
        }
        boolean success = true;
        for(int i = 0; i < iterate; i++){
            if(!gameManager.orderItem(args.get(0)))
                success = false;
        }
        if(success){
            /// /CONSOL OUT
            try {
                output.write(("A vásárlás sikeres. Bevételezve:"+ args.get(0)+".\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            /// /CONSOL OUT
            try {
                output.write((" A vásárlás sikertelen, nincs elég tőke.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return success;
    }
}
