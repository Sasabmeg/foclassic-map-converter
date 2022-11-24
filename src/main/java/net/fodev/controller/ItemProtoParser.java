package net.fodev.controller;

import net.fodev.model.ItemProto;
import net.fodev.model.ItemProtoMapping;

import java.io.*;
import java.util.*;

public class ProtoParser {
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

    public List<ItemProto> parseFromMultipleFiles(List<String> fileNames, String logFileName) throws IOException {
        List<ItemProto> result = new ArrayList<>();
        if (fileNames != null && fileNames.size() > 1) {
            for (String fileName : fileNames) {
                result.addAll(parseFromFile(fileName, logFileName));
            }
        }
        return result;
    }

    public List<ItemProto> parseFromFile(String fileName, String logFileName) throws IOException {
        List<ItemProto> itemProtos = new ArrayList<>();
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
                        ItemProto itemProto = new ItemProto();
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
                                            itemProto.setSourceFile(fileName);
                                            if (ignoreList.contains(keyValue[0])) {
                                                //  do nothing

                                            //  important properties, used for comparison and reference
                                            } else if (keyValue[0].equalsIgnoreCase("ProtoId")) {
                                                itemProto.setProtoId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("PicMap")) {
                                                itemProto.setPicMap(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("PicInv")) {
                                                itemProto.setPicInv(keyValue[1]);

                                            //  these are not important for now, only to ensure less warning in log
                                            //  added for completeness, only foclassic proto id's

                                            //  generic
                                            } else if (keyValue[0].equalsIgnoreCase("AnimHide_0")) {
                                                itemProto.setAnimHide0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimHide_1")) {
                                                itemProto.setAnimHide1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimShow_0")) {
                                                itemProto.setAnimShow0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimShow_1")) {
                                                itemProto.setAnimShow1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimStay_0")) {
                                                itemProto.setAnimStay0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimStay_1")) {
                                                itemProto.setAnimStay1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitBase")) {
                                                itemProto.setAnimWaitBase(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMax")) {
                                                itemProto.setAnimWaitRndMax(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("AnimWaitRndMin")) {
                                                itemProto.setAnimWaitRndMin(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Corner")) {
                                                itemProto.setCorner(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Cost")) {
                                                itemProto.setCost(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("CraftLevel")) {
                                                itemProto.setCraftLevel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Deteriorable")) {
                                                itemProto.setDeteriorable(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("DisableEgg")) {
                                                itemProto.setDisableEgg(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("DrawOrderOffsetHexY")) {
                                                itemProto.setDrawOrderOffsetHexY(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Flags")) {
                                                itemProto.setFlags(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("GroundLevel")) {
                                                itemProto.setGroundLevel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightDistance")) {
                                                itemProto.setLightDistance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightIntensity")) {
                                                itemProto.setLightIntensity(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("LightColor")) {
                                                itemProto.setLightColor(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Material")) {
                                                itemProto.setMaterial(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Misc_ChargeMax")) {
                                                itemProto.setMiscChargeMax(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("NeedBlueprint")) {
                                                itemProto.setNeedBlueprint(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetX")) {
                                                itemProto.setOffsetX(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("OffsetY")) {
                                                itemProto.setOffsetY(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptFunc")) {
                                                itemProto.setScriptFunc(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ScriptModule")) {
                                                itemProto.setScriptModule(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Slot")) {
                                                itemProto.setSlot(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("SoundId")) {
                                                itemProto.setSoundId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("SpriteCut")) {
                                                itemProto.setSpriteCut(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Stackable")) {
                                                itemProto.setStackable(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("StartCount")) {
                                                itemProto.setStartCount(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("StartValue_1")) {
                                                itemProto.setStartValue1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Type")) {
                                                itemProto.setType(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weight")) {
                                                itemProto.setWeight(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim1")) {
                                                itemProto.setWeaponAnim1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_0")) {
                                                itemProto.setWeaponAnim2_0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Anim2_1")) {
                                                itemProto.setWeaponAnim2_1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Volume")) {
                                                itemProto.setVolume(Integer.parseInt(keyValue[1]));

                                            //  ammo
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_AcMod")) {
                                                itemProto.setAmmoAcMod(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_Caliber")) {
                                                itemProto.setAmmoCaliber(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DmgDiv")) {
                                                itemProto.setAmmoDmgDiv(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DmgMult")) {
                                                itemProto.setAmmoDmgMult(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DrMod")) {
                                                itemProto.setAmmoDrMod(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ammo_DTDiv")) {
                                                itemProto.setAmmoDtDiv(Integer.parseInt(keyValue[1]));

                                            //  armor
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_AC")) {
                                                itemProto.setArmorAc(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CMCritChance")) {
                                                itemProto.setArmorCmCritChance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CMCritPower")) {
                                                itemProto.setArmorCmCritPower(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeFemale")) {
                                                itemProto.setArmorCrTypeFemale(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeFemale2")) {
                                                itemProto.setArmorCrTypeFemale2(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale")) {
                                                itemProto.setArmorCrTypeMale(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale2")) {
                                                itemProto.setArmorCrTypeMale2(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale3")) {
                                                itemProto.setArmorCrTypeMale3(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_CrTypeMale4")) {
                                                itemProto.setArmorCrTypeMale4(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRElectr")) {
                                                itemProto.setArmorDrElectr(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DREmp")) {
                                                itemProto.setArmorDrEmp(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRExplode")) {
                                                itemProto.setArmorDrExplode(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRFire")) {
                                                itemProto.setArmorDrFire(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRLaser")) {
                                                itemProto.setArmorDrLaser(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRNormal")) {
                                                itemProto.setArmorDrNormal(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DRPlasma")) {
                                                itemProto.setArmorDrPlasma(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTElectr")) {
                                                itemProto.setArmorDtElectr(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTEmp")) {
                                                itemProto.setArmorDtEmp(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTExplode")) {
                                                itemProto.setArmorDtExplode(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTFire")) {
                                                itemProto.setArmorDtFire(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTLaser")) {
                                                itemProto.setArmorDtLaser(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTNormal")) {
                                                itemProto.setArmorDtNormal(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_DTPlasma")) {
                                                itemProto.setArmorDtPlasma(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Armor_Perk")) {
                                                itemProto.setArmorPerk(Integer.parseInt(keyValue[1]));

                                            //  blueprint
                                            } else if (keyValue[0].equalsIgnoreCase("Blueprint_Id")) {
                                                itemProto.setBlueprintId(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Blueprint_Level")) {
                                                itemProto.setBlueprintLevel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Blueprint_Param")) {
                                                itemProto.setBlueprintParam(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Blueprint_Type")) {
                                                itemProto.setBlueprintType(Integer.parseInt(keyValue[1]));

                                            //  car
                                            } else if (keyValue[0].equalsIgnoreCase("BlockLines")) {
                                                itemProto.setBlockLines(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Car_CrittersCapacity")) {
                                                itemProto.setCarCrittersCapacity(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_DeteriorationRate")) {
                                                itemProto.setCarDeteriorationRate(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_Entrance")) {
                                                itemProto.setCarEntrance(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_FuelConsumption")) {
                                                itemProto.setCarFuelConsumption(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_MaxDeterioration")) {
                                                itemProto.setCarMaxDeterioration(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_MovementType")) {
                                                itemProto.setCarMovementType(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_Passability")) {
                                                itemProto.setCarPassability(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_Speed")) {
                                                itemProto.setCarSpeed(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Car_TankVolume")) {
                                                itemProto.setCarTankVolume(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ChildLines_0")) {
                                                itemProto.setChildLines0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ChildLines_1")) {
                                                itemProto.setChildLines1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("ChildPid_0")) {
                                                itemProto.setChildPid0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ChildPid_1")) {
                                                itemProto.setChildPid1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("ChildPid_2")) {
                                                itemProto.setChildPid2(Integer.parseInt(keyValue[1]));

                                            //  container
                                            } else if (keyValue[0].equalsIgnoreCase("Container_CannotPickUp")) {
                                                itemProto.setContainerCannotPickUp(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Container_Changeble")) {
                                                itemProto.setContainerChangeble(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Container_MagicHandsGrnd")) {
                                                itemProto.setContainerMagicHandsGrnd(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Container_Volume")) {
                                                itemProto.setContainerVolume(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Locker_Condition")) {
                                                itemProto.setLockerCondition(Integer.parseInt(keyValue[1]));

                                            //  door
                                            } else if (keyValue[0].equalsIgnoreCase("Door_NoBlockLight")) {
                                                itemProto.setDoorNoBlockLight(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Door_NoBlockMove")) {
                                                itemProto.setDoorNoBlockMove(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Door_NoBlockShoot")) {
                                                itemProto.setDoorNoBlockShoot(Integer.parseInt(keyValue[1]));

                                            //  drug
                                            } else if (keyValue[0].equalsIgnoreCase("Ingredient_Type_1")) {
                                                itemProto.setIngredientType1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Ingredient_Returns_1")) {
                                                itemProto.setIngredientReturns1(Integer.parseInt(keyValue[1]));

                                            //  grid
                                            } else if (keyValue[0].equalsIgnoreCase("Grid_Type")) {
                                                itemProto.setGridType(Integer.parseInt(keyValue[1]));

                                            //  misc
                                            } else if (keyValue[0].equalsIgnoreCase("HolodiskNum")) {
                                                itemProto.setHolodiscNum(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Misc_ToolSkillBonus")) {
                                                itemProto.setMiscToolSkillBonus(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Misc_ToolSkillNum")) {
                                                itemProto.setMiscToolSkillNum(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("RadioBroadcastSend")) {
                                                itemProto.setRadioBroadcastSend(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("RadioChannel")) {
                                                itemProto.setRadioChannel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("RevealSneakers")) {
                                                itemProto.setRevealSneakers(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("StartValue_2")) {
                                                itemProto.setStartValue2(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("StartValue_3")) {
                                                itemProto.setStartValue3(Integer.parseInt(keyValue[1]));

                                            //  weapon
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_ActiveUses")) {
                                                itemProto.setWeaponActiveUses(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Aim_0")) {
                                                itemProto.setWeaponAim0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Aim_1")) {
                                                itemProto.setWeaponAim1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_ApCost_0")) {
                                                itemProto.setWeaponApCost0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_ApCost_1")) {
                                                itemProto.setWeaponApCost1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Caliber")) {
                                                itemProto.setWeaponCaliber(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_CriticalFailture")) {
                                                itemProto.setWeaponCriticalFailure(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DefaultAmmoPid")) {
                                                itemProto.setWeaponDefaultAmmoPid(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgMin_0")) {
                                                itemProto.setWeaponDmgMin0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgMin_1")) {
                                                itemProto.setWeaponDmgMin1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgMax_0")) {
                                                itemProto.setWeaponDmgMax0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgMax_1")) {
                                                itemProto.setWeaponDmgMax1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgType_0")) {
                                                itemProto.setWeaponDmgType0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgType_1")) {
                                                itemProto.setWeaponDmgType1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_DmgType_2")) {
                                                itemProto.setWeaponDmgType2(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Effect_0")) {
                                                itemProto.setWeaponEffect0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Effect_1")) {
                                                itemProto.setWeaponEffect1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_IsUnarmed")) {
                                                itemProto.setWeaponIsUnarmed(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_MinStrength")) {
                                                itemProto.setWeaponMinStrength(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_MaxAmmoCount")) {
                                                itemProto.setWeaponMaxAmmoCount(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_MaxDist_0")) {
                                                itemProto.setWeaponMaxDist0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_MaxDist_1")) {
                                                itemProto.setWeaponMaxDist1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_NoRunning")) {
                                                itemProto.setWeaponNoRunning(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Perk")) {
                                                itemProto.setWeaponPerk(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_PicUse_0")) {
                                                itemProto.setWeaponPicUse0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_PicUse_1")) {
                                                itemProto.setWeaponPicUse1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_PicUse_2")) {
                                                itemProto.setWeaponPicUse2(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Remove_0")) {
                                                itemProto.setWeaponRemove0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Remove_1")) {
                                                itemProto.setWeaponRemove1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Round_0")) {
                                                itemProto.setWeaponRound0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Round_1")) {
                                                itemProto.setWeaponRound1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Skill_0")) {
                                                itemProto.setWeaponSkill0(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Skill_1")) {
                                                itemProto.setWeaponSkill1(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_Skill_2")) {
                                                itemProto.setWeaponSkill2(keyValue[1]);
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_SoundId_0")) {
                                                itemProto.setWeaponSoundId0(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_SoundId_1")) {
                                                itemProto.setWeaponSoundId1(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_UnarmedMinAgility")) {
                                                itemProto.setWeaponUnarmedMinAgility(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_UnarmedMinLevel")) {
                                                itemProto.setWeaponUnarmedMinLevel(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_UnarmedMinUnarmed")) {
                                                itemProto.setWeaponUnarmedMinUnarmed(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_UnarmedPriority")) {
                                                itemProto.setWeaponUnarmedPriority(Integer.parseInt(keyValue[1]));
                                            } else if (keyValue[0].equalsIgnoreCase("Weapon_UnarmedTree")) {
                                                itemProto.setWeaponUnarmedTree(keyValue[1]);

                                            //  add to ignore list unrecognized fields to reduce spam in log
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
                        itemProtos.add(itemProto);
                        if (logLevel <= LogLevel.info.ordinal()) {
                            logger.write(String.format("[Info] Line %d: Parsed proto (%d)\n", lineIndex, itemProto.getProtoId()));
                            System.out.println(String.format("[Info] Line %d: Parsed proto (%d)", lineIndex, itemProto.getProtoId()));
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
        return itemProtos;
    }

    public List<ItemProtoMapping> compareProtosVerbose(List<ItemProto> source, List<ItemProto> target, String logFileName) throws IOException {
        List<ItemProtoMapping> mapping = new ArrayList<>();
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Comparing protos..\n"));
        System.out.println(String.format("Comparing protos."));
        logger.write(String.format("Source protos: %d\n", source.size()));
        System.out.println(String.format("Source protos: %d", source.size()));
        logger.write(String.format("Target protos: %d\n", target.size()));
        System.out.println(String.format("Target protos: %d", target.size()));

        for (ItemProto itemProto : source) {
            int i = 0;
            int count = 0;
            while (i < target.size()) {
                ItemProto t = target.get(i);
                if (itemProto.sameMapPic(t) && itemProto.sameInventoryPic(t)) {
                    if (count > 0) {
                        if (logLevel <= LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Multiple match found for art: [%s %s] (%d, %d) (%s %s)\n",
                                    itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), t.getProtoId(), itemProto.getSourceFile(), t.getSourceFile());
                            logger.write(message);
                            System.out.print(message);
                        }
                    }
                    ItemProtoMapping itemProtoMapping = new ItemProtoMapping(itemProto.getProtoId(), t.getProtoId(), itemProto.getPicMap(), itemProto.getPicInv(),
                            itemProto.getSourceFile(), t.getSourceFile());
                    mapping.add(itemProtoMapping);
                    count++;
                    if (logLevel <= LogLevel.info.ordinal()) {
                        String message = String.format("[Info] Art: [%s %s] (%d, %d) (%s %s)\n",
                                itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), t.getProtoId(), itemProto.getSourceFile(), t.getSourceFile());
                        logger.write(message);
                        System.out.print(message);
                    }
                }
                i++;
                if (i >= target.size() && count == 0) {
                    if (logLevel <= LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] No proto match found for: [%s %s] %d (%s)\n",
                                itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), itemProto.getSourceFile());
                        logger.write(message);
                        System.out.print(message);
                    }
                }
            }
        }

        logger.write(String.format("Finished comparing protos.\n"));
        System.out.println(String.format("Finished comparing protos."));

        logger.close();
        Collections.sort(mapping);
        return mapping;
    }

    public Map<Integer, Integer> compareProtos(List<ItemProto> source, List<ItemProto> target, String logFileName) throws IOException {
        Map map = new TreeMap<Integer, Integer>();
        FileWriter logger = new FileWriter(logFileName, true);
        logger.write(String.format("Comparing protos..\n"));
        System.out.println(String.format("Comparing protos."));
        logger.write(String.format("Source protos: %d\n", source.size()));
        System.out.println(String.format("Source protos: %d", source.size()));
        logger.write(String.format("Target protos: %d\n", target.size()));
        System.out.println(String.format("Target protos: %d", target.size()));

        for (ItemProto itemProto : source) {
            int i = 0;
            int count = 0;
            while (i < target.size()) {
                ItemProto t = target.get(i);
                if (itemProto.sameMapPic(t) && itemProto.sameInventoryPic(t)) {
                    if (count > 0) {
                        if (logLevel <= LogLevel.warn.ordinal()) {
                            String message = String.format("[Warning] Multiple match found for art: [%s %s] (%d, %d) (%s %s)\n",
                                    itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), t.getProtoId(), itemProto.getSourceFile(), t.getSourceFile());
                            logger.write(message);
                            System.out.print(message);
                        }
                    }
                    map.put(itemProto.getProtoId(), t.getProtoId());
                    count++;
                    if (logLevel <= LogLevel.info.ordinal()) {
                        String message = String.format("[Info] Art: [%s %s] (%d, %d) (%s %s)\n",
                                itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), t.getProtoId(), itemProto.getSourceFile(), t.getSourceFile());
                        logger.write(message);
                        System.out.print(message);
                    }
                }
                i++;
                if (i >= target.size() && count == 0) {
                    if (logLevel <= LogLevel.warn.ordinal()) {
                        String message = String.format("[Warning] No proto match found for: [%s %s] %d (%s)\n",
                                itemProto.getPicMap(), itemProto.getPicInv(), itemProto.getProtoId(), itemProto.getSourceFile());
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

    public void generateMappingVerboseToFile(List<ItemProtoMapping> mapping, String outputFileName, String logFileName) throws IOException {
        FileWriter outPutter = new FileWriter(outputFileName);
        FileWriter logger = new FileWriter(logFileName, true);

        logger.write(String.format("Generating mapping file..\n"));
        System.out.println(String.format("Generating mapping file.."));

        for (ItemProtoMapping p : mapping) {
            String line = String.format("%d %d %s %s %s %s\n",
                    p.getSourceProtoId(), p.getTargetProtoId(), p.getPicMap(), p.getPicInv(), p.getSourceFile(), p.getTargetFile());
            outPutter.write(line);
            System.out.print(line);
        }

        logger.write(String.format("Done.\n"));
        System.out.println(String.format("Done."));

        logger.close();
        outPutter.close();
    }

    public Map<Integer, Integer> generateMapping(List<ItemProtoMapping> mapping) {
        Map<Integer, Integer> map = new HashMap<>();
        for (ItemProtoMapping p : mapping) {
            map.put(p.getSourceProtoId(), p.getTargetProtoId());
        }
        return map;
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
