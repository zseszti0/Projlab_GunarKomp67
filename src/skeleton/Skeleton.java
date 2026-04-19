package skeleton;

import model.inventory.Inventory;
import model.map.Tile;
import model.map.tilestates.CleanTileState;
import model.players.Cleaner;
import model.players.ICleaner;
import model.players.NPCDriver;
import model.shop.attachements.Attachment;
import java.util.Scanner;

import java.util.*;

public class Skeleton {

    private static List<String> keys = new ArrayList<>();

    private static void menuPrint(){
    }

    public static void main(String[] args) {
    }

    public static void log(String command){}

    public static boolean askBoolQuestion(String question){
        return true;
    }
    public static int askListQuestion(String question, String[] options){
        return 0;
    }

    public static int scan(){
        Scanner sc = new Scanner(System.in);

        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            log("Hiba: Kérjük, számot adjon meg!");
            return 0;
        }
    }

    public static void startLogging(){

    }
}