package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class ItemProto implements Comparable<ItemProto> {

    //  these are important for comparison and reference
    @Getter @Setter private Integer protoId;
    @Getter @Setter private String sourceFile;
    @Getter @Setter private String picMap;
    @Getter @Setter private String picInv;

    //  less important ones for now, only for cleaner warnings in logs

    //  generic
    @Getter @Setter private Integer animHide0;
    @Getter @Setter private Integer animHide1;
    @Getter @Setter private Integer animShow0;
    @Getter @Setter private Integer animShow1;
    @Getter @Setter private Integer animStay0;
    @Getter @Setter private Integer animStay1;
    @Getter @Setter private Integer animWaitBase;
    @Getter @Setter private Integer animWaitRndMax;
    @Getter @Setter private Integer animWaitRndMin;
    @Getter @Setter private Integer corner;
    @Getter @Setter private Integer cost;
    @Getter @Setter private Integer craftLevel;
    @Getter @Setter private String deteriorable;
    @Getter @Setter private String disableEgg;
    @Getter @Setter private Integer drawOrderOffsetHexY;
    @Getter @Setter private Integer flags;
    @Getter @Setter private Integer groundLevel;
    @Getter @Setter private Integer lightDistance;
    @Getter @Setter private Integer lightIntensity;
    @Getter @Setter private Integer lightColor;
    @Getter @Setter private String material;
    @Getter @Setter private Integer miscChargeMax;
    @Getter @Setter private Integer needBlueprint;
    @Getter @Setter private Integer offsetX;
    @Getter @Setter private Integer offsetY;
    @Getter @Setter private String scriptFunc;
    @Getter @Setter private String scriptModule;
    @Getter @Setter private String slot;
    @Getter @Setter private Integer soundId;
    @Getter @Setter private Integer spriteCut;
    @Getter @Setter private String stackable;
    @Getter @Setter private Integer startCount;
    @Getter @Setter private Integer startValue1;
    @Getter @Setter private String type;
    @Getter @Setter private Integer volume;
    @Getter @Setter private String weaponAnim1;
    @Getter @Setter private String weaponAnim2_0;
    @Getter @Setter private String weaponAnim2_1;
    @Getter @Setter private Integer weight;

    //  ammo (type = 4)
    @Getter @Setter private Integer ammoAcMod;
    @Getter @Setter private String ammoCaliber;
    @Getter @Setter private Integer ammoDmgDiv;
    @Getter @Setter private Integer ammoDmgMult;
    @Getter @Setter private Integer ammoDrMod;
    @Getter @Setter private Integer ammoDtDiv;

    //  armor (type = 1)
    @Getter @Setter private Integer armorAc;
    @Getter @Setter private Integer armorCmCritChance;
    @Getter @Setter private Integer armorCmCritPower;
    @Getter @Setter private Integer armorCrTypeFemale;
    @Getter @Setter private Integer armorCrTypeFemale2;
    @Getter @Setter private Integer armorCrTypeMale;
    @Getter @Setter private Integer armorCrTypeMale2;
    @Getter @Setter private Integer armorCrTypeMale3;
    @Getter @Setter private Integer armorCrTypeMale4;
    @Getter @Setter private Integer armorDrElectr;
    @Getter @Setter private Integer armorDrEmp;
    @Getter @Setter private Integer armorDrExplode;
    @Getter @Setter private Integer armorDrFire;
    @Getter @Setter private Integer armorDrLaser;
    @Getter @Setter private Integer armorDrNormal;
    @Getter @Setter private Integer armorDrPlasma;
    @Getter @Setter private Integer armorDtElectr;
    @Getter @Setter private Integer armorDtEmp;
    @Getter @Setter private Integer armorDtExplode;
    @Getter @Setter private Integer armorDtFire;
    @Getter @Setter private Integer armorDtLaser;
    @Getter @Setter private Integer armorDtNormal;
    @Getter @Setter private Integer armorDtPlasma;
    @Getter @Setter private Integer armorPerk;

    //  blueprint (type = 20)
    @Getter @Setter private Integer blueprintId;
    @Getter @Setter private Integer blueprintLevel;
    @Getter @Setter private Integer blueprintParam;
    @Getter @Setter private Integer blueprintType;

    //  car (type = 13)
    @Getter @Setter private String blockLines;
    @Getter @Setter private Integer carCrittersCapacity;
    @Getter @Setter private Integer carDeteriorationRate;
    @Getter @Setter private Integer carEntrance;
    @Getter @Setter private Integer carFuelConsumption;
    @Getter @Setter private Integer carMaxDeterioration;
    @Getter @Setter private Integer carMovementType;
    @Getter @Setter private Integer carPassability;
    @Getter @Setter private Integer carSpeed;
    @Getter @Setter private Integer carTankVolume;
    @Getter @Setter private String childLines0;
    @Getter @Setter private String childLines1;
    @Getter @Setter private Integer childPid0;
    @Getter @Setter private Integer childPid1;
    @Getter @Setter private Integer childPid2;

    //  container (type = ..many..)
    @Getter @Setter private Integer containerCannotPickUp;
    @Getter @Setter private Integer containerChangeble;
    @Getter @Setter private Integer containerMagicHandsGrnd;
    @Getter @Setter private Integer containerVolume;
    @Getter @Setter private Integer lockerCondition;

    //  door
    @Getter @Setter private Integer doorNoBlockLight;
    @Getter @Setter private Integer doorNoBlockMove;
    @Getter @Setter private Integer doorNoBlockShoot;

    //  drug
    @Getter @Setter private Integer ingredientType1;
    @Getter @Setter private Integer ingredientReturns1;

    //  grid
    @Getter @Setter private Integer gridType;

    //  misc
    @Getter @Setter private Integer holodiscNum;
    @Getter @Setter private Integer miscToolSkillBonus;
    @Getter @Setter private Integer miscToolSkillNum;
    @Getter @Setter private Integer radioBroadcastSend;
    @Getter @Setter private Integer radioChannel;
    @Getter @Setter private Integer revealSneakers;
    @Getter @Setter private Integer startValue2;
    @Getter @Setter private Integer startValue3;

    //  weapon
    @Getter @Setter private Integer weaponActiveUses;
    @Getter @Setter private String weaponAim0;
    @Getter @Setter private String weaponAim1;
    @Getter @Setter private Integer weaponApCost0;
    @Getter @Setter private Integer weaponApCost1;
    @Getter @Setter private String weaponCaliber;
    @Getter @Setter private Integer weaponCriticalFailure;
    @Getter @Setter private Integer weaponDefaultAmmoPid;
    @Getter @Setter private Integer weaponDmgMax0;
    @Getter @Setter private Integer weaponDmgMax1;
    @Getter @Setter private Integer weaponDmgMin0;
    @Getter @Setter private Integer weaponDmgMin1;
    @Getter @Setter private String weaponDmgType0;
    @Getter @Setter private String weaponDmgType1;
    @Getter @Setter private String weaponDmgType2;
    @Getter @Setter private Integer weaponEffect0;
    @Getter @Setter private Integer weaponEffect1;
    @Getter @Setter private String weaponIsUnarmed;
    @Getter @Setter private Integer weaponMaxAmmoCount;
    @Getter @Setter private Integer weaponMaxDist0;
    @Getter @Setter private Integer weaponMaxDist1;
    @Getter @Setter private Integer weaponMinStrength;
    @Getter @Setter private Integer weaponNoRunning;
    @Getter @Setter private String weaponPerk;
    @Getter @Setter private String weaponPicUse0;
    @Getter @Setter private String weaponPicUse1;
    @Getter @Setter private String weaponPicUse2;
    @Getter @Setter private String weaponRemove0;
    @Getter @Setter private String weaponRemove1;
    @Getter @Setter private Integer weaponRound0;
    @Getter @Setter private Integer weaponRound1;
    @Getter @Setter private String weaponSkill0;
    @Getter @Setter private String weaponSkill1;
    @Getter @Setter private String weaponSkill2;
    @Getter @Setter private Integer weaponSoundId0;
    @Getter @Setter private Integer weaponSoundId1;
    @Getter @Setter private Integer weaponUnarmedMinAgility;
    @Getter @Setter private Integer weaponUnarmedMinLevel;
    @Getter @Setter private Integer weaponUnarmedMinUnarmed;
    @Getter @Setter private Integer weaponUnarmedPriority;
    @Getter @Setter private String weaponUnarmedTree;

    public ItemProto() {
        protoId = 0;
        type = "N/A";
        picMap = "N/A";
        picInv = "N/A";
    }

    public boolean sameMapPic(ItemProto other) {
        return picMap.equalsIgnoreCase(other.getPicMap());
    }

    public boolean sameInventoryPic(ItemProto other) {
        return picInv.equalsIgnoreCase(other.getPicInv());
    }

    @Override
    public String toString() {
        return "Proto (" +
                "protoId=" + protoId +
                ", type=" + type +
                ", picMap='" + picMap + '\'' +
                ", picInv='" + picInv + '\'' +
                ", flags=" + flags +
                ", sound=" + soundId +
                ", material=" + material +
                ", disableEgg=" + disableEgg +
                ", lightDistance=" + lightDistance +
                ", lightIntensity=" + lightIntensity +
                ", LightColor=" + lightColor +
                ')';
    }

    @Override
    public int compareTo(ItemProto o) {
        return this.getProtoId() - o.getProtoId();
    }
}
