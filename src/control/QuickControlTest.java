package control;

public class QuickControlTest {
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
