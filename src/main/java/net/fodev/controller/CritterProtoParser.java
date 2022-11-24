package net.fodev.controller;

import net.fodev.model.CritterProto;
import net.fodev.model.ItemProto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CritterProtoParser {

    public enum LogLevel {
        info,
        warn,
        error
    }

    private int logLevel = 0;

    public static void logLine(String message, String logFileName) {
        FileWriter logger = null;
        try {
            logger = new FileWriter(logFileName, true);
            logger.write(message + "\n");
            logger.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> generateProtoMapping(List<CritterProto> source, List<CritterProto> target, String logFileName) throws IOException {
        Map<Integer, Integer> map = new LinkedHashMap<>();

        FileWriter logger = new FileWriter(logFileName, true);
        String message = String.format("Generating critter proto mapping.\n");
        logger.write(message);
        System.out.print(message);

        for (CritterProto sourceCritter: source) {
            Integer count = 0;
            for (CritterProto targetCritter: target) {
                if (sourceCritter.sameMainAttributes(targetCritter)) {
                    if (count < 1) {
                        map.put(sourceCritter.getPid(), targetCritter.getPid());
                        count++;
                    } else {
                        if (logLevel <= LogLevel.warn.ordinal()) {
                            String msg = String.format("[Warning] Multiple critter proto found (%d, %d).\n", sourceCritter.getPid(), targetCritter.getPid());
                            logger.write(msg);
                            System.out.print(msg);
                        }
                    }
                }
            }
        }

        logger.write("Done.\n\n");
        System.out.println("Done.");
        logger.close();

        return map;
    }

    public void printMappingToFile(Map<Integer, Integer> mapping, String outFilename) throws IOException {
        FileWriter out = new FileWriter(outFilename, true);
        mapping.entrySet().stream().forEach((kv) -> {
            try {
                out.write(String.format("%d %d\n", kv.getKey(), kv.getValue()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
    }

    public List<CritterProto> parseFromMultipleFiles(List<String> fileNames, String logFileName) throws IOException {
        List<CritterProto> result = new ArrayList<>();
        if (fileNames != null && fileNames.size() > 1) {
            for (String fileName : fileNames) {
                result.addAll(parseFromFile(fileName, logFileName));
            }
        }
        return result;
    }

    public List<CritterProto> parseFromFile(String fileName, String logFileName) throws IOException {
        List<CritterProto> critterProtos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        String key = "[";
        int lineIndex = 0;
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Parsing critter proto file: '%s'.\n", fileName));
        System.out.print(String.format("Parsing critter proto file: '%s'.", fileName));
        line = br.readLine();
        if (line != null) line = line.trim();
        lineIndex++;
        do {
            if (line != null) {
                if (line.length() >= key.length()) {
                    if (line.startsWith(key)) {
                        CritterProto critterProto = new CritterProto();
                        critterProto.setHeader(line);
                        boolean finishedReadingProto = false;
                        do {
                            line = br.readLine();
                            if (line != null) line = line.trim();
                            lineIndex++;
                            if (line == null || line.length() <2 || line.startsWith(key)) {
                                finishedReadingProto = true;
                            } else {
                                String[] keyValue = line.split("=");
                                if (keyValue.length > 1) {
                                    if ("Pid".equalsIgnoreCase(keyValue[0])) {
                                        try {
                                            critterProto.setPid(Integer.parseInt(keyValue[1]));
                                        } catch (NumberFormatException e) {
                                            if (logLevel <= ItemProtoParser.LogLevel.warn.ordinal()) {
                                                logger.write(String.format("[Warning] Line %d: Pid not in numeric format - %s\n", lineIndex, e.getMessage()));
                                                System.out.println(String.format("[Warning] Line %d: Pid not in numeric format - %s", lineIndex, e.getMessage()));
                                            }
                                        }
                                    } else {
                                        critterProto.add(keyValue[0], keyValue[1]);
                                    }
                                } else {
                                    if (logLevel <= ItemProtoParser.LogLevel.warn.ordinal()) {
                                        String message = String.format("[Warning] Line %d: Missing parameters for '%s' in %s.", lineIndex, keyValue[0], line);
                                        logger.write(message + "\n");
                                        System.out.println(message);
                                    }
                                }
                            }
                        } while (!finishedReadingProto);
                        critterProtos.add(critterProto);
                        if (logLevel <= ItemProtoParser.LogLevel.info.ordinal()) {
                            logger.write(String.format("[Info] Line %d: Parsed proto (%d)\n", lineIndex, critterProto.getPid()));
                            System.out.println(String.format("[Info] Line %d: Parsed proto (%d)", lineIndex, critterProto.getPid()));
                        }
                    } else {
                        line = br.readLine();
                        if (line != null) line = line.trim();
                        lineIndex++;
                    }
                } else {
                    line = br.readLine();
                    if (line != null) line = line.trim();
                    lineIndex++;
                }
            }
        }
        while (line != null);
        logger.write("Done.\n\n");
        System.out.println("Done.");
        logger.close();
        return critterProtos;
    }

    public void printToFile(List<CritterProto> critterProtos, String fileName) throws IOException {
        FileWriter out = new FileWriter(fileName, true);
        critterProtos.stream().forEach(cp -> {
            try {
                out.write(cp + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        out.close();
    }

    public void setLogLevel(String logLevel) {
        if ("warning".equalsIgnoreCase(logLevel) || "warn".equalsIgnoreCase(logLevel)) {
            this.logLevel = 1;
        } else if ("error".equalsIgnoreCase(logLevel) || "err".equalsIgnoreCase(logLevel)) {
            this.logLevel = 2;
        } else {
            this.logLevel = 0;
        }
    }
}
