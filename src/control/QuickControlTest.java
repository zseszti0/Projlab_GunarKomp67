package control;

/**
 * A vezérlés és modell gyors tesztelésére szolgáló tesztosztály.
 * Elsődleges célja az XML alapú mentés és betöltés
 * integritásának és helyes működésének ellenőrzése.
 */
public class QuickControlTest {
    /**
     * A tesztelés belépési pontja.
     * Lépései:
     * 1. Létrehoz egy új XMLParser példányt.
     * 2. Betölt egy meglévő játékállást a "src/control/test.xml" fájlból.
     * 3. A betöltött GameManager állapotát kimenti egy új "src/control/test2.xml" fájlba.
     * 4. Meghívja az assertXMLFile metódust, ami ellenőrzi, hogy a két XML fájl megegyezik-e.
     * 5. Betölti az új "test2.xml" fájlt egy második GameManager példányba.
     * 6. Összehasonlítja a két GameManager állapotát (ha egyenlőek, akkor kiírja, hogy: "yippie").
     *
     * @param args A parancssori argumentumok (jelen esetben nincsenek felhasználva).
     */
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        GameManager gameManager = parser.loadGame("src/control/test.xml");
        parser.saveGame(gameManager, "src/control/test2.xml");
        parser.assertXMLFile("src/control/test2.xml", "src/control/test.xml");

        GameManager gameManager2 = parser.loadGame("src/control/test2.xml");
        if(gameManager.equals(gameManager2)){
            System.out.println("yippie");
        }
    }
}
