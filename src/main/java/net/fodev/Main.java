package net.fodev;

import net.fodev.controller.Converter;
import net.fodev.controller.Parser;
import net.fodev.model.Proto;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("FOClassic Map Converter");

        ArgumentParser argumentParser = ArgumentParsers.newFor("foclassic-map-converter").build()
                .defaultHelp(true)
                .description("Map converter between FOnline versions.");
        argumentParser.addArgument("-p1", "--proto1")
                .help("Specify first proto id mapping file for conversion. (The file you convert proto ID's from.)");
        argumentParser.addArgument("-p2", "--proto2")
                .help("Specify second proto id mapping file for conversion. (The file you convert proto ID's to.)");
        argumentParser.addArgument("-gmf", "--generateMappingFile")
                .help("Specify ");
        argumentParser.addArgument("-pm", "--protoMapping")
                .help("Specify proto ID mapping file to use to convert map file.");

        argumentParser.addArgument("-lp", "--logPath")
                .help("Specify a path (folder/directory) for log files.");
        argumentParser.addArgument("-ll", "--logLevel")
                .choices("info", "warning", "error").setDefault("info")
                .help("Specify the level of logging, it can be ");

        Namespace ns = null;
        try {
            ns = argumentParser.parseArgs(args);
        } catch (ArgumentParserException e) {
            argumentParser.handleError(e);
            System.exit(1);
        }

        String mappingFileFrom = ns.getString("proto1");
        String mappingFileTo = ns.getString("proto2");
        String generateMappingFile = ns.getString("generateMappingFile");
        String logFolder = ns.getString("logPath");
        String logLevel = ns.getString("logLevel");
        String logFile = "lastParse.log";
        if (logFolder != null) {
            logFile = logFolder + "/" + logFile;
        }
        if (generateMappingFile != null) {
            if (mappingFileFrom != null && mappingFileTo != null) {
                try {
                    Files.deleteIfExists(Paths.get(logFile));
                    Files.deleteIfExists(Paths.get(generateMappingFile));
                    String message = String.format("Generating mapping file %s, mapping from %s to %s.", generateMappingFile, mappingFileFrom, mappingFileTo);
                    System.out.println(message);
                    Parser.logLine(message + "\n", logFile);
                    Parser parser = new Parser();
                    parser.setLogLevel(logLevel);
                    List<Proto> source = parser.parseFromFile(mappingFileFrom, logFile);
                    List<Proto> target = parser.parseFromFile(mappingFileTo, logFile);
                    Map<Integer, Integer> mapping = parser.compareProtos(source, target, logFile);
                    parser.generateMapping(mapping, generateMappingFile, logFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println(String.format("Cannot generate mapping file without two proto ID files.\nHint: try parameters like '-gmf filename -p1 filename1 -p2 filename2'."));
            }
        }
    }
}
