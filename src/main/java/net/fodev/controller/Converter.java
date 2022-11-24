package net.fodev.controller;

import net.fodev.model.Fomap;
import net.fodev.model.FomapObject;
import net.fodev.model.FomapTile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Converter {

    public enum LogLevel {
        info,
        warn,
        error
    }

    private int logLevel = 0;
    private Map<Integer, Integer> genericProtoMapping;
    private Map<Integer, Integer> critterProtoMapping;

    public Converter() {
        genericProtoMapping = new HashMap<>();
        critterProtoMapping = new HashMap<>();
    }

    public void setLogLevel(String logLevel) {
        if ("warning".equalsIgnoreCase(logLevel) || "warn".equalsIgnoreCase(logLevel)) {
            this.logLevel = 1;
        } else if ("error".equalsIgnoreCase(logLevel)) {
            this.logLevel = 2;
        } else {
            this.logLevel = 0;
        }
    }

    public Map<Integer, Integer> getGenericProtoMapping() {
        return genericProtoMapping;
    }

    public void setGenericProtoMapping(Map<Integer, Integer> genericProtoMapping) {
        this.genericProtoMapping = genericProtoMapping;
    }

    public Map<Integer, Integer> getCritterProtoMapping() {
        return critterProtoMapping;
    }

    public void setCritterProtoMapping(Map<Integer, Integer> critterProtoMapping) {
        this.critterProtoMapping = critterProtoMapping;
    }

    public Map<Integer, Integer> mapFromFile(String fileName, String logFileName) throws IOException {
        Map<Integer, Integer> map = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        FileWriter logger = new FileWriter(logFileName, true);
        String line;
        int lineIndex = 0;
        String msg = "Processing conversion file.\n";
        logger.write(msg);
        System.out.print(msg);
        while ((line = br.readLine()) != null) {
            lineIndex++;
            String[] keyValue = line.split("\\s+");
            if (keyValue.length > 1) {
                try {
                    Integer key = Integer.parseInt(keyValue[0]);
                    Integer value = Integer.parseInt(keyValue[1]);
                    map.put(key, value);
                } catch (NumberFormatException e) {
                    if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] Line %d: (%s, %s) pair not numeric format, skipped line.\n", lineIndex, keyValue[0], keyValue[1]);
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            } else {
                if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                    String message = String.format("[Warning] Line %d: Insufficient parameters, skipped line.\n", lineIndex);
                    logger.write(message);
                    System.out.print(message);
                }
            }
        }
        System.out.println("Done.");
        logger.close();
        return map;
    }

    public void printMap() {
        genericProtoMapping.entrySet().stream().forEach((kv) -> {
            System.out.println(String.format("(%d, %d)", kv.getKey(), kv.getValue()));
        });
    }

    public void convertFile(String inputFileName, String outputFileName, String logFileName, String handleMissingProto, Integer missingProtoReplaceValue) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));
        String line;
        int lineIndex = 0;
        FileWriter outputter = new FileWriter(outputFileName);
        FileWriter logger = new FileWriter(logFileName, true);
        if (handleMissingProto == null) {
            handleMissingProto = "ignore";
        }
        String msg = String.format("Converting '%s' into '%s', handling missing proto id's as: '%s' with default value: '%d' if applicable.\n",
                inputFileName, outputFileName, handleMissingProto, missingProtoReplaceValue);
        logger.write(msg);
        System.out.print(msg);
        while ((line = br.readLine()) != null) {
            lineIndex++;
            String key = "ProtoId";
            String spacing = "              ";
            if (line.length() > key.length() && line.substring(0, key.length()).equalsIgnoreCase(key)) {
                String[] keyValue = line.split("\\s+");
                if (keyValue.length > 1) {
                    try {
                        Integer value = Integer.parseInt(keyValue[1]);
                        Integer converted = genericProtoMapping.get(value);
                        if (converted != null) {
                            outputter.write(String.format("%s%s%s\n", keyValue[0], spacing, converted));
                            if (logLevel <= Converter.LogLevel.info.ordinal()) {
                                String message = String.format("[Info] Line %d: %s converted: %s -> %d\n",
                                        lineIndex, keyValue[0], keyValue[1], converted);
                                logger.write(message);
                                System.out.print(message);
                            }
                        } else {
                            if ("remove".equalsIgnoreCase(handleMissingProto)) {
                                //  TODO remove whole object from map file, not just proto id line
                                if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                                    String message = String.format("[Warning] Line %d: ProtoId %s not found in conversion map, removing line unchanged.\n",
                                            lineIndex, keyValue[1]);
                                    logger.write(message);
                                    System.out.print(message);
                                }
                            } else if ("replace".equalsIgnoreCase(handleMissingProto)) {
                                outputter.write(String.format("%s%s%s\n", keyValue[0], spacing, converted));
                                if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                                    String message = String.format("[Warning] Line %d: ProtoId %s not found in conversion map, replacing value with %d.\n",
                                            lineIndex, keyValue[1], missingProtoReplaceValue);
                                    logger.write(message);
                                    System.out.print(message);
                                }
                            } else {
                                outputter.write(line + "\n");
                                if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                                    String message = String.format("[Warning] Line %d: ProtoId %s not found in conversion map, leaving line unchanged.\n",
                                            lineIndex, keyValue[1]);
                                    logger.write(message);
                                    System.out.print(message);
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        outputter.write(line + "\n");
                        if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Line %d: Second parameter (%s, %s) not numeric format, leaving line unchanged.\n",
                                    lineIndex, keyValue[0], keyValue[1]);
                            logger.write(message);
                            System.out.print(message);
                        }
                    }
                } else {
                    outputter.write(line + "\n");
                    if (logLevel <= Converter.LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] Line %d: Insufficient parameters, leaving line unchanged.\n", lineIndex);
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            } else {
                //  ignore line, not relevant
                outputter.write(line + "\n");
            }
        }
        System.out.println("Done.");
        outputter.close();
        logger.close();
    }

    public void convertFomap(Fomap fomap, String outputFile, String logFile, String handleMissingProto, Integer missingProtoReplaceValue) throws IOException {
        FileWriter outputter = new FileWriter(outputFile);
        FileWriter logger = new FileWriter(logFile, true);
        String msg = String.format("Converting fomap to '%s', handling missing proto id's as: '%s' with default value: '%d' if applicable.\n",
                outputFile, handleMissingProto, missingProtoReplaceValue);
        logger.write(msg);
        System.out.print(msg);

        //  header
        outputter.write(fomap.getHeader() + "\n");
        if (genericProtoMapping.size() == 0) {
            if (logLevel <= ItemProtoParser.LogLevel.warn.ordinal()) {
                String message = "[Warning] Generic Proto Map contains no elements to help conversion.\n";
                logger.write(message);
                System.out.print(message);
            }
        }

        if (critterProtoMapping.size() == 0) {
            if (logLevel <= ItemProtoParser.LogLevel.warn.ordinal()) {
                String message = "[Warning] Critter Proto Map contains no elements to help conversion.\n";
                logger.write(message);
                System.out.print(message);
            }
        }

        //  tiles
        outputter.write("[Tiles]\n");
        for (FomapTile tile : fomap.getTiles()) {
            outputter.write(tile.toString() + "\n");
        }
        outputter.write("\n");

        //  objects
        outputter.write("[Objects]\n");
        for (FomapObject object : fomap.getObjects()) {
            Integer key = object.getProtoId();
            if (object.getType() != 0) {
                if (genericProtoMapping.containsKey(key)) {
                    object.setProtoId(genericProtoMapping.get(key));
                    outputter.write(object.toString() + "\n");
                } else {
                    if ("remove".equalsIgnoreCase(handleMissingProto)) {
                        //outputter.write(object.toString() + "\n");
                        //  do nothing
                    } else if ("replace".equalsIgnoreCase(handleMissingProto)) {
                        //  todo
                    } else {
                        outputter.write(object.toString() + "\n");
                    }
                }
            } else {
                if (critterProtoMapping.containsKey(key)) {
                    object.setProtoId(critterProtoMapping.get(key));
                    outputter.write(object.toString() + "\n");
                } else {
                    if ("remove".equalsIgnoreCase(handleMissingProto)) {
                        //outputter.write(object.toString() + "\n");
                        //  do nothing
                    } else if ("replace".equalsIgnoreCase(handleMissingProto)) {
                        //  todo
                    } else {
                        outputter.write(object.toString() + "\n");
                    }
                }
            }
        }
        outputter.write("\n");

        logger.close();
        outputter.close();
    }
}
