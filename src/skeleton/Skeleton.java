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

    private static Map<String, ModelInit> models = new HashMap<>();
    private static List<String> keys = new ArrayList<>();

    private static void menuPrint(){
        log("Válasszon opciót:");
        for(int i = 0; i < models.size(); i++){
            log((i+1) + ". " + keys.get(i));
        }
    }

    public static void main(String[] args) {
        //init the usecases
        models.put("Lépés söprő fejjel tiszta mezőre", new ModelInit("MoveWithSweeperToCleanTile"));
        keys.add("Lépés söprő fejjel tiszta mezőre");
        models.put("Lépés söprő fejjel sekély havas mezőre", new ModelInit("MoveWithSweeperToShallowSnowyTile"));
        keys.add("Lépés söprő fejjel sekély havas mezőre");
        models.put("Lépés hányó fejjel sekély havas mezőre", new ModelInit("MoveWithBlowerToShallowSnowyTile"));
        keys.add("Lépés hányó fejjel sekély havas mezőre");
        models.put("Lépés jégtörő fejjel jeges mezőre", new ModelInit("MoveWithIcebreakerToIcyTile"));
        keys.add("Lépés jégtörő fejjel jeges mezőre");
        models.put("Lépés sószóró fejjel sekély havas mezőre", new ModelInit("MoveWithSalterToShallowSnowyTile"));
        keys.add("Lépés sószóró fejjel sekély havas mezőre");
        models.put("Lépés sárkány fejjel sekély havas mezőre", new ModelInit("MoveWithDragonToShallowSnowyTile"));
        keys.add("Lépés sárkány fejjel sekély havas mezőre");
        models.put("Söprő fej vásárlása", new ModelInit("BuySweeper"));
        keys.add("Söprő fej vásárlása");
        models.put("Só vásárlása", new ModelInit("BuySalt"));
        keys.add("Só vásárlása");
        models.put("Hókotró vásárlása", new ModelInit("BuySnowShovel"));
        keys.add("Hókotró vásárlása");
        models.put("Söprő fej cseréje hányó fejre", new ModelInit("ChangeSweeperToBlower"));
        keys.add("Söprő fej cseréje hányó fejre");
        models.put("Autó lépése karambol nélkül", new ModelInit("CarMoveWithoutCrash"));
        keys.add("Autó lépése karambol nélkül");
        models.put("Autó lépése karambollal", new ModelInit("CarMoveWithCrash"));
        keys.add("Autó lépése karambollal");
        models.put("Sómentes mezők állapotának frissítése", new ModelInit("UnsaltedUpdate"));
        keys.add("Sómentes mezők állapotának frissítése");
        models.put("Sózott mezők állapotának frissítése", new ModelInit("SaltedUpdate"));
        keys.add("Sózott mezők állapotának frissítése");
        models.put("Autó elakadás", new ModelInit("CarMoveWithCrash"));
        keys.add("Autó elakadás");

        startLogging();
        menuPrint();

        int answer = scan();

        ModelInit model = models.get(keys.get(answer - 1));

        switch (answer) {
            case 1,2,3,4,5,6:{
                Cleaner cleaner = model.cleaners.get(0);

                String[] tiles = new String[model.tiles.size()];

                for (int i = 0; i < model.tiles.size(); i++) {
                    tiles[i] = model.tiles.get(i).getName();
                }

                int listNumber = askListQuestion("Hova lépjen?", tiles);

                cleaner.drive(cleaner.getVehicles().get(0), model.tiles.get(listNumber-1));
                break;
            }
            case 7: {
                Cleaner cleaner = model.cleaners.get(0);
                cleaner.shop("Söprő fej", model.shop);
                break;
            }
            case 8: {
                Cleaner cleaner = model.cleaners.get(0);
                cleaner.shop("Só", model.shop);
                break;
            }
            case 9: {
                Cleaner cleaner = model.cleaners.get(0);
                cleaner.shop("Hókotró", model.shop,model.tiles.get(0));
                break;
            }
            case 10: {
                Cleaner cleaner = model.cleaners.get(0);
                Inventory inventory = cleaner.getInventory();
                List<Attachment> attachments = inventory.getAttachments();
                cleaner.changeAttachment(cleaner.getVehicles().get(0), attachments.get(0));
                break;
            }
            case 11, 12, 15: {
                NPCDriver npcDriver = model.npcDriver;
                npcDriver.update();
                break;
            }
            case 13,14 : {
                List<Tile> tiles = model.tiles;
                for(Tile tile : tiles){
                    tile.update();
                }
                break;
            }
        }
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