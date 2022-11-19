package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Fomap {
    @Getter @Setter private Integer version;
    @Getter @Setter private Integer maxHexX;
    @Getter @Setter private Integer maxHexY;
    @Getter @Setter private Integer workHexX;
    @Getter @Setter private Integer workHexY;
    @Getter @Setter private String scriptModule;
    @Getter @Setter private String scriptFunc;
    @Getter @Setter private Integer noLogOut;
    @Getter @Setter private Integer time;
    @Getter @Setter private String dayTime;
    @Getter @Setter private String dayColor0;
    @Getter @Setter private String dayColor1;
    @Getter @Setter private String dayColor2;
    @Getter @Setter private String dayColor3;
    @Getter @Setter private List<FomapTile> tiles;
    @Getter @Setter private List<FomapObject> objects;

    public Fomap() {
        tiles = new ArrayList<>();
        objects = new ArrayList<>();
    }

    public void addTile(FomapTile tile) {
        tiles.add(tile);
    }

    public void addObject(FomapObject object) {
        objects.add(object);
    }

    public String getHeader() {
        String result = "[Header]\n";
        result += String.format("%-20s %d\n", "Version", version);
        result += String.format("%-20s %d\n", "MaxHexX", maxHexX);
        result += String.format("%-20s %d\n", "MaxHexY", maxHexY);
        result += String.format("%-20s %d\n", "WorkHexX", workHexX);
        result += String.format("%-20s %d\n", "WorkHexY", workHexY);
        result += String.format("%-20s %s\n", "ScriptModule", scriptModule);
        result += String.format("%-20s %s\n", "ScriptFunc", scriptFunc);
        result += String.format("%-20s %d\n", "NoLogOut", noLogOut);
        result += String.format("%-20s %d\n", "Time", time);
        result += String.format("%-20s %s\n", "DayTime", dayTime);
        result += String.format("%-20s %s\n", "DayColor0", dayColor0);
        result += String.format("%-20s %s\n", "DayColor1", dayColor1);
        result += String.format("%-20s %s\n", "DayColor2", dayColor2);
        result += String.format("%-20s %s\n", "DayColor3", dayColor3);
        return result;
    }
}
