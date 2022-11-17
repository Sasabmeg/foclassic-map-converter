package net.fodev.controller;

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

    public void setLogLevel(String logLevel) {
        if ("warning".equalsIgnoreCase(logLevel) || "warn".equalsIgnoreCase(logLevel)) {
            this.logLevel = 1;
        } else if ("error".equalsIgnoreCase(logLevel)) {
            this.logLevel = 2;
        } else {
            this.logLevel = 0;
        }
    }

    Map<Integer, Integer> map;

    public Converter() {
        map = new HashMap<>();
    }

    public void mapFromFile(String fileName, String logFileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        FileWriter logger = new FileWriter(logFileName, true);
        String line;
        int lineIndex = 0;
        logger.write("Processing conversion file.\n");
        System.out.print("Processing conversion file.");
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
        System.out.println("done.");
        logger.close();

    }

    public void printMap() {
        map.entrySet().stream().forEach((kv) -> {
            System.out.println(String.format("(%d, %d)", kv.getKey(), kv.getValue()));
        });
    }

    public void convertFile(String inputFileName, String outputFileName, String logFileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(inputFileName));
        String line;
        int lineIndex = 0;
        FileWriter outputter = new FileWriter(outputFileName);
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Converting '%s' into '%s'.\n", inputFileName, outputFileName));
        System.out.print(String.format("Converting '%s' into '%s'... ", inputFileName, outputFileName));
        while ((line = br.readLine()) != null) {
            lineIndex++;
            String key = "ProtoId";
            String spacing = "              ";
            if (line.length() > key.length() && line.substring(0, key.length()).equalsIgnoreCase(key)) {
                String[] keyValue = line.split("\\s+");
                if (keyValue.length > 1) {
                    try {
                        Integer value = Integer.parseInt(keyValue[1]);
                        Integer converted = map.get(value);
                        if (converted != null) {
                            outputter.write(String.format("%s%s%s\n", keyValue[0], spacing, converted));
                            if (logLevel <= Converter.LogLevel.info.ordinal()) {
                                String message = String.format("[Info] Line %d: %s converted: %s -> %d\n",
                                        lineIndex, keyValue[0], keyValue[1], converted);
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
        System.out.println("done.");
        outputter.close();
        logger.close();
    }
}
