package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class FomapObject {
    @Getter @Setter private Integer type;
    @Getter @Setter private Integer protoId;
    @Getter @Setter private Integer mapX;
    @Getter @Setter private Integer mapY;
    @Getter @Setter private Integer offsetX;
    @Getter @Setter private Integer offsetY;
    @Getter @Setter private Integer dir;
    @Getter @Setter private Integer uid;
    @Getter @Setter private String scriptName;
    @Getter @Setter private String funcName;
    @Getter @Setter private Integer lightColor;
    @Getter @Setter private Integer lightDistance;
    @Getter @Setter private Integer lightIntensity;
    @Getter @Setter private Integer critterCond;
    @Getter @Setter private Integer critterAnim1;
    @Getter @Setter private Integer critterAnim2;
    @Getter @Setter private String critterParamIndex0;
    @Getter @Setter private Integer critterParamValue0;
    @Getter @Setter private String critterParamIndex1;
    @Getter @Setter private Integer critterParamValue1;
    @Getter @Setter private String critterParamIndex2;
    @Getter @Setter private Integer critterParamValue2;
    @Getter @Setter private String critterParamIndex3;
    @Getter @Setter private Integer critterParamValue3;
    @Getter @Setter private String critterParamIndex4;
    @Getter @Setter private Integer critterParamValue4;
    @Getter @Setter private String critterParamIndex5;
    @Getter @Setter private Integer critterParamValue5;
    @Getter @Setter private String critterParamIndex6;
    @Getter @Setter private Integer critterParamValue6;
    @Getter @Setter private String critterParamIndex7;
    @Getter @Setter private Integer critterParamValue7;
    @Getter @Setter private String critterParamIndex8;
    @Getter @Setter private Integer critterParamValue8;
    @Getter @Setter private String critterParamIndex9;
    @Getter @Setter private Integer critterParamValue9;
    @Getter @Setter private String critterParamIndex10;
    @Getter @Setter private Integer critterParamValue10;

    @Getter @Setter private Integer containerUID;
    @Getter @Setter private Integer itemLockerDoorId;
    @Getter @Setter private Integer itemLockerComplexity;
    @Getter @Setter private Integer itemLockerCondition;
    @Getter @Setter private Integer itemItemSlot;
    @Getter @Setter private Integer itemCount;
    @Getter @Setter private Integer itemBrokenFlags;
    @Getter @Setter private Integer itemVal0;
    @Getter @Setter private Integer itemVal1;
    @Getter @Setter private Integer itemVal2;

    @Getter @Setter private Integer sceneryTriggerNum;
    @Getter @Setter private Integer sceneryToEntire;
    @Getter @Setter private Integer sceneryToDir;
    @Getter @Setter private Integer sceneryToMapPid;
    @Getter @Setter private Integer sceneryCanUse;
    @Getter @Setter private Integer sceneryCanTalk;
    @Getter @Setter private Integer sceneryParamsCount;
    @Getter @Setter private Integer sceneryParam0;

    @Getter @Setter private Integer animStayBegin;
    @Getter @Setter private Integer animStayEnd;

    @Override
    public String toString() {
        String result = String.format("%-20s %d\n", "MapObjType", type);
        if (protoId != null) result += String.format("%-20s %d\n", "ProtoId", protoId);
        if (mapX != null) result += String.format("%-20s %d\n", "MapX", mapX);
        if (mapY != null) result += String.format("%-20s %d\n", "MapY", mapY);
        if (dir != null) result += String.format("%-20s %d\n", "Dir", dir);
        if (uid != null) result += String.format("%-20s %d\n", "UID", uid);
        if (scriptName != null) result += String.format("%-20s %s\n", "ScriptName", scriptName);
        if (funcName != null) result += String.format("%-20s %s\n", "FuncName", funcName);
        if (lightColor != null) result += String.format("%-20s %d\n", "LightColor", lightColor);
        if (lightDistance != null) result += String.format("%-20s %d\n", "LightDistance", lightDistance);
        if (lightIntensity != null) result += String.format("%-20s %d\n", "LightIntensity", lightIntensity);
        if (critterCond != null) result += String.format("%-20s %d\n", "Critter_Cond", critterCond);
        if (critterAnim1 != null) result += String.format("%-20s %d\n", "Critter_Anim1", critterAnim1);
        if (critterAnim2 != null) result += String.format("%-20s %d\n", "Critter_Anim2", critterAnim2);
        if (critterParamIndex0 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex0", critterParamIndex0);
        if (critterParamValue0 != null) result += String.format("%-20s %d\n", "Critter_ParamValue0", critterParamValue0);
        if (critterParamIndex1 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex1", critterParamIndex1);
        if (critterParamValue1 != null) result += String.format("%-20s %d\n", "Critter_ParamValue1", critterParamValue1);
        if (critterParamIndex2 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex2", critterParamIndex2);
        if (critterParamValue2 != null) result += String.format("%-20s %d\n", "Critter_ParamValue2", critterParamValue2);
        if (critterParamIndex3 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex3", critterParamIndex3);
        if (critterParamValue3 != null) result += String.format("%-20s %d\n", "Critter_ParamValue3", critterParamValue3);
        if (critterParamIndex4 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex4", critterParamIndex4);
        if (critterParamValue4 != null) result += String.format("%-20s %d\n", "Critter_ParamValue4", critterParamValue4);
        if (critterParamIndex5 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex5", critterParamIndex5);
        if (critterParamValue5 != null) result += String.format("%-20s %d\n", "Critter_ParamValue5", critterParamValue5);
        if (critterParamIndex6 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex6", critterParamIndex6);
        if (critterParamValue6 != null) result += String.format("%-20s %d\n", "Critter_ParamValue6", critterParamValue6);
        if (critterParamIndex7 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex7", critterParamIndex7);
        if (critterParamValue7 != null) result += String.format("%-20s %d\n", "Critter_ParamValue7", critterParamValue7);
        if (critterParamIndex8 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex8", critterParamIndex8);
        if (critterParamValue8 != null) result += String.format("%-20s %d\n", "Critter_ParamValue8", critterParamValue8);
        if (critterParamIndex9 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex9", critterParamIndex9);
        if (critterParamValue9 != null) result += String.format("%-20s %d\n", "Critter_ParamValue9", critterParamValue9);
        if (critterParamIndex10 != null) result += String.format("%-20s %s\n", "Critter_ParamIndex10", critterParamIndex10);
        if (critterParamValue10 != null) result += String.format("%-20s %d\n", "Critter_ParamValue10", critterParamValue10);

        if (containerUID != null) result += String.format("%-20s %d\n", "ContainerUID", containerUID);
        if (itemLockerDoorId != null) result += String.format("%-20s %d\n", "Item_LockerDoorId", itemLockerDoorId);
        if (itemLockerComplexity != null) result += String.format("%-20s %d\n", "Item_LockerComplexity", itemLockerComplexity);
        if (itemLockerCondition != null) result += String.format("%-20s %d\n", "Item_LockerCondition", itemLockerCondition);
        if (itemItemSlot != null) result += String.format("%-20s %d\n", "Item_ItemSlot", itemItemSlot);
        if (itemBrokenFlags != null) result += String.format("%-20s %d\n", "Item_BrokenFlags", itemBrokenFlags);
        if (itemCount != null) result += String.format("%-20s %d\n", "Item_Count", itemCount);
        if (itemVal0 != null) result += String.format("%-20s %d\n", "Item_Val0", itemVal0);
        if (itemVal1 != null) result += String.format("%-20s %d\n", "Item_Val1", itemVal1);
        if (itemVal2 != null) result += String.format("%-20s %d\n", "Item_Val2", itemVal2);

        if (sceneryTriggerNum != null) result += String.format("%-20s %d\n", "MapY", sceneryTriggerNum);
        if (sceneryToMapPid != null) result += String.format("%-20s %d\n", "Scenery_ToMapPid", sceneryToMapPid);
        if (sceneryToEntire != null) result += String.format("%-20s %d\n", "Scenery_ToEntire", sceneryToEntire);
        if (sceneryToDir != null) result += String.format("%-20s %d\n", "Scenery_ToDir", sceneryToDir);
        if (sceneryCanUse != null) result += String.format("%-20s %d\n", "Scenery_CanUse", sceneryCanUse);
        if (sceneryCanTalk != null) result += String.format("%-20s %d\n", "Scenery_CanTalk", sceneryCanTalk);
        if (sceneryParamsCount != null) result += String.format("%-20s %d\n", "Scenery_ParamsCount", sceneryParamsCount);
        if (sceneryParam0 != null) result += String.format("%-20s %d\n", "Scenery_Param0", sceneryParam0);

        if (offsetX != null) result += String.format("%-20s %d\n", "OffsetX", offsetX);
        if (offsetY != null) result += String.format("%-20s %d\n", "OffsetY", offsetY);

        if (animStayBegin != null) result += String.format("%-20s %d\n", "AnimStayBegin", animStayBegin);
        if (animStayEnd != null) result += String.format("%-20s %d\n", "AnimStayEnd", animStayEnd);

        return result;
    }

}
