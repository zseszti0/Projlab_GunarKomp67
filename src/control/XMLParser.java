package control;

import model.map.Tile;
import java.util.List;

import model.inventory.Inventory;
import model.map.Lane;
import model.map.PathFinder;
import model.map.tilestates.*;
import model.players.BusChaffeur;
import model.players.Cleaner;
import model.players.NPCDriver;
import model.shop.attachements.*;
import model.shop.consumables.*;
import model.vehicles.Bus;
import model.vehicles.Car;
import model.vehicles.SnowShovel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    public List<Tile> LoadMap(String input) {
        return null;
    }

    public void SaveGame(GameManager gameManager, String output) {
    }

    public GameManager loadGame(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // 1. Térkép és Mezők (Tile & Lane) beolvasása
            Map<String, Tile> tileMap = new HashMap<>();
            Map<String, Lane> laneMap = new HashMap<>();
            Map<String, List<String>> tileNeighborsMap = new HashMap<>();
            List<Tile> allTiles = new ArrayList<>();

            NodeList mapList = doc.getElementsByTagName("map");
            if (mapList.getLength() > 0) {
                Element mapNode = (Element) mapList.item(0);
                NodeList tiles = mapNode.getElementsByTagName("tile");

                // Első kör: Objektumok létrehozása üres szomszédsági listákkal
                for (int i = 0; i < tiles.getLength(); i++) {
                    Element tileElement = (Element) tiles.item(i);
                    String id = tileElement.getAttribute("id");
                    TileState state = getTileStateByType(tileElement.getAttribute("type"));
                    boolean isSalted = Boolean.parseBoolean(tileElement.getAttribute("isSalted"));
                    boolean isRubbled = Boolean.parseBoolean(tileElement.getAttribute("isRubbled"));
                    int compIdx = getIntAttribute(tileElement, "compressionIndex", 0);
                    int meltIdx = getIntAttribute(tileElement, "saltMeltingIndex", 0);
                    int fadeIdx = getIntAttribute(tileElement, "rubbleFadingIndex", 0);

                    // Sáv kezelése
                    Lane tileLane = null;
                    NodeList lanes = tileElement.getElementsByTagName("lane");
                    if (lanes.getLength() > 0) {
                        String laneId = ((Element) lanes.item(0)).getAttribute("id");
                        laneMap.putIfAbsent(laneId, new Lane(new ArrayList<>(), laneId));
                        tileLane = laneMap.get(laneId);
                    }

                    // Lista előkészítése a szomszédoknak
                    List<Tile> neighbors = new ArrayList<>();

                    // FELTÉTELEZETT TELJES KONSTRUKTOR:
                    Tile tile = new Tile(id, state, isSalted, isRubbled, compIdx, meltIdx, fadeIdx, neighbors, tileLane);
                    tileMap.put(id, tile);
                    allTiles.add(tile);
                    if (tileLane != null) tileLane.getTiles().add(tile);

                    // Szomszéd ID-k elmentése későbbi összekötéshez
                    List<String> neighborIds = new ArrayList<>();
                    NodeList neighborNodes = tileElement.getElementsByTagName("neighbor");
                    for (int j = 0; j < neighborNodes.getLength(); j++) {
                        neighborIds.add(((Element) neighborNodes.item(j)).getAttribute("id"));
                    }
                    tileNeighborsMap.put(id, neighborIds);
                }

                // Második kör: Szomszédok referenciáinak feltöltése
                for (String tileId : tileMap.keySet()) {
                    Tile tile = tileMap.get(tileId);
                    for (String neighborId : tileNeighborsMap.get(tileId)) {
                        if (tileMap.containsKey(neighborId)) {
                            tile.getNeighbors().add(tileMap.get(neighborId));
                        }
                    }
                }
            }

            // 2. Takarítók beolvasása
            List<Cleaner> cleaners = new ArrayList<>();
            NodeList cleanerNodes = doc.getElementsByTagName("cleaner");
            for (int i = 0; i < cleanerNodes.getLength(); i++) {
                Element cleanerNode = (Element) cleanerNodes.item(i);
                String id = cleanerNode.getAttribute("id");

                Element invNode = getSingleChildElement(cleanerNode, "inventory");
                int money = (invNode != null && getSingleChildElement(invNode, "money") != null)
                        ? getIntAttribute(getSingleChildElement(invNode, "money"), "amount", 0) : 0;

                List<Attachment> attachments = new ArrayList<>();
                List<Consumable> consumables = new ArrayList<>();

                if (invNode != null) {
                    NodeList attNodes = invNode.getElementsByTagName("attachment");
                    for (int j = 0; j < attNodes.getLength(); j++) {
                        Element attNode = (Element) attNodes.item(j);
                        attachments.add(createAttachment(attNode.getAttribute("type"), attNode.getAttribute("id")));
                    }
                    NodeList consNodes = invNode.getElementsByTagName("consumable");
                    for (int j = 0; j < consNodes.getLength(); j++) {
                        Element consNode = (Element) consNodes.item(j);
                        consumables.add(createConsumable(consNode.getAttribute("type"), consNode.getAttribute("id"), getIntAttribute(consNode, "amount", 0)));
                    }
                }

                // FELTÉTELEZETT TELJES KONSTRUKTOR:
                Inventory inventory = new Inventory(id + "_Inv", money, attachments, consumables);
                List<SnowShovel> fleet = new ArrayList<>();

                NodeList shovelNodes = cleanerNode.getElementsByTagName("snowShovel");
                for (int j = 0; j < shovelNodes.getLength(); j++) {
                    Element shovelNode = (Element) shovelNodes.item(j);
                    String shovelId = shovelNode.getAttribute("id");

                    Element posNode = getSingleChildElement(shovelNode, "position");
                    Tile position = (posNode != null) ? tileMap.get(posNode.getAttribute("id")) : null;

                    Element attNode = getSingleChildElement(shovelNode, "attachment");
                    Attachment equipped = (attNode != null) ? createAttachment(attNode.getAttribute("type"), attNode.getAttribute("id")) : null;

                    // FELTÉTELEZETT TELJES KONSTRUKTOR:
                    SnowShovel shovel = new SnowShovel(shovelId, position, equipped);
                    fleet.add(shovel);
                    if (position != null) position.setVehicle(shovel);
                }

                // FELTÉTELEZETT TELJES KONSTRUKTOR:
                cleaners.add(new Cleaner(id, inventory, fleet));
            }

            // 3. Buszsofőrök beolvasása
            List<BusChaffeur> busChauffeurs = new ArrayList<>();
            NodeList busChauffeurNodes = doc.getElementsByTagName("busChauffeur");
            for (int i = 0; i < busChauffeurNodes.getLength(); i++) {
                Element bcNode = (Element) busChauffeurNodes.item(i);
                String id = bcNode.getAttribute("id");

                List<Bus> fleet = new ArrayList<>();
                NodeList busNodes = bcNode.getElementsByTagName("bus");
                for (int j = 0; j < busNodes.getLength(); j++) {
                    Element busNode = (Element) busNodes.item(j);
                    String busId = busNode.getAttribute("id");
                    boolean isStunned = Boolean.parseBoolean(busNode.getAttribute("isStunned"));

                    Element posNode = getSingleChildElement(busNode, "position");
                    Tile position = (posNode != null) ? tileMap.get(posNode.getAttribute("id")) : null;

                    Element destNode = getSingleChildElement(busNode, "destination");
                    Tile destination = (destNode != null) ? tileMap.get(destNode.getAttribute("id")) : null;

                    List<Tile> landMarks = new ArrayList<>();
                    NodeList lmNodes = busNode.getElementsByTagName("landMark");
                    for (int k = 0; k < lmNodes.getLength(); k++) {
                        landMarks.add(tileMap.get(((Element) lmNodes.item(k)).getAttribute("id")));
                    }

                    // FELTÉTELEZETT TELJES KONSTRUKTOR:
                    Bus bus = new Bus(busId, position, landMarks, destination, isStunned);
                    fleet.add(bus);
                    if (position != null) position.setVehicle(bus);
                }

                // FELTÉTELEZETT TELJES KONSTRUKTOR:
                busChauffeurs.add(new BusChaffeur(id, fleet));
            }

            // 4. NPC Sofőrök beolvasása
            List<NPCDriver> npcDrivers = new ArrayList<>();
            NodeList npcNodes = doc.getElementsByTagName("NPCDriver");
            for (int i = 0; i < npcNodes.getLength(); i++) {
                Element npcNode = (Element) npcNodes.item(i);
                String id = npcNode.getAttribute("id");

                List<Car> fleet = new ArrayList<>();
                NodeList carNodes = npcNode.getElementsByTagName("car");
                for (int j = 0; j < carNodes.getLength(); j++) {
                    Element carNode = (Element) carNodes.item(j);
                    String carId = carNode.getAttribute("id");
                    boolean isCrashed = Boolean.parseBoolean(carNode.getAttribute("isCrashed"));

                    Element posNode = getSingleChildElement(carNode, "position");
                    Tile position = (posNode != null) ? tileMap.get(posNode.getAttribute("id")) : null;

                    Element destNode = getSingleChildElement(carNode, "destination");
                    Tile destination = (destNode != null) ? tileMap.get(destNode.getAttribute("id")) : null;

                    List<Tile> landMarks = new ArrayList<>();
                    NodeList lmNodes = carNode.getElementsByTagName("landMark");
                    for (int k = 0; k < lmNodes.getLength(); k++) {
                        landMarks.add(tileMap.get(((Element) lmNodes.item(k)).getAttribute("id")));
                    }

                    // FELTÉTELEZETT TELJES KONSTRUKTOR:
                    Car car = new Car(carId, position, landMarks, destination, isCrashed);
                    fleet.add(car);
                    if (position != null) position.setVehicle(car);
                }

                // FELTÉTELEZETT TELJES KONSTRUKTOR:
                npcDrivers.add(new NPCDriver(id, new PathFinder("PF_" + id), fleet));
            }

            // 5. Config beolvasása és GameManager példányosítása
            boolean randomized = false;
            String currentActorId = null;
            int currentActorAp = 0;
            List<String> actorQueue = new ArrayList<>();

            NodeList configNodes = doc.getElementsByTagName("config");
            if (configNodes.getLength() > 0) {
                Element configNode = (Element) configNodes.item(0);
                randomized = Boolean.parseBoolean(configNode.getAttribute("randomized"));

                Element currentActor = getSingleChildElement(configNode, "currentActor");
                if (currentActor != null) {
                    currentActorId = currentActor.getAttribute("id");
                    currentActorAp = getIntAttribute(currentActor, "ap", 0);
                }

                Element queueNode = getSingleChildElement(configNode, "actorQueue");
                if (queueNode != null) {
                    NodeList actors = queueNode.getElementsByTagName("actor");
                    for (int i = 0; i < actors.getLength(); i++) {
                        actorQueue.add(((Element) actors.item(i)).getAttribute("id"));
                    }
                }
            }

            // FELTÉTELEZETT TELJES KONSTRUKTOR:
            return new GameManager(randomized, currentActorId, currentActorAp, actorQueue, allTiles, cleaners, busChauffeurs, npcDrivers);

        } catch (Exception e) {
            System.err.println("Hiba történt az XML beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // --- SEGÉDFÜGGVÉNYEK ---

    private TileState getTileStateByType(String type) {
        if (type == null) return CleanTileState.getInstance();
        switch (type) {
            case "ShallowSnowy": return ShallowSnowyTileState.getInstance();
            case "DeepSnowy": return DeepSnowyTileState.getInstance();
            case "Icy": return IcyTileState.getInstance();
            case "Blocked": return BlockedTileState.getInstance();
            default: return CleanTileState.getInstance();
        }
    }

    private Attachment createAttachment(String type, String id) {
        int idNum = id != null ? id.hashCode() : 0;
        if (type == null) return new SweeperHead("SweeperHead", idNum);
        switch (type) {
            case "SalterHead": return new SalterHead(type, idNum);
            case "BlowerHead": return new BlowerHead(type, idNum);
            case "IcebreakerHead": return new IcebreakerHead(type, idNum);
            case "CobblestoneHead": return new CobblestoneHead(type, idNum);
            case "DragonHead": return new DragonHead(type, idNum);
            default: return new SweeperHead(type, idNum);
        }
    }

    private Consumable createConsumable(String type, String id, int amount) {
        int idNum = id != null ? id.hashCode() : 0;
        if (type == null) return null;
        switch (type) {
            case "Salt": return new Salt(idNum, amount, "Salt");
            case "Rubble": return new Rubble(idNum, amount, "Rubble");
            case "Biokerosene": return new Biokerosene(idNum, amount, "Biokerosene");
            default: return null;
        }
    }

    private int getIntAttribute(Element element, String attribute, int defaultValue) {
        String val = element.getAttribute(attribute);
        if (val == null || val.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private Element getSingleChildElement(Element parent, String tagName) {
        NodeList children = parent.getElementsByTagName(tagName);
        if (children.getLength() > 0) {
            return (Element) children.item(0);
        }
        return null;
    }
}