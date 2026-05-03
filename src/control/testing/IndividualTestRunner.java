package control.testing;

import control.GameManager;
import control.XMLParser;

/**
 * Ez az osztály valósítja meg az egyes tesztek lefuttatását.
 */
public class IndividualTestRunner {
    /**
     * Lefuttat egy darab tesztet.
     * @param config A teszt configurálása.
     *               config[0]: a teszt directory-ja
     *               config[1]: az arrange XML neve
     *               config[2]: az act neve
     *               config[3]: az assert XML neve
     */
    public static void run(String[] config) {
        XMLParser parser = new XMLParser();
        /*
         * ARRANGE:
        */
        GameManager gameManager = parser.loadGame(config[0] + "/arrange/" + config[1]);
        /*
         * ACT:
        */
        String[] commands = readAct(config[2]);
        //commandok véghez vitele:

        /*
         * ASSERT:
        */
        parser.saveGame(gameManager, config[0] + "/result/result.xml");
        parser.assertXMLFile(config[0] + "/result/result.xml", config[0] + "/assert/" + config[3]);

        GameManager gameManager2 = parser.loadGame("src/control/test2.xml");
        if(gameManager.equals(gameManager2)){
            System.out.println("yippie");
        }
    }

    /**
     * Beaolvassa a .txt formátumú act-ben lévő commandokat.
     * @param directory Az act.txt elérési útvonala.
     * @return A .txt-ből kiolvasott commandok egy String tömbben
     */
    public static String[] readAct(String directory) {

    }
}
