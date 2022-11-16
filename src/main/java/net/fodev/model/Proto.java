package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class Proto implements Comparable<Proto>{
    private Integer protoId;
    private Integer type;
    private String picMap;
    private String picInv;
    private Integer flags;
    private Integer sound;
    private Integer material;
    private Integer corner;
    private Integer disableEgg;
    private Integer lightDistance;
    private Integer lightIntensity;
    private Integer lightColor;
    private Integer offsetX;
    private Integer offsetY;
    private Integer weight;
    private String weaponAnim1;
    private String weaponAnim2_0;
    private String weaponAnim2_1;
    private Integer animWaitBase;
    private Integer animWaitRndMin;
    private Integer animWaitRndMax;
    private String scriptModule;
    private String scriptFunc;
    @Getter @Setter private Integer volume;
    @Getter @Setter private Integer cost;
    @Getter @Setter private Integer spriteCut;

    public Proto() {
        protoId = 0;
        type = 0;
        picMap = "N/A";
        picInv = "N/A";
        flags = 0;
        sound = 0;
        material = 0;
    }

    public Proto(Integer protoId, Integer type, String picMap, String picInv, Integer flags, Integer sound, Integer material) {
        this.protoId = protoId;
        this.type = type;
        this.picMap = picMap;
        this.picInv = picInv;
        this.flags = flags;
        this.sound = sound;
        this.material = material;
    }

    public boolean sameMapPic(Proto other) {
        if (picMap.equals(other.getPicMap())) {
            return true;
        } else {
            return false;
        }
    }

    public Integer getProtoId() {
        return protoId;
    }

    public void setProtoId(Integer protoId) {
        this.protoId = protoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPicMap() {
        return picMap;
    }

    public void setPicMap(String picMap) {
        this.picMap = picMap;
    }

    public String getPicInv() {
        return picInv;
    }

    public void setPicInv(String picInv) {
        this.picInv = picInv;
    }

    public Integer getFlags() {
        return flags;
    }

    public void setFlags(Integer flags) {
        this.flags = flags;
    }

    public Integer getSound() {
        return sound;
    }

    public void setSound(Integer sound) {
        this.sound = sound;
    }

    public Integer getMaterial() {
        return material;
    }

    public void setMaterial(Integer material) {
        this.material = material;
    }

    public Integer getCorner() {
        return corner;
    }

    public void setCorner(Integer corner) {
        this.corner = corner;
    }

    public Integer getDisableEgg() {
        return disableEgg;
    }

    public void setDisableEgg(Integer disableEgg) {
        this.disableEgg = disableEgg;
    }

    public Integer getLightDistance() {
        return lightDistance;
    }

    public void setLightDistance(Integer lightDistance) {
        this.lightDistance = lightDistance;
    }

    public Integer getLightIntensity() {
        return lightIntensity;
    }

    public void setLightIntensity(Integer lightIntensity) {
        this.lightIntensity = lightIntensity;
    }

    public Integer getLightColor() {
        return lightColor;
    }

    public void setLightColor(Integer lightColor) {
        this.lightColor = lightColor;
    }

    public Integer getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(Integer offsetX) {
        this.offsetX = offsetX;
    }

    public Integer getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(Integer offsetY) {
        this.offsetY = offsetY;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeaponAnim1() {
        return weaponAnim1;
    }

    public void setWeaponAnim1(String weaponAnim1) {
        this.weaponAnim1 = weaponAnim1;
    }

    public String getWeaponAnim2_0() {
        return weaponAnim2_0;
    }

    public void setWeaponAnim2_0(String weaponAnim2_0) {
        this.weaponAnim2_0 = weaponAnim2_0;
    }

    public String getWeaponAnim2_1() {
        return weaponAnim2_1;
    }

    public void setWeaponAnim2_1(String weaponAnim2_1) {
        this.weaponAnim2_1 = weaponAnim2_1;
    }

    public Integer getAnimWaitBase() {
        return animWaitBase;
    }

    public void setAnimWaitBase(Integer animWaitBase) {
        this.animWaitBase = animWaitBase;
    }

    public Integer getAnimWaitRndMin() {
        return animWaitRndMin;
    }

    public void setAnimWaitRndMin(Integer animWaitRndMin) {
        this.animWaitRndMin = animWaitRndMin;
    }

    public Integer getAnimWaitRndMax() {
        return animWaitRndMax;
    }

    public void setAnimWaitRndMax(Integer animWaitRndMax) {
        this.animWaitRndMax = animWaitRndMax;
    }

    public String getScriptModule() {
        return scriptModule;
    }

    public void setScriptModule(String scriptModule) {
        this.scriptModule = scriptModule;
    }

    public String getScriptFunc() {
        return scriptFunc;
    }

    public void setScriptFunc(String scriptFunc) {
        this.scriptFunc = scriptFunc;
    }

    @Override
    public String toString() {
        return "Proto (" +
                "protoId=" + protoId +
                ", type=" + type +
                ", picMap='" + picMap + '\'' +
                ", picInv='" + picInv + '\'' +
                ", flags=" + flags +
                ", sound=" + sound +
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
