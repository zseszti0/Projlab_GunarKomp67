package test;

import control.ProtoController;
import control.XMLParser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestOracle {
    public static void main(String[] args) {
        ProtoController.main(args);
        XMLParser parser = new XMLParser(args[0]);

        String assertPath = args[0] + "/assert/assert.xml";
        String outputPath = args[0] + "/output/output.xml";
        String massageOutputPath = args[0] + "/output/output.txt";

        FileInputStream inputStream = null;
        try {
            Path filePath = Path.of(massageOutputPath);
            String actContent = Files.readString(filePath);

            parser.assertXMLFile(outputPath, assertPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
