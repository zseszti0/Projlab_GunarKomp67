package control.commands;

import control.GameManager;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Játékparancs, amellyel egy takarító (Cleaner) egy új eszközt vásárolhat.
 */
public class Buy extends GameCommand {
    /**
     * Végrehajtja a kiegészítő megvásárlását.
     * Lépései:
     * 1. Megkeresi a megadott nevű (args[0]) takarítót. Ha nem találja, azonnal kilép (false).
     * 2. Meghívja a megtalált takarító buyAttachment() metódusát, átadva a kiegészítő azonosítóját (args[1]).
     * 3. Visszatér a takarító belső vásárlási logikájának eredményével.
     *
     * @param gameManager A játék állapotát kezelő objektum.
     * @param args args[0]: a vásárló takarító neve, args[1]: a vásárolandó kiegészítő azonosítója.
     * @return true, ha a takarító létezik és a vásárlás sikeres volt, egyébként false.
     */
    @Override
    public boolean execute(GameManager gameManager, List<String> args, OutputStream output) {
        int iterate = 1;
        if(args.size() > 1){
            iterate = Integer.parseInt(args.get(2));
        }
        boolean success = true;
        for(int i = 0; i < iterate; i++){
            if(!gameManager.orderItem(args.get(0)))
                success = false;
        }
        if(success){
            //CONSOLE OUT
            try {
                output.write(("A vásárlás sikeres. "+ args.get(0)+" vásárolva.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            //CONSOLE OUT
            try {
                output.write((" A vásárlás sikertelen, nincs elég tőke.\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return success;
    }
}
