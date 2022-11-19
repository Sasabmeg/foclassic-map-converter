package net.fodev;

import net.fodev.controller.Converter;
import net.fodev.controller.ProtoParser;
import net.fodev.model.Proto;
import net.fodev.model.ProtoMapping;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("FOClassic Map Converter");

        ArgumentParser argumentParser = ArgumentParsers.newFor("foclassic-map-converter").build()
                .defaultHelp(true)
                .description("Map converter between FOnline versions.");
        //  create proto id mappings file
        argumentParser.addArgument("-p1", "--proto1")
                .help("Specify first proto id file for conversion. (The file you convert proto ID's from.)");
        argumentParser.addArgument("-ps1", "--protoFiles1")
                .nargs("*")
                .help("Specify multiple proto id files for conversion. (The files you convert proto ID's from.)");
        argumentParser.addArgument("-p2", "--proto2")
                .help("Specify second proto id file for conversion. (The file you convert proto ID's to.)");
        argumentParser.addArgument("-ps2", "--protoFiles2")
                .nargs("*")
                .help("Specify multiple proto id files for conversion. (The files you convert proto ID's to.)");
        argumentParser.addArgument("-gmf", "--generateMappingFile")
                .help("Specify ");

        //  use proto id mappings file to convert maps between different fonline versions
        argumentParser.addArgument("-pm", "--protoMapping")
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
        String logParseFile = "lastParse.log";
        String logConversionFile = "lastConversion.log";
        if (logFolder != null) {
            logParseFile = logFolder + "/" + logParseFile;
            logConversionFile = logFolder + "/" + logConversionFile;
        }

        String mappingFileFrom = ns.getString("proto1");
        String mappingFileTo = ns.getString("proto2");
        List<String> mappingFilesFrom = ns.getList("protoFiles1");
        List<String> mappingFilesTo = ns.getList("protoFiles2");
        String generateMappingFile = ns.getString("generateMappingFile");
        String protoMappingFile = ns.getString("protoMapping");
        String mapSourceFile = ns.getString("mapSourceFile");
        String mapTargetFile = ns.getString("mapTargetFile");
        String missingProtoHandling = ns.getString("missingProtoHandling");
        Integer replaceValue = 0;
        try {
            replaceValue = Integer.parseInt(ns.getString("replaceValue"));
        } catch (NumberFormatException e) {
            replaceValue = -1;
        }

        if (generateMappingFile != null) {
            if (mappingFileFrom != null && mappingFileTo != null) {
                try {
                    Files.deleteIfExists(Paths.get(logParseFile));
                    Files.deleteIfExists(Paths.get(generateMappingFile));
                    String message = String.format("Generating mapping file %s, mapping from %s to %s.", generateMappingFile, mappingFileFrom, mappingFileTo);
                    System.out.println(message);
                    ProtoParser.logLine(message + "\n", logParseFile);
                    ProtoParser protoParser = new ProtoParser();
                    protoParser.setLogLevel(logLevel);
                    List<Proto> source = protoParser.parseFromFile(mappingFileFrom, logParseFile);
                    List<Proto> target = protoParser.parseFromFile(mappingFileTo, logParseFile);
                    List<ProtoMapping> mapping = protoParser.compareProtosVerbose(source, target, logParseFile);
                    protoParser.generateMappingVerbose(mapping, generateMappingFile, logParseFile);
                } catch (IOException e) {
                    String message = String.format("[Error] %s\n", e.getMessage());
                    System.out.print(message);
                    ProtoParser.logLine(message, logParseFile);
                    e.printStackTrace();
                }
            } else if (mappingFilesFrom != null && mappingFilesTo != null) {
                try {
                    Files.deleteIfExists(Paths.get(logParseFile));
                    Files.deleteIfExists(Paths.get(generateMappingFile));
                    String message = String.format("Generating mapping file %s using from multiple source and target files\nSource: %s\nTarget: %s",
                            generateMappingFile, Arrays.toString(mappingFilesFrom.toArray()), Arrays.toString(mappingFilesTo.toArray()));
                    System.out.println(message);
                    ProtoParser.logLine(message + "\n", logParseFile);
                    ProtoParser protoParser = new ProtoParser();
                    protoParser.setLogLevel(logLevel);
                    List<Proto> source = protoParser.parseFromMultipleFiles(mappingFilesFrom, logParseFile);
                    List<Proto> target = protoParser.parseFromMultipleFiles(mappingFilesTo, logParseFile);
                    List<ProtoMapping> mapping = protoParser.compareProtosVerbose(source, target, logParseFile);
                    protoParser.generateMappingVerbose(mapping, generateMappingFile, logParseFile);
                } catch (IOException e) {
                    String message = String.format("[Error] %s", e.getMessage());
                    System.out.println(message);
                    ProtoParser.logLine(message + "\n", logParseFile);
                    e.printStackTrace();
                }
            } else {
                String message = String.format("[Error] Cannot generate mapping file without two proto ID files.\nHint: try parameters like '-gmf filename -p1 filename1 -p2 filename2'.");
                System.out.print(message);
                ProtoParser.logLine(message, logParseFile);
            }
        }

        Converter converter = new Converter();
        if (logLevel != null) {
            converter.setLogLevel(logLevel);
        }
        if (protoMappingFile != null) {
            if (mapSourceFile != null && mapTargetFile != null) {
                try {
                    Files.deleteIfExists(Paths.get(logConversionFile));
                    Files.deleteIfExists(Paths.get(mapTargetFile));
                    converter.mapFromFile(protoMappingFile, logConversionFile);
                    converter.convertFile(mapSourceFile, mapTargetFile, logConversionFile, missingProtoHandling, replaceValue);
                } catch (IOException e) {
                    String message = String.format("[Error] %s\n", e.getMessage());
                    System.out.print(message);
                    ProtoParser.logLine(message, logConversionFile);
                    e.printStackTrace();
                }
            } else {
                String message = String.format("[Error] Cannot convert map file with missing in/out parameters. Switch '--protoMapping' must be used with valid '--mapSourceFile' and '--mapTargetFile'.\n");
                System.out.print(message);
                ProtoParser.logLine(message, logConversionFile);
            }
        }

    }
}
