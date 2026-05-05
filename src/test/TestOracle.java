package test;

import control.ProtoController;
import control.XMLParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.util.List;
import java.util.ArrayList;

/**
 * A tesztelés automatizálásáért és a tesztesetek kötegelt futtatásáért felelős osztály.
 * Feladata a tesztkönyvtárak felderítése, a tesztek végrehajtása a ProtoController segítségével,
 * valamint a kimenetek és az elvárt eredmények (assert fájlok) automatikus összehasonlítása.
 */
public class TestOracle {
    /**
     * A program belépési pontja a teszteléshez.
     * Lépései:
     * 1. Ellenőrzi a paramétereket. Ha nem kapott konkrét teszt mappát (args.length == 0), felderíti a megadott gyökérmappában lévő összes tesztkönyvtárat.
     * 2. Végigmegy a megtalált mappákon és egységesíti az elérési utak elválasztójeleit.
     * 3. Sorban lefuttatja a teszteket a runTest metódussal, és kiírja a konzolra az aktuális futás állapotát.
     * 4. Minden teszt eredményét (OK/FAIL) rögzíti, a sikereseket megszámolja, és a végén egy output.txt fájlba írja az összesített statisztikát.
     * 5. Ha kapott parancssori argumentumot, akkor csak azt az egy specifikus tesztet futtatja le, ami az argumentumban szerepel.
     *
     * @param args A parancssori argumentumok. Ha üres, minden teszt lefut. Ha tartalmaz elemet, az első elem a futtatandó teszt mappája.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            List<String> folders = gatherTestSourceFolders();

            for(int i = 0; i < folders.size(); i++){
                folders.set(i, folders.get(i).replace("\\", "/"));
            }

            String results = "";
            int successCount = 0;
            for (String f : folders) {
                System.out.println("Running test: " + f);
                String[] newArgs = {f};
                boolean succsess = runTest(newArgs);
                results += f + " " + (succsess ? "OK" : "FAIL") + "\n";
                if(succsess) successCount++;
                System.out.println("Test finished.");
            }

            results += "\n\n" + successCount + " test(s) passed.";
            try {
                Files.write(Paths.get("output.txt"), results.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        runTest(args);
    }

    /**
     * Lefuttat egy adott tesztesetet és kiértékeli annak eredményét.
     * Lépései:
     * 1. Meghívja a ProtoController main metódusát a kapott argumentumokkal, amely elvégzi a teszt lépéseit és legenerálja az output.xml fájlt.
     * 2. Példányosít egy XMLParser-t a tesztkönyvtár elérési útjával.
     * 3. Összehasonlítja a generált output.xml fájlt az elvárt assert/assert.xml fájllal az assertXMLFile metódus segítségével.
     *
     * @param args A futtatáshoz szükséges paraméterek (args[0] tartalmazza a teszt könyvtárát).
     * @return true, ha a teszt sikeres (a generált kimenet maradéktalanul megegyezik az elvárttal), egyébként false.
     */
    private static boolean runTest(String[] args){
        ProtoController.main(args);
        XMLParser parser = new XMLParser(args[0]);

        String assertPath = args[0] + "/assert/assert.xml";
        String outputPath = args[0] + "/output.xml";

        return parser.assertXMLFile(outputPath, assertPath);
    }

    /**
     * Felderíti és összegyűjti a teszteseteket tartalmazó könyvtárakat a fájlrendszerben.
     * Lépései:
     * 1. Megnyitja a megadott gyökérkönyvtárat (jelen esetben keménykódolva a "tests/cleaner/clean" mappa).
     * 2. Rekurzívan bejárja az alatta lévő összes alkönyvtárat.
     * 3. Megvizsgálja a mappák tartalmát: ha egy mappa tartalmaz legalább egy 'act', 'arrange' vagy 'assert' nevű alkönyvtárat, akkor azt érvényes tesztkönyvtárnak tekinti.
     * 4. A talált tesztkönyvtárak elérési útját hozzáadja a visszatérési listához.
     *
     * @return A felfedezett tesztkönyvtárak (könyvtárstruktúrák) elérési útjainak listája.
     */
    private static List<String> gatherTestSourceFolders(){
        List<String> result = new ArrayList<>();
        Path testsRoot = Paths.get("tests/");
        if (!Files.exists(testsRoot) || !Files.isDirectory(testsRoot)) {
            return result;
        }

        try {
            try (java.util.stream.Stream<Path> stream = Files.walk(testsRoot)) {
                stream.filter(Files::isDirectory).forEach(dir -> {
                    try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
                        for (Path child : ds) {
                            if (Files.isDirectory(child)) {
                                String name = child.getFileName().toString().toLowerCase();
                                if ("act".equals(name) || "arrange".equals(name) || "assert".equals(name)) {
                                    result.add(dir.toString());
                                    break;
                                }
                            }
                        }
                    } catch (IOException ignored) {
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan tests directory", e);
        }

        return result;
    }
}
