package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class Proto implements Comparable<Proto>{

    //  these are important for comparison and reference
    @Getter @Setter private Integer protoId;
    @Getter @Setter private String sourceFile;
    @Getter @Setter private String picMap;
    @Getter @Setter private String picInv;

    //  less important ones for now, only for cleaner warnings in logs

    //  generic
    @Getter @Setter private Integer cost;
    @Getter @Setter private Integer craftLevel;
    @Getter @Setter private String deteriorable;
    @Getter @Setter private String disableEgg;
    @Getter @Setter private Integer flags;
    @Getter @Setter private Integer lightDistance;
    @Getter @Setter private Integer lightIntensity;
    @Getter @Setter private Integer lightColor;
    @Getter @Setter private String material;
    @Getter @Setter private Integer miscChargeMax;
    @Getter @Setter private Integer needBlueprint;
    @Getter @Setter private String scriptFunc;
    @Getter @Setter private String scriptModule;
    @Getter @Setter private Integer slot;
    @Getter @Setter private Integer soundId;
    @Getter @Setter private String stackable;
    @Getter @Setter private Integer startCount;
    @Getter @Setter private String type;
    @Getter @Setter private Integer volume;
    @Getter @Setter private Integer weight;

    //  ammo (type = 4)
    @Getter @Setter private Integer ammoAcMod;
    @Getter @Setter private Integer ammoCaliber;
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


    @Getter @Setter private Integer corner;
    @Getter @Setter private Integer offsetX;
    @Getter @Setter private Integer offsetY;
    @Getter @Setter private Integer spriteCut;
    @Getter @Setter private String weaponAnim1;
    @Getter @Setter private String weaponAnim2_0;
    @Getter @Setter private String weaponAnim2_1;

    @Getter @Setter private Integer animWaitBase;
    @Getter @Setter private Integer animWaitRndMin;
    @Getter @Setter private Integer animWaitRndMax;


    public Proto() {
        protoId = 0;
        type = "N/A";
        picMap = "N/A";
        picInv = "N/A";
    }

    public boolean sameMapPic(Proto other) {
        return picMap.equalsIgnoreCase(other.getPicMap());
    }

    public boolean sameInventoryPic(Proto other) {
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
    public int compareTo(Proto o) {
        return this.getProtoId() - o.getProtoId();
    }
}
