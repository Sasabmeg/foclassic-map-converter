package net.fodev.controller;

import net.fodev.model.Fomap;
import net.fodev.model.FomapObject;
import net.fodev.model.FomapTile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FomapParser {
    enum LogLevel {
        info,
        warn,
        error
    }

    private int logLevel = 0;

    public Fomap parseFromFile(String fileName, String logFileName) throws IOException {
        Fomap result = new Fomap();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        FileWriter logger = new FileWriter(logFileName, true);

        String msg = String.format("Parsing fomap file: %s.\n", fileName);
        logger.write(msg);
        System.out.print(msg);

        String line;
        int lineIndex = 0;
        List<String> ignoreList = new ArrayList<>();

        //  skip until [Header]
        boolean headerFound = false;
        do {
            line = br.readLine();
            if (line != null) {
                lineIndex++;
                line = line.trim();
                //  [Header]
                if (line.length() > 2 && line.charAt(0) == '[') {
                    headerFound = true;
                    if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
                        String message = String.format("Parsing header.\n");
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            }
        } while (line != null && !headerFound);

        //  parse [Header]
        do {
            line = br.readLine();
            lineIndex++;
            if (line != null) {
                line = line.trim();
                String[] keyValue = line.split("\\s+");
                if (line.length() > 2 && keyValue.length > 0) {
                    try {
                        if ("Version".equalsIgnoreCase(keyValue[0])) {
                            result.setVersion(Integer.parseInt(keyValue[1]));
                        } else if ("MaxHexX".equalsIgnoreCase(keyValue[0])) {
                            result.setMaxHexX(Integer.parseInt(keyValue[1]));
                        } else if ("MaxHexY".equalsIgnoreCase(keyValue[0])) {
                            result.setMaxHexY(Integer.parseInt(keyValue[1]));
                        } else if ("WorkHexX".equalsIgnoreCase(keyValue[0])) {
                            result.setWorkHexX(Integer.parseInt(keyValue[1]));
                        } else if ("WorkHexY".equalsIgnoreCase(keyValue[0])) {
                            result.setWorkHexY(Integer.parseInt(keyValue[1]));
                        } else if ("ScriptModule".equalsIgnoreCase(keyValue[0])) {
                            result.setScriptModule(keyValue[1]);
                        } else if ("ScriptFunc".equalsIgnoreCase(keyValue[0])) {
                            result.setScriptFunc(keyValue[1]);
                        } else if ("NoLogOut".equalsIgnoreCase(keyValue[0])) {
                            result.setNoLogOut(Integer.parseInt(keyValue[1]));
                        } else if ("Time".equalsIgnoreCase(keyValue[0])) {
                            result.setTime(Integer.parseInt(keyValue[1]));
                        } else if ("DayTime".equalsIgnoreCase(keyValue[0])) {
                            result.setDayTime(Arrays.stream(keyValue).skip(1).limit(keyValue.length - 1).collect(Collectors.joining(" ")));
                        } else if ("DayColor0".equalsIgnoreCase(keyValue[0])) {
                            result.setDayColor0(Arrays.stream(keyValue).skip(1).limit(keyValue.length - 1).collect(Collectors.joining(" ")));
                        } else if ("DayColor1".equalsIgnoreCase(keyValue[0])) {
                            result.setDayColor1(Arrays.stream(keyValue).skip(1).limit(keyValue.length - 1).collect(Collectors.joining(" ")));
                        } else if ("DayColor2".equalsIgnoreCase(keyValue[0])) {
                            result.setDayColor2(Arrays.stream(keyValue).skip(1).limit(keyValue.length - 1).collect(Collectors.joining(" ")));
                        } else if ("DayColor3".equalsIgnoreCase(keyValue[0])) {
                            result.setDayColor3(Arrays.stream(keyValue).skip(1).limit(keyValue.length - 1).collect(Collectors.joining(" ")));
                        } else {
                            if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                                String message = String.format("[Warning] Line %d: Unknown field ('%s') found.", lineIndex, line);
                                logger.write(message + "\n");
                                System.out.println(message);
                            }
                        }
                    } catch (NumberFormatException e) {
                        if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Line %d: %s\n", lineIndex, e);
                            logger.write(message);
                            System.out.print(message);
                        }
                    }
                }
            }
        } while (line != null && line.length() > 2);

        //  print header to log and screen
        if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
            logger.write(result.getHeader() + "\n");
            System.out.println(result.getHeader());
        }

        //  parse [Tiles]
        do {
            line = br.readLine();
            lineIndex++;
            if (line != null) {
                line = line.trim();
                String[] keyValue = line.split("\\s+");
                try {
                    if ("[Tiles]".equalsIgnoreCase(keyValue[0])) {
                        if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
                            logger.write("Parsing tiles.\n");
                            System.out.print("Parsing tiles.\n");
                        }
                    } else if ("tile".equalsIgnoreCase(keyValue[0])) {
                        if (keyValue.length >= 4) {
                            FomapTile tile = new FomapTile();
                            tile.setType("tile");
                            tile.setHexX(Integer.parseInt(keyValue[1]));
                            tile.setHexY(Integer.parseInt(keyValue[2]));
                            tile.setFile(keyValue[3]);
                            result.addTile(tile);
                            if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
                                logger.write(tile.toString() + "\n");
                                System.out.print(tile + "\n");
                            }
                        } else {
                            if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                                String message = String.format("[Warning] Line %d: Missing parameters for '%s': (%s).\n", lineIndex, "tile", line);
                                logger.write(message);
                                System.out.print(message);
                            }
                        }
                    } else if ("roof".equalsIgnoreCase(keyValue[0])) {
                        if (keyValue.length >= 4) {
                            FomapTile roof = new FomapTile();
                            roof.setType("roof");
                            roof.setHexX(Integer.parseInt(keyValue[1]));
                            roof.setHexY(Integer.parseInt(keyValue[2]));
                            roof.setFile(keyValue[3]);
                            result.addTile(roof);
                            if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
                                logger.write(roof.toString() + "\n");
                                System.out.print(roof + "\n");
                            }
                        } else {
                            if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                                String message = String.format("[Warning] Line %d: Missing parameters for '%s': (%s).\n", lineIndex, "roof", line);
                                logger.write(message);
                                System.out.print(message);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] Line %d: %s\n", lineIndex, e);
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            }
        } while (line != null && line.length() > 2);

        //  parse [Objects]
        logger.write("Parsing objects.\n");
        System.out.print("Parsing objects.\n");
        do {
            line = br.readLine();
            lineIndex++;
            if (line != null) {
                line = line.trim();
                String[] keyValue = line.split("\\s+");
                try {
                    if (line.length() < 2 || line.charAt(0) == '[') {
                        //  ignore empty, short lines or info lines
                    } else if ("MapObjType".equalsIgnoreCase(keyValue[0])) {
                        FomapObject object = new FomapObject();
                        object.setType(Integer.parseInt(keyValue[1]));
                        do {
                            line = br.readLine();
                            lineIndex++;
                            if (line != null) {
                                line = line.trim();
                                String[] split = line.split("\\s+");
                                if (line.length() > 2 && split.length > 0) {
                                    if ("ProtoId".equalsIgnoreCase(split[0])) {
                                        object.setProtoId(Integer.parseInt(split[1]));
                                    } else if ("MapX".equalsIgnoreCase(split[0])) {
                                        object.setMapX(Integer.parseInt(split[1]));
                                    } else if ("MapY".equalsIgnoreCase(split[0])) {
                                        object.setMapY(Integer.parseInt(split[1]));
                                    } else if ("OffsetX".equalsIgnoreCase(split[0])) {
                                        object.setOffsetX(Integer.parseInt(split[1]));
                                    } else if ("OffsetY".equalsIgnoreCase(split[0])) {
                                        object.setOffsetY(Integer.parseInt(split[1]));
                                    } else if ("Dir".equalsIgnoreCase(split[0])) {
                                        object.setDir(Integer.parseInt(split[1]));
                                    } else if ("ScriptName".equalsIgnoreCase(split[0])) {
                                        object.setScriptName(split[1]);
                                    } else if ("FuncName".equalsIgnoreCase(split[0])) {
                                        object.setFuncName(split[1]);
                                    } else if ("Critter_Cond".equalsIgnoreCase(split[0])) {
                                        object.setCritterCond(Integer.parseInt(split[1]));
                                    } else if ("Critter_Anim1".equalsIgnoreCase(split[0])) {
                                        object.setCritterAnim1(Integer.parseInt(split[1]));
                                    } else if ("Critter_Anim2".equalsIgnoreCase(split[0])) {
                                        object.setCritterAnim2(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex0".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex0(split[1]);
                                    } else if ("Critter_ParamValue0".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue0(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex1".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex1(split[1]);
                                    } else if ("Critter_ParamValue1".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue1(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex2".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex2(split[1]);
                                    } else if ("Critter_ParamValue2".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue2(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex3".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex3(split[1]);
                                    } else if ("Critter_ParamValue3".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue3(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex4".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex4(split[1]);
                                    } else if ("Critter_ParamValue4".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue4(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex5".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex5(split[1]);
                                    } else if ("Critter_ParamValue5".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue5(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex6".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex6(split[1]);
                                    } else if ("Critter_ParamValue6".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue6(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex7".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex7(split[1]);
                                    } else if ("Critter_ParamValue7".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue7(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex8".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex8(split[1]);
                                    } else if ("Critter_ParamValue8".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue8(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex9".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex9(split[1]);
                                    } else if ("Critter_ParamValue9".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue9(Integer.parseInt(split[1]));
                                    } else if ("Critter_ParamIndex10".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamIndex10(split[1]);
                                    } else if ("Critter_ParamValue10".equalsIgnoreCase(split[0])) {
                                        object.setCritterParamValue10(Integer.parseInt(split[1]));
                                    } else if ("UID".equalsIgnoreCase(split[0])) {
                                        object.setUid(Integer.parseInt(split[1]));
                                    } else if ("ContainerUID".equalsIgnoreCase(split[0])) {
                                        object.setContainerUID(Integer.parseInt(split[1]));
                                    } else if ("Item_LockerDoorId".equalsIgnoreCase(split[0])) {
                                        object.setItemLockerDoorId(Integer.parseInt(split[1]));
                                    } else if ("Item_LockerComplexity".equalsIgnoreCase(split[0])) {
                                        object.setItemLockerComplexity(Integer.parseInt(split[1]));
                                    } else if ("Item_LockerCondition".equalsIgnoreCase(split[0])) {
                                        object.setItemLockerCondition(Integer.parseInt(split[1]));
                                    } else if ("Item_ItemSlot".equalsIgnoreCase(split[0])) {
                                        object.setItemItemSlot(Integer.parseInt(split[1]));
                                    } else if ("Item_BrokenFlags".equalsIgnoreCase(split[0])) {
                                        object.setItemBrokenFlags(Integer.parseInt(split[1]));
                                    } else if ("Item_Count".equalsIgnoreCase(split[0])) {
                                        object.setItemCount(Integer.parseInt(split[1]));
                                    } else if ("Item_Val0".equalsIgnoreCase(split[0])) {
                                        object.setItemVal0(Integer.parseInt(split[1]));
                                    } else if ("Item_Val1".equalsIgnoreCase(split[0])) {
                                        object.setItemVal1(Integer.parseInt(split[1]));
                                    } else if ("Item_Val2".equalsIgnoreCase(split[0])) {
                                        object.setItemVal2(Integer.parseInt(split[1]));
                                    } else if ("Scenery_ParamsCount".equalsIgnoreCase(split[0])) {
                                        object.setSceneryParamsCount(Integer.parseInt(split[1]));
                                    } else if ("Scenery_Param0".equalsIgnoreCase(split[0])) {
                                        object.setSceneryParam0(Integer.parseInt(split[1]));
                                    } else if ("Scenery_ToDir".equalsIgnoreCase(split[0])) {
                                        object.setSceneryToDir(Integer.parseInt(split[1]));
                                    } else if ("Scenery_ToEntire".equalsIgnoreCase(split[0])) {
                                        object.setSceneryToEntire(Integer.parseInt(split[1]));
                                    } else if ("Scenery_ToMapPid".equalsIgnoreCase(split[0])) {
                                        object.setSceneryToMapPid(Integer.parseInt(split[1]));
                                    } else if ("Scenery_CanUse".equalsIgnoreCase(split[0])) {
                                        object.setSceneryCanUse(Integer.parseInt(split[1]));
                                    } else if ("Scenery_CanTalk".equalsIgnoreCase(split[0])) {
                                        object.setSceneryCanTalk(Integer.parseInt(split[1]));
                                    } else if ("LightColor".equalsIgnoreCase(split[0])) {
                                        object.setLightColor(Integer.parseInt(split[1]));
                                    } else if ("LightDistance".equalsIgnoreCase(split[0])) {
                                        object.setLightDistance(Integer.parseInt(split[1]));
                                    } else if ("LightIntensity".equalsIgnoreCase(split[0])) {
                                        object.setLightIntensity(Integer.parseInt(split[1]));
                                    } else if ("AnimStayBegin".equalsIgnoreCase(split[0])) {
                                        object.setAnimStayBegin(Integer.parseInt(split[1]));
                                    } else if ("AnimStayEnd".equalsIgnoreCase(split[0])) {
                                        object.setAnimStayEnd(Integer.parseInt(split[1]));
                                    } else {
                                        if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                                            String message = String.format("[Warning] Line %d: Unknown field (%s) found. Ignoring any occurrences of '%s' in %s.", lineIndex, line, split[0], fileName);
                                            logger.write(message + "\n");
                                            System.out.println(message);
                                        }
                                    }
                                }
                            }
                        } while (line != null && line.length() > 2);
                        result.addObject(object);
                        if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
                            logger.write(object.toString() + "\n");
                            System.out.println(object);
                        }
                    } else {
                        if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Line %d: Unknown field ('%s') found.", lineIndex, line);
                            logger.write(message + "\n");
                            System.out.println(message);
                        }
                    }
                } catch (NumberFormatException e) {
                    if (logLevel <= ProtoParser.LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] Line %d: %s\n", lineIndex, e);
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            }
        } while (line != null);

        if (logLevel <= ProtoParser.LogLevel.info.ordinal()) {
            logger.write("Done.\n");
            System.out.println("Done.");
        }
        logger.close();

        return result;
    }
}
