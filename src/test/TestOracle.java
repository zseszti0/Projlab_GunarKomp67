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

public class TestOracle {
    public static void main(String[] args) {
        if (args.length == 0) {
            // No specific test folder provided: print discovered test folders and exit.
            List<String> folders = gatherTestSourceFolders();

            //switch every "\" to "/"
            for(int i = 0; i < folders.size(); i++){
                folders.set(i, folders.get(i).replace("\\", "/"));
            }

            String results = "";
            int successCount = 0;
            for (String f : folders) {
                String[] newArgs = {f};
                boolean succsess = runTest(newArgs);
                results += f + " " + (succsess ? "OK" : "FAIL") + "\n";
                if(succsess) successCount++;
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
    private static boolean runTest(String[] args){
        ProtoController.main(args);
        XMLParser parser = new XMLParser(args[0]);

        String assertPath = args[0] + "/assert/assert.xml";
        String outputPath = args[0] + "/output.xml";

        return parser.assertXMLFile(outputPath, assertPath);
    }

    private static List<String> gatherTestSourceFolders(){
        List<String> result = new ArrayList<>();
        Path testsRoot = Paths.get("tests/cleaner/clean");
        if (!Files.exists(testsRoot) || !Files.isDirectory(testsRoot)) {
            return result; // empty list if no tests directory
        }

        try {
            // Walk every directory under testsRoot and find directories that contain
            // an immediate child named 'act', 'arrange' or 'assert'
            try (java.util.stream.Stream<Path> stream = Files.walk(testsRoot)) {
                stream.filter(Files::isDirectory).forEach(dir -> {
                    try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
                        for (Path child : ds) {
                            // Only consider directories
                            if (Files.isDirectory(child)) {
                                String name = child.getFileName().toString().toLowerCase();
                                if ("act".equals(name) || "arrange".equals(name) || "assert".equals(name)) {
                                    result.add(dir.toString());
                                    break;
                                }
                            }
                        }
                    } catch (IOException ignored) {
                        // ignore directories we can't read and continue
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan tests directory", e);
        }

        return result;
    }
}
