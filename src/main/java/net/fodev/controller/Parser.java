package net.fodev.controller;

import net.fodev.model.Proto;

import java.io.*;
import java.util.*;

public class Parser {
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

    public List<Proto> parseFromMultipleFiles(List<String> fileNames, String logFileName) throws IOException {
        List<Proto> result = new ArrayList<>();
        if (fileNames != null && fileNames.size() > 1) {
            for (String fileName : fileNames) {
                result.addAll(parseFromFile(fileName, logFileName));
            }
        }
        return result;
    }

    public List<Proto> parseFromFile(String fileName, String logFileName) throws IOException {
        List<Proto> protos = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        String key = "[Proto]";
        int lineIndex = 0;
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Parsing file: '%s'.\n", fileName));
        System.out.print(String.format("Parsing file: '%s'.", fileName));
        line = br.readLine();
        if (line != null) line = line.trim();
        lineIndex++;
        List<String> ignoreList = new ArrayList<String>();
        do {
            if (line != null) {
                if (line.length() >= key.length()) {
                    if (line.substring(0, key.length()).equals(key)) {
                        Proto proto = new Proto();
                        boolean finishedReadingProto = false;
                        do {
                            line = br.readLine();
                            if (line != null) line = line.trim();
                            lineIndex++;
                            if (line == null || (line.length() >= key.length() && line.substring(0, key.length()).equals(key))) {
                                finishedReadingProto = true;
                            } else {
                                if (line != null) {
                                    String[] keyValue = line.split("=");
                                    try {
                                        if (keyValue.length > 1) {
                                            if (ignoreList.contains(keyValue[0])) {
                                                //  do nothing
                                            } else if (keyValue[0].equalsIgnoreCase("ProtoId")) {
                                                proto.setProtoId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Type")) {
                                                proto.setType(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("PicMap")) {
                                                proto.setPicMap(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("PicInv")) {
                                                proto.setPicInv(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Flags")) {
                                                proto.setFlags(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("SoundId")) {
                                                proto.setSound(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Material")) {
                                                proto.setMaterial(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Corner")) {
                                                proto.setCorner(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("DisableEgg")) {
                                                proto.setDisableEgg(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightDistance")) {
                                                proto.setLightDistance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightIntensity")) {
                                                proto.setLightIntensity(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightColor")) {
                                                proto.setLightColor(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetX")) {
                                                proto.setOffsetX(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetY")) {
                                                proto.setOffsetY(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weight")) {
                                                proto.setWeight(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim1")) {
                                                proto.setWeaponAnim1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_0")) {
                                                proto.setWeaponAnim2_0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_1")) {
                                                proto.setWeaponAnim2_1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitBase")) {
                                                proto.setAnimWaitBase(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMin")) {
                                                proto.setAnimWaitRndMin(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMax")) {
                                                proto.setAnimWaitRndMax(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptModule")) {
                                                proto.setScriptModule(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptFunc")) {
                                                proto.setScriptFunc(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Volume")) {
                                                proto.setVolume(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Cost")) {
                                                proto.setCost(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("SpriteCut")) {
                                                proto.setSpriteCut(Integer.parseInt(keyValue[1]));
                                            } else {
                                                ignoreList.add(keyValue[0]);
                                                if (logLevel <= LogLevel.warn.ordinal()) {
                                                    String message = String.format("[Warning] Line %d: Unknown field (%s) found for proto. Ignoring any occurrences of '%s' in this file.", lineIndex, line, keyValue[0]);
                                                    logger.write(message + "\n");
                                                    System.out.println(message);
                                                }
                                            }
                                        }
                                        //System.out.println(Arrays.toString(keyValue));
                                    } catch (NumberFormatException e) {
                                        if (logLevel <= LogLevel.warn.ordinal()) {
                                            logger.write(String.format("[Warning] Line %d: %s\n", lineIndex, e.toString()));
                                            System.out.println(String.format("[Warning] Line %d: %s", lineIndex, e.toString()));
                                        }
                                    }
                                }
                            }
                        } while (!finishedReadingProto);
                        protos.add(proto);
                        if (logLevel <= LogLevel.info.ordinal()) {
                            logger.write(String.format("[Info] Line %d: Parsed proto (%d)\n", lineIndex, proto.getProtoId()));
                            System.out.println(String.format("[Info] Line %d: Parsed proto (%d)", lineIndex, proto.getProtoId()));
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
        return protos;
    }

    public Map<Integer, Integer> compareProtos(List<Proto> source, List<Proto> target, String logFileName) throws IOException {
        Map map = new TreeMap<Integer, Integer>();
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Comparing protos..\n"));
        System.out.println(String.format("Comparing protos."));
        logger.write(String.format("Source protos: %d\n", source.size()));
        System.out.println(String.format("Source protos: %d", source.size()));
        logger.write(String.format("Target protos: %d\n", target.size()));
        System.out.println(String.format("Target protos: %d", target.size()));

        for (Proto proto : source) {
            int i = 0;
            int count = 0;
            while (i < target.size()) {
                Proto t = target.get(i);
                if (proto.getPicMap().equalsIgnoreCase(t.getPicMap())) {
                    if (count > 0) {
                        if (logLevel <= LogLevel.warn.ordinal()) {
                            logger.write(String.format("[Warning] Multiple match found for art: %s (%d, %d)\n", proto.getPicMap(), proto.getProtoId(), t.getProtoId()));
                            System.out.println(String.format("[Warning] Multiple match found art: %s (%d, %d)", proto.getPicMap(), proto.getProtoId(), t.getProtoId()));
                        }
                    }
                    map.put(proto.getProtoId(), t.getProtoId());
                    count++;
                    if (logLevel <= LogLevel.info.ordinal()) {
                        logger.write(String.format("[Info] %s: (%d, %d)\n", proto.getPicMap(), proto.getProtoId(), t.getProtoId()));
                        System.out.println(String.format("(%d, %d)", proto.getProtoId(), t.getProtoId()));
                    }
                }
                i++;
                if (i >= target.size() && count == 0) {
                    if (logLevel <= LogLevel.warn.ordinal()) {
                        logger.write(String.format("[Warning] No proto match found for: %s %d\n", proto.getPicMap(), proto.getProtoId()));
                        System.out.println(String.format("[Warning] No proto match found for: %s %d", proto.getPicMap(), proto.getProtoId()));
                    }
                }
            }
        }

        logger.write(String.format("Finished comparing protos.\n"));
        System.out.println(String.format("Finished comparing protos."));

        logger.close();
        return map;
    }

    public void generateMapping(Map<Integer, Integer> mapping, String outputFileName, String logFileName) throws IOException {
        FileWriter outPutter = new FileWriter(outputFileName);
        FileWriter logger = new FileWriter(logFileName, true);

        logger.write(String.format("Generating mapping file..\n"));
        System.out.println(String.format("Generating mapping file.."));

        for (Map.Entry<Integer, Integer> entry : mapping.entrySet()) {
            outPutter.write(String.format("%d %d\n", entry.getKey(), entry.getValue()));
            System.out.println(String.format("%d %d", entry.getKey(), entry.getValue()));
        }

        logger.write(String.format("Done.\n"));
        System.out.println(String.format("Done."));

        logger.close();
        outPutter.close();
    }

    public void setLogLevel(String logLevel) {
        if ("warning".equalsIgnoreCase(logLevel)) {
            this.logLevel = 1;
        } else if ("error".equalsIgnoreCase(logLevel)) {
            this.logLevel = 2;
        } else {
            this.logLevel = 0;
        }
    }
}
