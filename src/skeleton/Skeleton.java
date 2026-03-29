package skeleton;

import model.inventory.Inventory;
import model.map.Tile;
import model.map.tilestates.CleanTileState;
import model.players.Cleaner;
import model.players.ICleaner;
import model.players.NPCDriver;
import model.shop.attachements.Attachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Skeleton {

    private static Map<String, ModelInit> models = new HashMap<>();

    private static void menuPrint(){
        log("Válasszon opciót:");
        for(int i = 0; i < models.size(); i++){
            log((i+1) + ". " + models.keySet().toArray()[i]);
        }
    }

    public static void main(String[] args) {
        //init the usecases
        models.put("Lépés söprő fejjel tiszta mezőre", new ModelInit("MoveWithSweeperToCleanTile"));
        models.put("Lépés söprő fejjel sekély havas mezőre", new ModelInit("MoveWithSweeperToShallowSnowyTile"));
        models.put("Lépés hányó fejjel sekély havas mezőre", new ModelInit("MoveWithBlowerToShallowSnowyTile"));
        models.put("Lépés jégtörő fejjel jeges mezőre", new ModelInit("MoveWithIcebreakerToIcyTile"));
        models.put("Lépés sószóró fejjel sekély havas mezőre", new ModelInit("MoveWithSalterToShallowSnowyTile"));
        models.put("Lépés sárkány fejjel sekély havas mezőre", new ModelInit("MoveWithDragonToShallowSnowyTile"));
        models.put("Söprő fej vásárlása", new ModelInit("BuySweeper"));
        models.put("Só vásárlása", new ModelInit("BuySalt"));
        models.put("Hókotró vásárlása", new ModelInit("BuySnowShovel"));
        models.put("Söprő fej cseréje hányó fejre", new ModelInit("ChangeSweeperToBlower"));
        models.put("Autó lépése karambol nélkül", new ModelInit("CarMoveWithoutCrash"));
        models.put("Autó lépése karambollal", new ModelInit("CarMoveWithCrash"));
        models.put("Sómentes mezők állapotának frissítése", new ModelInit("UnsaltedUpdate"));
        models.put("Sózott mezők állapotának frissítése", new ModelInit("SaltedUpdate"));
        models.put("Autó elakadás", new ModelInit("CarMoveWithCrash"));

        menuPrint();

        int answer = scan();

        ModelInit model = models.get(models.keySet().toArray()[answer-1]);
        switch (answer) {
            case 1,2,3,4,5,6:{
                Cleaner cleaner = model.cleaners.get(0);
                cleaner.drive(cleaner.getVehicles().get(0), model.tiles.get(0));
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
        return 0;
    }
}