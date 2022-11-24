package net.fodev;

import net.fodev.controller.Converter;
import net.fodev.controller.CritterProtoParser;
import net.fodev.controller.FomapParser;
import net.fodev.controller.ItemProtoParser;
import net.fodev.model.CritterProto;
import net.fodev.model.Fomap;
import net.fodev.model.ItemProto;
import net.fodev.model.ItemProtoMapping;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("FOClassic Map Converter");

        ArgumentParser argumentParser = ArgumentParsers.newFor("foclassic-map-converter").build()
                .defaultHelp(true)
                .description("Map converter between FOnline versions.");
        //  create proto id mappings file
        argumentParser.addArgument("-ps", "--protoSource")
                .help("Specify first proto id file for conversion. (The file you convert proto ID's from.)");
        argumentParser.addArgument("-pms", "--protoMultipleSources")
                .nargs("*")
                .help("Specify multiple proto id files for conversion. (The files you convert proto ID's from.)");
        argumentParser.addArgument("-pt", "--protoTarget")
                .help("Specify second proto id file for conversion. (The file you convert proto ID's to.)");
        argumentParser.addArgument("-pmt", "--protoMultipleTargets")
                .nargs("*")
                .help("Specify multiple proto id files for conversion. (The files you convert proto ID's to.)");
        argumentParser.addArgument("-gipm", "--generateItemProtoMappingFile")
                .help("Specify ");
        argumentParser.addArgument("-gcpm", "--generateCritterProtoMappingFile")
                .help("Specify ");

        //  use proto id mappings file to convert maps between different fonline versions
        argumentParser.addArgument("-ipm", "--itemProtoMapping")
                .help("Specify proto ID mapping file to use to convert map file.");
        argumentParser.addArgument("-cpm", "--critterProtoMapping")
                .help("Specify proto ID mapping file to use to convert map file.");
        argumentParser.addArgument("-ms", "--mapSourceFile")
                .help("Specify fomap file to convert.");
        argumentParser.addArgument("-mt", "--mapTargetFile")
                .help("Specify fomap file name as conversion result.");
        argumentParser.addArgument("-mph", "--missingProtoHandling")
                .help("Specify fomap file name as conversion result.");
        argumentParser.addArgument("-rv", "--replaceValue")
                .help("Specify fomap file name as conversion result.");

        //  logging related
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

        String logFolder = ns.getString("logPath");
        String logLevel = ns.getString("logLevel");
        if (logLevel == null) {
            logFolder = "warning";
        }
        String logParseFile = "lastParse.log";
        String logConversionFile = "lastConversion.log";
        if (logFolder != null) {
            logParseFile = logFolder + "/" + logParseFile;
            logConversionFile = logFolder + "/" + logConversionFile;
        }

        String mappingFileFrom = ns.getString("protoSource");
        String mappingFileTo = ns.getString("protoTarget");
        List<String> mappingFilesFrom = ns.getList("protoMultipleSources");
        List<String> mappingFilesTo = ns.getList("protoMultipleTargets");
        String generateItemProtoMappingFile = ns.getString("generateItemProtoMappingFile");
        String generateCritterProtoMappingFile = ns.getString("generateCritterProtoMappingFile");
        String itemProtoMappingFile = ns.getString("itemProtoMapping");
        String critterProtoMappingFile = ns.getString("critterProtoMapping");
        String mapSourceFile = ns.getString("mapSourceFile");
        String mapTargetFile = ns.getString("mapTargetFile");
        String missingProtoHandling = ns.getString("missingProtoHandling");
        if (missingProtoHandling == null) {
            missingProtoHandling = "remove";
        }
        Integer replaceValue = 0;
        try {
            replaceValue = Integer.parseInt(ns.getString("replaceValue"));
        } catch (NumberFormatException e) {
            replaceValue = -1;
        }

        if (generateItemProtoMappingFile != null) {
            //  generate proto mapping file from single source
            if (mappingFileFrom != null && mappingFileTo != null) {
                generateItemProtoMappingFromSingleSource(logLevel, logParseFile, mappingFileFrom, mappingFileTo, generateItemProtoMappingFile);
                //  generate proto mapping file from multiple sources
            } else if (mappingFilesFrom != null && mappingFilesTo != null) {
                generateItemProtoMappingFromMultipleSources(logLevel, logParseFile, mappingFilesFrom, mappingFilesTo, generateItemProtoMappingFile);
            } else {
                String message = String.format("[Error] Cannot generate item mapping file without two proto ID files.\nHint: try parameters like '-gmf filename -p1 filename1 -p2 filename2'.");
                System.out.print(message);
                ItemProtoParser.logLine(message, logParseFile);
            }
        }

        if (generateCritterProtoMappingFile != null) {
            //  generate proto mapping file from multiple source
            if (mappingFilesFrom != null && mappingFilesTo != null) {
                generateCritterProtoMappingFromMultipleSources(logLevel, logParseFile, mappingFilesFrom, mappingFilesTo, generateCritterProtoMappingFile);
            } else {
                String message = String.format("[Error] Cannot generate critter mapping file without specifying (multiple) source and target proto ID files.\nHint: try parameters like '-gmf filename -p1 filename1 -p2 filename2'.");
                System.out.print(message);
                ItemProtoParser.logLine(message, logParseFile);
            }
        }


        Converter converter = new Converter();
        if (logLevel != null) {
            converter.setLogLevel(logLevel);
        }
        if (mapSourceFile != null || mapTargetFile != null) {
            if (mapSourceFile != null && mapTargetFile != null) {
                if (itemProtoMappingFile != null && critterProtoMappingFile != null) {
                    //  convert map
                    convertMap(logLevel, logConversionFile, itemProtoMappingFile, critterProtoMappingFile, mapSourceFile, mapTargetFile, missingProtoHandling, replaceValue, converter);
                } else {
                    String message = String.format("[Error] Missing item or critter proto mapping file for conversion.\n");
                    System.out.print(message);
                    ItemProtoParser.logLine(message, logConversionFile);
                }
            } else {
                String message = String.format("[Error] Both map source and target params must be given for conversion.\n");
                System.out.print(message);
                ItemProtoParser.logLine(message, logConversionFile);
            }
        }
    }

    private static void convertMap(String logLevel, String logConversionFile, String itemProtoMappingFile, String critterProtoMappingFile, String mapSourceFile, String mapTargetFile, String missingProtoHandling, Integer replaceValue, Converter converter) {
        try {
            Files.deleteIfExists(Paths.get(logConversionFile));
            Files.deleteIfExists(Paths.get(mapTargetFile));

            Map<Integer, Integer> itemProtoMapping = converter.mapFromFile(itemProtoMappingFile, logConversionFile);
            converter.setGenericProtoMapping(itemProtoMapping);
            Map<Integer, Integer> critterProtoMapping = converter.mapFromFile(critterProtoMappingFile, logConversionFile);
            converter.setCritterProtoMapping(critterProtoMapping);

            FomapParser fomapParser = new FomapParser();
            fomapParser.setLogLevel(logLevel);
            Fomap fomap = fomapParser.parseFromFile(mapSourceFile, logConversionFile);
            converter.convertFomap(fomap, mapTargetFile, logConversionFile, missingProtoHandling, replaceValue);
        } catch (IOException e) {
            String message = String.format("[Error] %s\n", e.getMessage());
            System.out.print(message);
            ItemProtoParser.logLine(message, logConversionFile);
            e.printStackTrace();
        }
    }

    private static void generateItemProtoMappingFromSingleSource(String logLevel, String logParseFile, String mappingFileFrom, String mappingFileTo, String generateMappingFile) {
        try {
            Files.deleteIfExists(Paths.get(logParseFile));
            Files.deleteIfExists(Paths.get(generateMappingFile));
            String message = String.format("Generating mapping file %s, mapping from %s to %s.", generateMappingFile, mappingFileFrom, mappingFileTo);
            System.out.println(message);
            ItemProtoParser.logLine(message + "\n", logParseFile);
            ItemProtoParser itemProtoParser = new ItemProtoParser();
            itemProtoParser.setLogLevel(logLevel);
            List<ItemProto> source = itemProtoParser.parseFromFile(mappingFileFrom, logParseFile);
            List<ItemProto> target = itemProtoParser.parseFromFile(mappingFileTo, logParseFile);
            List<ItemProtoMapping> mapping = itemProtoParser.compareProtosVerbose(source, target, logParseFile);
            itemProtoParser.generateMappingVerboseToFile(mapping, generateMappingFile, logParseFile);
        } catch (IOException e) {
            String message = String.format("[Error] %s\n", e.getMessage());
            System.out.print(message);
            ItemProtoParser.logLine(message, logParseFile);
            e.printStackTrace();
        }
    }

    private static void generateItemProtoMappingFromMultipleSources(String logLevel, String logParseFile, List<String> mappingFilesFrom, List<String> mappingFilesTo, String generateMappingFile) {
        try {
            Files.deleteIfExists(Paths.get(logParseFile));
            Files.deleteIfExists(Paths.get(generateMappingFile));
            String message = String.format("Generating item proto mapping file %s using multiple source and target files\nSource: %s\nTarget: %s",
                    generateMappingFile, Arrays.toString(mappingFilesFrom.toArray()), Arrays.toString(mappingFilesTo.toArray()));
            System.out.println(message);
            ItemProtoParser.logLine(message + "\n", logParseFile);
            ItemProtoParser itemProtoParser = new ItemProtoParser();
            itemProtoParser.setLogLevel(logLevel);
            List<ItemProto> source = itemProtoParser.parseFromMultipleFiles(mappingFilesFrom, logParseFile);
            List<ItemProto> target = itemProtoParser.parseFromMultipleFiles(mappingFilesTo, logParseFile);
            List<ItemProtoMapping> mapping = itemProtoParser.compareProtosVerbose(source, target, logParseFile);
            itemProtoParser.generateMappingVerboseToFile(mapping, generateMappingFile, logParseFile);
        } catch (IOException e) {
            String message = String.format("[Error] %s", e.getMessage());
            System.out.println(message);
            ItemProtoParser.logLine(message + "\n", logParseFile);
            e.printStackTrace();
        }
    }

    private static void generateCritterProtoMappingFromMultipleSources(String logLevel, String logParseFile, List<String> mappingFilesFrom, List<String> mappingFilesTo, String generateMappingFile) {
        try {
            Files.deleteIfExists(Paths.get(logParseFile));
            Files.deleteIfExists(Paths.get(generateMappingFile));
            String message = String.format("Generating critter proto mapping file %s using multiple source and target files\nSource: %s\nTarget: %s",
                    generateMappingFile, Arrays.toString(mappingFilesFrom.toArray()), Arrays.toString(mappingFilesTo.toArray()));
            System.out.println(message);
            CritterProtoParser.logLine(message + "\n", logParseFile);
            CritterProtoParser critterProtoParser = new CritterProtoParser();
            critterProtoParser.setLogLevel(logLevel);
            List<CritterProto> source = critterProtoParser.parseFromMultipleFiles(mappingFilesFrom, logParseFile);
            List<CritterProto> target = critterProtoParser.parseFromMultipleFiles(mappingFilesTo, logParseFile);
            Map<Integer, Integer> critterProtoMapping = critterProtoParser.generateProtoMapping(source, target, logParseFile);
            critterProtoParser.printMappingToFile(critterProtoMapping, generateMappingFile);
        } catch (IOException e) {
            String message = String.format("[Error] %s", e.getMessage());
            System.out.println(message);
            ItemProtoParser.logLine(message + "\n", logParseFile);
            e.printStackTrace();
        }
    }
}
