package control;

public class QuickControlTest {
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        GameManager gameManager = parser.loadGame("src/control/test.xml");
        System.out.println("yippie");
    }
}
