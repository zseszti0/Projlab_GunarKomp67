package control;

import model.map.Tile;

import java.io.*;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Az XML fájlok feldolgozásáért felelős osztály.
 * Lehetővé teszi a térképek és a teljes játékállás betöltését,
 * valamint a jelenlegi játékállás elmentését XML formátumban.
 */
public class XMLParser {
    private String sourceFolder;
    private XMLParser() {}
    private OutputStream outputStream;
    public XMLParser(String sourceFolder) {
        this.sourceFolder = sourceFolder;
        if(sourceFolder == "")
            outputStream = System.out;
        else {
            try {
                outputStream = new FileOutputStream(sourceFolder + "/output.txt",true);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Betölti a játéktáblát (mezőket és sávokat) a megadott XML fájlból.
     * * @param filePath Az olvasandó XML fájl elérési útvonala.
     * @return A betöltött térképet alkotó {@link Tile} objektumok listája.
     */
    public List<Tile> loadMap(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList mapList = doc.getElementsByTagName("map");
            if (mapList.getLength() > 0) {
                // Itt egy üres Map-et adunk át, mert a loadMap esetében nincs szükségünk
                // az ID alapján történő jármű-lehelyezésekhez a referenciákra utólag.
                return parseMapData((Element) mapList.item(0), new HashMap<>());
            }
        } catch (Exception e) {
            System.err.println("Hiba történt a térkép beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Elmenti az aktuális játékállást (térkép, entitások, konfiguráció, inventory stb.)
     * a megadott XML fájlba.
     * @param gameManager Az aktuális játékállapotot tartalmazó menedzser objektum.
     */
    public void saveGame(GameManager gameManager) {
        try {
            String output = sourceFolder + "/output.xml";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            // Root element
            Element root = doc.createElement("game");
            doc.appendChild(root);

            // 1. Save Config
            Element configElement = doc.createElement("config");
            if (gameManager.isRandomized()) {
                configElement.setAttribute("randomized", "true");
            }
            root.appendChild(configElement);

            // Current Actor
            Element currentActorElement = doc.createElement("currentActor");
            currentActorElement.setAttribute("id", gameManager.getCurrentActorId());
            currentActorElement.setAttribute("ap", String.valueOf(gameManager.getCurrentActorRemainingAp()));
            configElement.appendChild(currentActorElement);

            // Actor Queue
            Element queueElement = doc.createElement("actorQueue");
            for (String actorId : gameManager.getActorQueue()) {
                Element actorElement = doc.createElement("actor");
                actorElement.setAttribute("id", actorId);
                queueElement.appendChild(actorElement);
            }
            configElement.appendChild(queueElement);

            // 2. Save Map & Tiles
            Element mapElement = doc.createElement("map");
            root.appendChild(mapElement);

            for (Tile tile : gameManager.getTiles()) {
                Element tileElement = doc.createElement("tile");
                tileElement.setAttribute("id", tile.getName());

                // Get tile state type name
                String stateType = getTileStateTypeName(tile.getState());
                tileElement.setAttribute("type", stateType);

                // Only save optional attributes if they differ from default
                if (tile.isSalted()) {
                    tileElement.setAttribute("isSalted", "true");
                }
                if (tile.isRubbled()) {
                    tileElement.setAttribute("isRubbled", "true");
                }
                if (tile.getCompressionIndex() != 0) {
                    tileElement.setAttribute("compressionIndex", String.valueOf(tile.getCompressionIndex()));
                }
                if (tile.getSaltMeltingIndex() != 0) {
                    tileElement.setAttribute("saltMeltingIndex", String.valueOf(tile.getSaltMeltingIndex()));
                }
                if (tile.getRubbleFadingIndex() != 0) {
                    tileElement.setAttribute("rubbleFadingIndex", String.valueOf(tile.getRubbleFadingIndex()));
                }

                // Neighbors
                Element neighborsElement = doc.createElement("neighbors");
                if (tile.getNeighbors() != null) {
                    for (Tile neighbor : tile.getNeighbors()) {
                        Element neighborElement = doc.createElement("neighbor");
                        neighborElement.setAttribute("id", neighbor.getName());
                        neighborsElement.appendChild(neighborElement);
                    }
                }
                tileElement.appendChild(neighborsElement);

                // JAVÍTVA: Több sáv (Lane) kiírása a Tile-okhoz
                Element lanesElement = doc.createElement("lanes");
                if (tile.getLanes() != null) {
                    for (Lane lane : tile.getLanes()) {
                        Element laneElement = doc.createElement("lane");
                        laneElement.setAttribute("id", lane.getName());
                        lanesElement.appendChild(laneElement);
                    }
                }
                tileElement.appendChild(lanesElement);

                mapElement.appendChild(tileElement);
            }

            // 3. Save Cleaners
            for (Cleaner cleaner : gameManager.getCleaners()) {
                Element cleanerElement = doc.createElement("cleaner");
                cleanerElement.setAttribute("id", cleaner.getName());
                root.appendChild(cleanerElement);

                // Snow Shovels
                for (SnowShovel shovel : cleaner.getVehicles()) {
                    Element shovelElement = doc.createElement("snowShovel");
                    shovelElement.setAttribute("id", shovel.getName());
                    cleanerElement.appendChild(shovelElement);

                    // Position
                    Element posElement = doc.createElement("position");
                    if (shovel.getPosition() != null) {
                        posElement.setAttribute("id", shovel.getPosition().getName());
                    }
                    shovelElement.appendChild(posElement);

                    // Equipped Attachment
                    if (shovel.getEquippedAttachment() != null) {
                        Element attElement = doc.createElement("attachment");
                        attElement.setAttribute("id", shovel.getEquippedAttachment().getName());

                        // JAVÍTVA: Csak akkor írjuk ki a type attribútumot, ha létezik (referenciáknál nem írjuk ki)
                        String attType = shovel.getEquippedAttachment().getType();
                        if (attType != null && !attType.isEmpty()) {
                            attElement.setAttribute("type", attType);
                        }

                        shovelElement.appendChild(attElement);
                    }
                }

                // Inventory
                Element invElement = doc.createElement("inventory");
                cleanerElement.appendChild(invElement);

                // Money
                Element moneyElement = doc.createElement("money");
                int money = cleaner.getInventory().getMoney();
                if (money != 0) {
                    moneyElement.setAttribute("amount", String.valueOf(money));
                }
                invElement.appendChild(moneyElement);

                // Attachments in Inventory
                if (!cleaner.getInventory().getAttachments().isEmpty()) {
                    Element attachmentsElement = doc.createElement("attachments");
                    for (Attachment att : cleaner.getInventory().getAttachments()) {
                        Element attElement = doc.createElement("attachment");
                        attElement.setAttribute("id", att.getName());
                        attElement.setAttribute("type", att.getType());
                        attachmentsElement.appendChild(attElement);
                    }
                    invElement.appendChild(attachmentsElement);
                }

                // Consumables in Inventory
                if (!cleaner.getInventory().getConsumables().isEmpty()) {
                    Element consumablesElement = doc.createElement("consumables");
                    for (Consumable cons : cleaner.getInventory().getConsumables()) {
                        Element consElement = doc.createElement("consumable");
                        consElement.setAttribute("id", cons.getName());
                        consElement.setAttribute("type", cons.getType());
                        consElement.setAttribute("amount", String.valueOf(cons.getAmount()));
                        consumablesElement.appendChild(consElement);
                    }
                    invElement.appendChild(consumablesElement);
                }
            }

            // 4. Save BusChauffeurs
            for (BusChaffeur busChauffeur : gameManager.getBusChauffeurs()) {
                Element bcElement = doc.createElement("busChauffeur");
                bcElement.setAttribute("id", busChauffeur.getName());
                root.appendChild(bcElement);

                for (Bus bus : busChauffeur.getVehicles()) {
                    Element busElement = doc.createElement("bus");
                    busElement.setAttribute("id", bus.getName());
                    if (bus.isStunned()) {
                        busElement.setAttribute("isStunned", "true");
                    }
                    bcElement.appendChild(busElement);

                    // Position
                    Element posElement = doc.createElement("position");
                    if (bus.getPosition() != null) {
                        posElement.setAttribute("id", bus.getPosition().getName());
                    }
                    busElement.appendChild(posElement);

                    // Landmarks
                    Element landMarksElement = doc.createElement("landMarks");
                    if (bus.getLandMarks() != null) {
                        for (Tile landmark : bus.getLandMarks()) {
                            Element lmElement = doc.createElement("landMark");
                            lmElement.setAttribute("id", landmark.getName());
                            landMarksElement.appendChild(lmElement);
                        }
                    }
                    busElement.appendChild(landMarksElement);

                    // Current Destination
                    Element destElement = doc.createElement("destination");
                    Tile currentDest = bus.getCurrentDestination();
                    if (currentDest != null) {
                        destElement.setAttribute("id", currentDest.getName());
                    }
                    busElement.appendChild(destElement);
                }
            }

            // 5. Save NPCDrivers
            for (NPCDriver npcDriver : gameManager.getNpcDrivers()) {
                Element npcElement = doc.createElement("NPCDriver");
                npcElement.setAttribute("id", npcDriver.getName());
                root.appendChild(npcElement);

                for (Car car : npcDriver.getVehicles()) {
                    Element carElement = doc.createElement("car");
                    carElement.setAttribute("id", car.getName());
                    if (car.isCrashed()) {
                        carElement.setAttribute("isCrashed", "true");
                    }
                    npcElement.appendChild(carElement);

                    // Position
                    Element posElement = doc.createElement("position");
                    if (car.getPosition() != null) {
                        posElement.setAttribute("id", car.getPosition().getName());
                    }
                    carElement.appendChild(posElement);

                    // Landmarks
                    Element landMarksElement = doc.createElement("landMarks");
                    if (car.getLandMarks() != null) {
                        for (Tile landmark : car.getLandMarks()) {
                            Element lmElement = doc.createElement("landMark");
                            lmElement.setAttribute("id", landmark.getName());
                            landMarksElement.appendChild(lmElement);
                        }
                    }
                    carElement.appendChild(landMarksElement);

                    // Current Destination
                    Element destElement = doc.createElement("destination");
                    Tile currentDest = car.getCurrentDestination();
                    if (currentDest != null) {
                        destElement.setAttribute("id", currentDest.getName());
                    }
                    carElement.appendChild(destElement);
                }
            }

            // Write to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(output));
            transformer.transform(source, result);

        } catch (Exception e) {
            System.err.println("Hiba történt a játék mentésekor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Betölt egy teljes játékállást az XML fájlból. Létrehozza a térképet,
     * inicializálja az összes entitást (takarítók, buszsofőrök, NPC-k) és beállítja a referenciákat.
     * @param filePath A beolvasandó játékállás XML fájljának útvonala.
     * @return Egy inicializált {@link GameManager} példány a betöltött adatokkal.
     */
    public GameManager loadGame(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // 1. Térkép és Mezők (Tile & Lane) beolvasása a közös függvénnyel
            Map<String, Tile> tileMap = new HashMap<>();
            List<Tile> allTiles = new ArrayList<>();

            NodeList mapList = doc.getElementsByTagName("map");
            if (mapList.getLength() > 0) {
                // Itt átadjuk a tileMap-et, hogy a parseMapData feltöltse az ID -> Tile párokkal,
                // amik kellenek a járművek pozíciójának beállításához!
                allTiles = parseMapData((Element) mapList.item(0), tileMap);
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
                    Attachment equipped = null;
                    if (attNode != null) {
                        equipped = createAttachment(attNode.getAttribute("type"), attNode.getAttribute("id"));
                    }

                    // FELTÉTELEZETT TELJES KONSTRUKTOR:
                    SnowShovel shovel = new SnowShovel(shovelId, position, equipped);
                    fleet.add(shovel);
                    if (position != null) position.setVehicle(shovel);
                }

                // FELTÉTELEZETT TELJES KONSTRUKTOR:
                cleaners.add(new Cleaner(id, inventory, fleet));
            }

            // 3. Buszsofőrök beolvasása
            // ... (Itt változatlan maradt a tegnapi kód)
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

                    Bus bus = new Bus(busId, position, landMarks, destination, isStunned);
                    fleet.add(bus);
                    if (position != null) position.setVehicle(bus);
                }

                busChauffeurs.add(new BusChaffeur(id, fleet));
            }

            // 4. NPC Sofőrök beolvasása
            // ... (Itt változatlan maradt a tegnapi kód)
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

                    Car car = new Car(carId, position, landMarks, destination, isCrashed);
                    fleet.add(car);
                    if (position != null) position.setVehicle(car);
                }

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

            return new GameManager(randomized, currentActorId, currentActorAp, actorQueue, allTiles, cleaners, busChauffeurs, npcDrivers);

        } catch (Exception e) {
            System.err.println("Hiba történt az XML beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Összehasonlít két XML fájlt és meghatározza, hogy azonosak-e.
     * Ha különbségek vannak, kiírja őket a konzolra.
     * @param outputFile a mentett XML fájl elérési útja
     * @param assertFile az elvárt XML fájl elérési útja
     * @return true, ha a fájlok azonosak, false, ha különböznek
     */
    public boolean assertXMLFile(String outputFile, String assertFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            File outputFilePath = new File(outputFile);
            File assertFilePath = new File(assertFile);


            if (!outputFilePath.exists()) {
                System.err.println("HIBA: Kimeneti fájl nem található: " + outputFile);
                return false;
            }

            if (!assertFilePath.exists()) {
                System.err.println("HIBA: Elvárt fájl nem található: " + assertFile);
                return false;
            }

            Document outputDoc = dBuilder.parse(outputFilePath);
            Document assertDoc = dBuilder.parse(assertFilePath);

            outputDoc.getDocumentElement().normalize();
            assertDoc.getDocumentElement().normalize();

            /// /CONSOL OUT
            try {
                outputStream.write(("=== XML Fájl Összehasonlítás ===\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            boolean isEqual = compareElements(outputDoc.getDocumentElement(),
                    assertDoc.getDocumentElement(),
                    "");

            if (isEqual) {
                /// /CONSOL OUT
                try {
                    outputStream.write(("✓ A fájlok azonosak!\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                /// /CONSOL OUT
                try {
                    outputStream.write(("✗ A fájlok különböznek!\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return isEqual;

        } catch (Exception e) {
            System.err.println("HIBA történt az XML összehasonlítás során: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // --- SEGÉDFÜGGVÉNYEK ---

    /**
     * Szöveges azonosító alapján visszaadja a megfelelő mezőállapot (TileState) példányát.
     * @param type Az állapot String reprezentációja (pl. "Icy", "Blocked").
     * @return A megfelelő {@link TileState} példány. Alapértelmezetten {@link CleanTileState}.
     */
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

    /**
     * Szöveges azonosító alapján létrehoz és visszaad egy konkrét kotrófejet (Attachment).
     * @param type A kotrófej típusa.
     * @param name A kotrófej azonosítója/neve.
     * @return A létrehozott {@link Attachment} példány.
     */
    private Attachment createAttachment(String type, String name) {
        // JAVÍTVA: Ha nincs típus megadva, egy olyan névtelen osztályt adunk vissza, aminek üres a típusa.
        // Így kimentésnél nem fogja legenerálni a `type="SweeperHead"` attribútumot, ezáltal pontosan megegyezik a fájllal.
        if (type == null || type.isEmpty()) {
            return new SweeperHead(name) {
                @Override
                public String getType() {
                    return "";
                }
            };
        }

        switch (type) {
            case "SalterHead": return new SalterHead(name);
            case "BlowerHead": return new BlowerHead(name);
            case "IcebreakerHead": return new IcebreakerHead(name);
            case "CobblestoneHead": return new CobblestoneHead(name);
            case "DragonHead": return new DragonHead(name);
            case "SweeperHead": return new SweeperHead(name);
            default: return new SweeperHead(name);
        }
    }

    /**
     * Szöveges azonosító alapján létrehoz egy új fogyóeszközt (Consumable).
     * @param type A fogyóeszköz típusa (pl.: "Salt").
     * @param name A fogyóeszköz neve/azonosítója.
     * @param amount A rendelkezésre álló mennyiség.
     * @return A létrehozott {@link Consumable} objektum, vagy null.
     */
    private Consumable createConsumable(String type, String name, int amount) {
        if (type == null) return null;
        switch (type) {
            case "Salt": return new Salt(amount, name);
            case "Rubble": return new Rubble(amount, name);
            case "Biokerosene": return new Biokerosene(amount, name);
            default: return null;
        }
    }

    /**
     * Segédfüggvény egy XML elem numerikus (int) attribútumának biztonságos kiolvasására.
     * @param element Az XML elem.
     * @param attribute Az attribútum neve.
     * @param defaultValue Ha az attribútum nem létezik vagy nem szám, ezzel tér vissza.
     * @return A beolvasott szám, vagy a defaultValue.
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
     * Visszaadja a megadott XML elem első, meghatározott nevű gyermek elemét.
     * @param parent A szülő XML elem.
     * @param tagName A keresett gyermek elem neve (tag név).
     * @return Az első egyező gyermek {@link Element}, vagy null ha nem létezik.
     */
    private Element getSingleChildElement(Element parent, String tagName) {
        NodeList children = parent.getElementsByTagName(tagName);
        if (children.getLength() > 0) {
            return (Element) children.item(0);
        }
        return null;
    }

    /**
     * Visszaadja a kapott mezőállapot (TileState) XML szerializációhoz használt string nevét.
     * @param state A vizsgálandó {@link TileState} példány.
     * @return Az állapot string azonosítója (pl. "DeepSnowy").
     */
    private String getTileStateTypeName(TileState state) {
        if (state instanceof ShallowSnowyTileState) return "ShallowSnowy";
        if (state instanceof DeepSnowyTileState) return "DeepSnowy";
        if (state instanceof IcyTileState) return "Icy";
        if (state instanceof BlockedTileState) return "Blocked";
        return "Clean";
    }

    /**
     * Rekurzívan összehasonlít két XML elemet.
     * @param outputElement a kimeneti XML elem
     * @param assertElement az elvárt XML elem
     * @param path az aktuális elem útvonala a XML hierarchiában
     * @return true, ha az elemek azonosak, false, ha különböznek
     */
    private boolean compareElements(Element outputElement, Element assertElement, String path) {
        boolean isEqual = true;
        String currentPath = path.isEmpty() ? outputElement.getTagName() :
                path + " > " + outputElement.getTagName();

        // 1. Tag nevek összehasonlítása
        if (!outputElement.getTagName().equals(assertElement.getTagName())) {
            /// /CONSOL OUT
            try {
                outputStream.write(("✗ HIBA: Tag név különbözik a " + path + " szinten\n" + "  Kimenet: <" + outputElement.getTagName() + ">\n" + "  Elvárt:  <" + assertElement.getTagName() + ">\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return false;
        }

        // 2. Attribútumok összehasonlítása
        isEqual = compareAttributes(outputElement, assertElement, currentPath) && isEqual;

        // 3. Gyermek elemek összehasonlítása
        NodeList outputChildren = outputElement.getChildNodes();
        NodeList assertChildren = assertElement.getChildNodes();

        // Szűrjük ki a szöveges csomópontokat (whitespace)
        java.util.List<Element> outputElementChildren = new ArrayList<>();
        java.util.List<Element> assertElementChildren = new ArrayList<>();

        for (int i = 0; i < outputChildren.getLength(); i++) {
            if (outputChildren.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                outputElementChildren.add((Element) outputChildren.item(i));
            }
        }

        for (int i = 0; i < assertChildren.getLength(); i++) {
            if (assertChildren.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                assertElementChildren.add((Element) assertChildren.item(i));
            }
        }

        // Gyermek elemek száma
        if (outputElementChildren.size() != assertElementChildren.size()) {
            /// /CONSOL OUT
            try {
                outputStream.write(("✗ HIBA: " + currentPath + " - Gyermek elemek száma különbözik\n" + "  Kimenet: " + outputElementChildren.size() + " elem\n" + "  Elvárt:  " + assertElementChildren.size() + " elem\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isEqual = false;
        }

        // Gyermek elemek összehasonlítása
        int minChildren = Math.min(outputElementChildren.size(), assertElementChildren.size());
        for (int i = 0; i < minChildren; i++) {
            Element outputChild = outputElementChildren.get(i);
            Element assertChild = assertElementChildren.get(i);

            // Az aktuális gyermek tag nevét hozzáadjuk az útvonalhoz
            if (!compareElements(outputChild, assertChild, currentPath)) {
                isEqual = false;
            }
        }

        // Ha több elem van az egyik oldalon
        if (outputElementChildren.size() > assertElementChildren.size()) {
            for (int i = assertElementChildren.size(); i < outputElementChildren.size(); i++) {
                Element extra = outputElementChildren.get(i);
                /// /CONSOL OUT
                try {
                    outputStream.write(("✗ HIBA: " + currentPath + " - Extra elem a kimenetben: <" +
                            extra.getTagName() + ">\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isEqual = false;
            }
        } else if (assertElementChildren.size() > outputElementChildren.size()) {
            for (int i = outputElementChildren.size(); i < assertElementChildren.size(); i++) {
                Element missing = assertElementChildren.get(i);
                try {
                    outputStream.write(("✗ HIBA: " + currentPath + " - Hiányzó elem a kimenetből: <" +
                            missing.getTagName() + ">\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isEqual = false;
            }
        }

        return isEqual;
    }

    /**
     * Összehasonlít két elem attribútumait.
     * @param outputElement a kimeneti XML elem
     * @param assertElement az elvárt XML elem
     * @param path az aktuális elem útvonala
     * @return true, ha az attribútumok azonosak, false, ha különböznek
     */
    private boolean compareAttributes(Element outputElement, Element assertElement, String path) {
        boolean isEqual = true;
        String tagName = outputElement.getTagName();

        // Kimeneti attribútumok ellenőrzése
        org.w3c.dom.NamedNodeMap outputAttrs = outputElement.getAttributes();
        org.w3c.dom.NamedNodeMap assertAttrs = assertElement.getAttributes();

        // Az elvárt attribútumok száma
        int expectedAttrCount = assertAttrs.getLength();
        int actualAttrCount = outputAttrs.getLength();

        if (actualAttrCount != expectedAttrCount) {
            /// /CONSOL OUT
            try {
                outputStream.write(("✗ HIBA: <" + tagName + "> - Attribútumok száma különbözik\n" + "  Kimenet: " + actualAttrCount + " attribútum\n" + "  Elvárt:  " + expectedAttrCount + " attribútum\n").getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            isEqual = false;
        }

        // Minden elvárt attribútum ellenőrzése
        for (int i = 0; i < assertAttrs.getLength(); i++) {
            org.w3c.dom.Attr assertAttr = (org.w3c.dom.Attr) assertAttrs.item(i);
            String attrName = assertAttr.getName();
            String assertValue = assertAttr.getValue();

            String outputValue = outputElement.getAttribute(attrName);

            if (outputValue == null || outputValue.isEmpty()) {
                /// /CONSOL OUT
                try {
                    outputStream.write(("✗ HIBA: <" + tagName + "> [" + path + "]\n" +  "  Hiányzó attribútum: " + attrName + "\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isEqual = false;
            } else if (!outputValue.equals(assertValue)) {

                /// /CONSOL OUT
                try {
                    outputStream.write(("✗ HIBA: <" + tagName + "> attribútum: " + attrName + " [" + path + "]\n" + "  Kimenet: " + attrName + "=\"" + outputValue + "\"\n" + "  Elvárt:  " + attrName + "=\"" + assertValue + "\"\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isEqual = false;
            }
        }

        // Extra attribútumok ellenőrzése (ami csak a kimenetben van)
        for (int i = 0; i < outputAttrs.getLength(); i++) {
            org.w3c.dom.Attr outputAttr = (org.w3c.dom.Attr) outputAttrs.item(i);
            String attrName = outputAttr.getName();

            if (assertElement.getAttribute(attrName) == null ||
                    assertElement.getAttribute(attrName).isEmpty()) {
                /// /CONSOL OUT
                try {
                    outputStream.write(("✗ HIBA: <" + tagName + "> [" + path + "]\n" + "  Extra attribútum a kimenetben: " + attrName + "=\"" +
                            outputAttr.getValue() + "\"\n").getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                isEqual = false;
            }
        }

        return isEqual;
    }

    /**
     * Közös segédfüggvény a térkép (mezők és sávok) beolvasására és összekötésére.
     * @param mapNode Az XML <map> eleme
     * @param tileMap Egy Map, amit a függvény feltölt (ID -> Tile) a későbbi referenciákhoz (pl. járművekhez).
     * @return A betöltött Tile objektumok listája.
     */
    private List<Tile> parseMapData(Element mapNode, Map<String, Tile> tileMap) {
        Map<String, Lane> laneMap = new HashMap<>();
        Map<String, List<String>> tileNeighborsMap = new HashMap<>();
        List<Tile> allTiles = new ArrayList<>();

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

            // Több sáv kezelése
            List<Lane> tileLanes = new ArrayList<>();
            NodeList lanes = tileElement.getElementsByTagName("lane");
            for (int j = 0; j < lanes.getLength(); j++) {
                String laneId = ((Element) lanes.item(j)).getAttribute("id");
                laneMap.putIfAbsent(laneId, new Lane(new ArrayList<>(), laneId));
                Lane tileLane = laneMap.get(laneId);
                tileLanes.add(tileLane);
            }

            // Lista előkészítése a szomszédoknak
            List<Tile> neighbors = new ArrayList<>();

            Tile tile = new Tile(id, state, isSalted, isRubbled, compIdx, meltIdx, fadeIdx, neighbors, tileLanes);
            tileMap.put(id, tile);
            allTiles.add(tile);

            // Sávokhoz is hozzáadjuk a csempét
            for (Lane lane : tileLanes) {
                lane.getTiles().add(tile);
            }

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

        return allTiles;
    }
}
