package control;

import model.map.Tile;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

// Importáld be a modelled osztályait, pl:
// import model.GameManager;

public class XMLParser {
    public List<Tile> LoadMap(String input) {
        return null;
    }

    public void SaveGame(GameManager gameManager, String output) {
    }
    /**
     * A játékállapot betöltéséért felelős fő függvény.
     */
    public GameManager loadGame(String filePath) {
        // Feltételezem, hogy a GameManager tartalmazza az összefogó változókat
        GameManager gameManager = new GameManager();

        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // A root elem (bár a dokumentáció alapján egymás mellett is lehetnek a fő tag-ek,
            // de DOM parse-nál tipikusan van egy gyökér elem, ha nincs, a getElementsByTagName ugyanúgy működik)

            // 1. Config feldolgozása
            NodeList configList = doc.getElementsByTagName("config");
            if (configList.getLength() > 0) {
                parseConfig((Element) configList.item(0), gameManager);
            }

            // 2. Map feldolgozása
            NodeList mapList = doc.getElementsByTagName("map");
            if (mapList.getLength() > 0) {
                parseMap((Element) mapList.item(0), gameManager);
            }

            // 3. Takarítók (Cleaner) feldolgozása
            NodeList cleanerList = doc.getElementsByTagName("cleaner");
            for (int i = 0; i < cleanerList.getLength(); i++) {
                parseCleaner((Element) cleanerList.item(i), gameManager);
            }

            // 4. Buszsofőrök (busChauffeur) feldolgozása
            NodeList busList = doc.getElementsByTagName("busChauffeur");
            for (int i = 0; i < busList.getLength(); i++) {
                parseBusChauffeur((Element) busList.item(i), gameManager);
            }

            // 5. NPC sofőrök (NPCDriver) feldolgozása
            NodeList npcList = doc.getElementsByTagName("NPCDriver");
            for (int i = 0; i < npcList.getLength(); i++) {
                parseNPCDriver((Element) npcList.item(i), gameManager);
            }

            // Opcionális: Ha az azonosítókat eddig csak String-ként mentetted el,
            // itt érdemes meghívni egy függvényt a GameManageren, ami összeköti az objektumokat
            // (pl. a Tile-ok szomszédságait és a járművek pozícióit feloldja a referenciákból).
            // gameManager.resolveReferences();

        } catch (Exception e) {
            System.err.println("Hiba történt az XML beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }

        return gameManager;
    }

    // --- SEGÉDFÜGGVÉNYEK ---

    private void parseConfig(Element configNode, GameManager gameManager) {
        // CurrentActor beolvasása
        NodeList currentActorList = configNode.getElementsByTagName("currentActor");
        if (currentActorList.getLength() > 0) {
            Element currentActor = (Element) currentActorList.item(0);
            String id = currentActor.getAttribute("id");
            int ap = getIntAttribute(currentActor, "ap", 0);
            // gameManager.setCurrentActor(id, ap);
        }

        // ActorQueue beolvasása
        NodeList actorQueueList = configNode.getElementsByTagName("actorQueue");
        if (actorQueueList.getLength() > 0) {
            Element actorQueue = (Element) actorQueueList.item(0);
            NodeList actors = actorQueue.getElementsByTagName("actor");
            for (int i = 0; i < actors.getLength(); i++) {
                Element actor = (Element) actors.item(i);
                String actorId = actor.getAttribute("id");
                // gameManager.addActorToQueue(actorId);
            }
        }
    }

    private void parseMap(Element mapNode, GameManager gameManager) {
        NodeList tiles = mapNode.getElementsByTagName("tile");
        for (int i = 0; i < tiles.getLength(); i++) {
            Element tileElement = (Element) tiles.item(i);

            String id = tileElement.getAttribute("id");
            String type = tileElement.getAttribute("type");
            boolean isSalted = Boolean.parseBoolean(tileElement.getAttribute("isSalted"));
            boolean isRubbled = Boolean.parseBoolean(tileElement.getAttribute("isRubbled"));
            int compressionIndex = getIntAttribute(tileElement, "compressionIndex", 0);
            int saltMeltingIndex = getIntAttribute(tileElement, "saltMeltingIndex", 0);
            int rubbleFadingIndex = getIntAttribute(tileElement, "rubbleFadingIndex", 0);

            // TODO: Tile példányosítása és hozzáadása a gameManager-hez

            // Szomszédok (neighbors)
            NodeList neighbors = tileElement.getElementsByTagName("neighbor");
            for (int j = 0; j < neighbors.getLength(); j++) {
                Element neighbor = (Element) neighbors.item(j);
                String neighborId = neighbor.getAttribute("id");
                // A kapcsolatok összekötéséhez menthetjük
            }

            // Sávok (lanes)
            NodeList lanes = tileElement.getElementsByTagName("lane");
            for (int j = 0; j < lanes.getLength(); j++) {
                Element lane = (Element) lanes.item(j);
                String laneId = lane.getAttribute("id");
            }
        }
    }

    private void parseCleaner(Element cleanerNode, GameManager gameManager) {
        String cleanerId = cleanerNode.getAttribute("id");
        // TODO: Cleaner objektum létrehozása

        // SnowShovels beolvasása
        NodeList shovels = cleanerNode.getElementsByTagName("snowShovel");
        for (int i = 0; i < shovels.getLength(); i++) {
            Element shovelNode = (Element) shovels.item(i);
            String shovelId = shovelNode.getAttribute("id");

            Element posNode = getSingleChildElement(shovelNode, "position");
            String positionId = (posNode != null) ? posNode.getAttribute("id") : null;

            Element attachmentNode = getSingleChildElement(shovelNode, "attachment");
            String attachmentId = (attachmentNode != null) ? attachmentNode.getAttribute("id") : null;
            String attachmentType = (attachmentNode != null) ? attachmentNode.getAttribute("type") : null;

            // TODO: Hókotró létrehozása és hozzáadása a cleanerhez
        }

        // Inventory beolvasása
        Element inventoryNode = getSingleChildElement(cleanerNode, "inventory");
        if (inventoryNode != null) {
            Element moneyNode = getSingleChildElement(inventoryNode, "money");
            int money = (moneyNode != null) ? getIntAttribute(moneyNode, "amount", 0) : 0;

            // Inventory attachments
            NodeList attachments = inventoryNode.getElementsByTagName("attachment");
            for (int i = 0; i < attachments.getLength(); i++) {
                Element attNode = (Element) attachments.item(i);
                String attId = attNode.getAttribute("id");
                String attType = attNode.getAttribute("type");
            }

            // Consumables
            NodeList consumables = inventoryNode.getElementsByTagName("consumable");
            for (int i = 0; i < consumables.getLength(); i++) {
                Element consNode = (Element) consumables.item(i);
                String consId = consNode.getAttribute("id");
                String consType = consNode.getAttribute("type");
                int amount = getIntAttribute(consNode, "amount", 0);
            }
        }
    }

    private void parseBusChauffeur(Element busChauffeurNode, GameManager gameManager) {
        String chauffeurId = busChauffeurNode.getAttribute("id");
        // TODO: Buszsofőr példányosítása

        NodeList buses = busChauffeurNode.getElementsByTagName("bus");
        for (int i = 0; i < buses.getLength(); i++) {
            Element busNode = (Element) buses.item(i);
            String busId = busNode.getAttribute("id");
            boolean isStunned = Boolean.parseBoolean(busNode.getAttribute("isStunned"));

            Element posNode = getSingleChildElement(busNode, "position");
            String positionId = (posNode != null) ? posNode.getAttribute("id") : null;

            Element destNode = getSingleChildElement(busNode, "destination");
            String destinationId = (destNode != null) ? destNode.getAttribute("id") : null;

            NodeList landmarks = busNode.getElementsByTagName("landMark");
            for (int j = 0; j < landmarks.getLength(); j++) {
                Element landmarkNode = (Element) landmarks.item(j);
                String landmarkId = landmarkNode.getAttribute("id");
            }
            // TODO: Busz hozzáadása
        }
    }

    private void parseNPCDriver(Element npcDriverNode, GameManager gameManager) {
        String npcId = npcDriverNode.getAttribute("id");

        NodeList cars = npcDriverNode.getElementsByTagName("car");
        for (int i = 0; i < cars.getLength(); i++) {
            Element carNode = (Element) cars.item(i);
            String carId = carNode.getAttribute("id");
            boolean isCrashed = Boolean.parseBoolean(carNode.getAttribute("isCrashed"));

            Element posNode = getSingleChildElement(carNode, "position");
            String positionId = (posNode != null) ? posNode.getAttribute("id") : null;

            Element destNode = getSingleChildElement(carNode, "destination");
            String destinationId = (destNode != null) ? destNode.getAttribute("id") : null;

            NodeList landmarks = carNode.getElementsByTagName("landMark");
            for (int j = 0; j < landmarks.getLength(); j++) {
                Element landmarkNode = (Element) landmarks.item(j);
                String landmarkId = landmarkNode.getAttribute("id");
            }
            // TODO: Autó hozzáadása
        }
    }

    // --- HASZNOS UTIL FÜGGVÉNYEK ---

    /**
     * Kinyer egy egész számot az attribútumból, ha hiányzik vagy hibás, default értéket ad.
     */
    private int getIntAttribute(Element element, String attribute, int defaultValue) {
        String val = element.getAttribute(attribute);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Segédfüggvény egy konkrét egyedi gyerek tag gyors lekérésére (pl. position, inventory)
     */
    private Element getSingleChildElement(Element parent, String tagName) {
        NodeList children = parent.getElementsByTagName(tagName);
        if (children.getLength() > 0) {
            return (Element) children.item(0);
        }
        return null;
    }
}