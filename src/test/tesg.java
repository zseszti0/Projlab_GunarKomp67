package test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tesg {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Gather test source folders and ensure each has a "save" line before "exit"
        List<String> testFolders = gatherTestSourceFolders();
        for (String folderStr : testFolders) {
            Path folder = Paths.get(folderStr);
            try {
                ensureSaveBeforeExit(folder);
            } catch (IOException e) {
                System.out.println("Failed to process folder " + folder + ": " + e.getMessage());
            }
        }
    }

    private static List<String> gatherTestSourceFolders(){
        List<String> result = new ArrayList<>();
        Path testsRoot = Paths.get("tests");
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

    /**
     * Ensure that act.txt has a "save" line immediately before the "exit" line.
     * If "exit" exists and "save" is not on the line immediately before it, insert "save".
     */
    private static void ensureSaveBeforeExit(Path folder) throws IOException {
        Path actTxt = folder.resolve("act").resolve("act.txt");
        if (!Files.exists(actTxt) || !Files.isRegularFile(actTxt)) {
            // nothing to do
            return;
        }

        List<String> lines = Files.readAllLines(actTxt, StandardCharsets.UTF_8);
        if (lines.isEmpty()) return;

        // Find the "exit" line (case-insensitive, trimmed)
        int exitLineIndex = -1;
        for (int i = lines.size() - 1; i >= 0; i--) {
            String trimmed = lines.get(i).trim().toLowerCase();
            if ("exit".equals(trimmed)) {
                exitLineIndex = i;
                break;
            }
        }

        if (exitLineIndex < 0) {
            // no exit line found, nothing to do
            return;
        }

        // Check if the line immediately before "exit" is "save"
        if (exitLineIndex > 0) {
            String prevTrimmed = lines.get(exitLineIndex - 1).trim().toLowerCase();
            if ("save".equals(prevTrimmed)) {
                // "save" is already there
                return;
            }
        }

        // Insert "save" immediately before the "exit" line
        lines.add(exitLineIndex, "save");
        Files.write(actTxt, lines, StandardCharsets.UTF_8);
        System.out.println("Inserted 'save' before 'exit' in " + actTxt);
    }
}
