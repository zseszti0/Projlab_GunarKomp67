package control.commands;

import control.GameManager;

import java.util.List;

public class Buy extends GameCommand {
    @Override
    public boolean execute(GameManager gameManager, List<String> args) {
        int iterate = 1;
        if(args.size() > 1){
            iterate = Integer.parseInt(args.get(1));
        }
        boolean success = true;
        for(int i = 0; i < iterate; i++){
            if(!gameManager.orderItem(args.get(0)))
                success = false;
        }
        return success;
    }
}
