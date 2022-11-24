package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class ItemProtoMapping implements Comparable<ItemProtoMapping> {

    @Getter @Setter private Integer sourceProtoId;
    @Getter @Setter private Integer targetProtoId;
    @Getter @Setter private String picMap;
    @Getter @Setter private String picInv;
    @Getter @Setter private String sourceFile;
    @Getter @Setter private String targetFile;

    public ItemProtoMapping(Integer sourceProtoId, Integer targetProtoId, String picMap, String picInv, String sourceFile, String targetFile) {
        this.sourceProtoId = sourceProtoId;
        this.targetProtoId = targetProtoId;
        this.picMap = picMap;
        this.picInv = picInv;
        this.sourceFile = sourceFile;
        this.targetFile = targetFile;
    }

    @Override
    public String toString() {
        return "ProtoMapping (" +
                "sourceProtoId=" + sourceProtoId +
                ", targetProtoId=" + targetProtoId +
                ", picMap='" + picMap + '\'' +
                ", picInv='" + picInv + '\'' +
                ", targetProtoId='" + sourceFile + '\'' +
                ", targetProtoId='" + targetFile + '\'' +
                ')';
    }

    @Override
    public int compareTo(ItemProtoMapping o) {
        if (this.sourceProtoId - o.sourceProtoId != 0) {
            return this.sourceProtoId - o.sourceProtoId;
        } else {
            return this.targetProtoId - o.targetProtoId;
        }
    }
}
