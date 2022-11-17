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
                                            proto.setSourceFile(fileName);
                                            if (ignoreList.contains(keyValue[0])) {
                                                //  do nothing

                                            //  important properties, used for comparison and reference
                                            } else if (keyValue[0].equalsIgnoreCase("ProtoId")) {
                                                proto.setProtoId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("PicMap")) {
                                                proto.setPicMap(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("PicInv")) {
                                                proto.setPicInv(keyValue[1]);

                                            //  these are not important for now, only to ensure less warning in log
                                            //  added for completeness, only foclassic proto id's

                                            //  generic
                                            } else if (keyValue[0].equalsIgnoreCase("Cost")) {
                                                proto.setCost(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("CraftLevel")) {
                                                proto.setCraftLevel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Deteriorable")) {
                                                proto.setDeteriorable(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("DisableEgg")) {
                                                proto.setDisableEgg(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Flags")) {
                                                proto.setFlags(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightDistance")) {
                                                proto.setLightDistance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightIntensity")) {
                                                proto.setLightIntensity(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightColor")) {
                                                proto.setLightColor(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Material")) {
                                                proto.setMaterial(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Misc_ChargeMax")) {
                                                proto.setMiscChargeMax(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("NeedBlueprint")) {
                                                proto.setNeedBlueprint(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptFunc")) {
                                                proto.setScriptFunc(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptModule")) {
                                                proto.setScriptModule(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Slot")) {
                                                proto.setSlot(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("SoundId")) {
                                                proto.setSoundId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Stackable")) {
                                                proto.setStackable(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("StartCount")) {
                                                proto.setStartCount(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Type")) {
                                                proto.setType(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weight")) {
                                                proto.setWeight(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Volume")) {
                                                proto.setVolume(Integer.parseInt(keyValue[1]));

                                            //  ammo
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_AcMod")) {
                                                proto.setAmmoAcMod(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_Caliber")) {
                                                proto.setAmmoCaliber(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DmgDiv")) {
                                                proto.setAmmoDmgDiv(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DmgMult")) {
                                                proto.setAmmoDmgMult(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DrMod")) {
                                                proto.setAmmoDrMod(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DTDiv")) {
                                                proto.setAmmoDtDiv(Integer.parseInt(keyValue[1]));

                                            //  armor
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_AC")) {
                                                proto.setArmorAc(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CMCritChance")) {
                                                proto.setArmorCmCritChance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CMCritPower")) {
                                                proto.setArmorCmCritPower(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeFemale")) {
                                                proto.setArmorCrTypeFemale(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeFemale2")) {
                                                proto.setArmorCrTypeFemale2(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale")) {
                                                proto.setArmorCrTypeMale(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale2")) {
                                                proto.setArmorCrTypeMale2(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale3")) {
                                                proto.setArmorCrTypeMale3(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale4")) {
                                                proto.setArmorCrTypeMale4(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRElectr")) {
                                                proto.setArmorDrElectr(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DREmp")) {
                                                proto.setArmorDrEmp(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRExplode")) {
                                                proto.setArmorDrExplode(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRFire")) {
                                                proto.setArmorDrFire(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRLaser")) {
                                                proto.setArmorDrLaser(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRNormal")) {
                                                proto.setArmorDrNormal(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRPlasma")) {
                                                proto.setArmorDrPlasma(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTElectr")) {
                                                proto.setArmorDtElectr(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTEmp")) {
                                                proto.setArmorDtEmp(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTExplode")) {
                                                proto.setArmorDtExplode(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTFire")) {
                                                proto.setArmorDtFire(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTLaser")) {
                                                proto.setArmorDtLaser(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTNormal")) {
                                                proto.setArmorDtNormal(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTPlasma")) {
                                                proto.setArmorDtPlasma(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_Perk")) {
                                                proto.setArmorPerk(Integer.parseInt(keyValue[1]));



                                                /*
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitBase")) {
                                                proto.setAnimWaitBase(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMin")) {
                                                proto.setAnimWaitRndMin(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMax")) {
                                                proto.setAnimWaitRndMax(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Corner")) {
                                                proto.setCorner(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetX")) {
                                                proto.setOffsetX(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetY")) {
                                                proto.setOffsetY(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptModule")) {
                                                proto.setScriptModule(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptFunc")) {
                                                proto.setScriptFunc(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("SpriteCut")) {
                                                proto.setSpriteCut(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Volume")) {
                                                proto.setVolume(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim1")) {
                                                proto.setWeaponAnim1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_0")) {
                                                proto.setWeaponAnim2_0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_1")) {
                                                proto.setWeaponAnim2_1(keyValue[1]);

                                                 */
                                            } else {
                                                ignoreList.add(keyValue[0]);
                                                if (logLevel <= LogLevel.warn.ordinal()) {
                                                    String message = String.format("[Warning] Line %d: Unknown field (%s) found for proto. Ignoring any occurrences of '%s' in %s.", lineIndex, line, keyValue[0], fileName);
                                                    logger.write(message + "\n");
                                                    System.out.println(message);
                                                }
                                            }
                                        }
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
                if (proto.sameMapPic(t) && proto.sameInventoryPic(t)) {
                    if (count > 0) {
                        if (logLevel <= LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Multiple match found for art: [%s %s] (%d, %d) (%s %s)\n",
                                    proto.getPicMap(), proto.getPicInv(), proto.getProtoId(), t.getProtoId(), proto.getSourceFile(), t.getSourceFile());
                            logger.write(message);
                            System.out.print(message);
                        }
                    }
                    map.put(proto.getProtoId(), t.getProtoId());
                    count++;
                    if (logLevel <= LogLevel.info.ordinal()) {
                        String message = String.format("[Info] Art: [%s %s] (%d, %d) (%s %s)\n",
                                proto.getPicMap(), proto.getPicInv(), proto.getProtoId(), t.getProtoId(), proto.getSourceFile(), t.getSourceFile());
                        logger.write(message);
                        System.out.print(message);
                    }
                }
                i++;
                if (i >= target.size() && count == 0) {
                    if (logLevel <= LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] No proto match found for: [%s %s] %d (%s)\n",
                                proto.getPicMap(), proto.getPicInv(), proto.getProtoId(), proto.getSourceFile());
                        logger.write(message);
                        System.out.print(message);
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
