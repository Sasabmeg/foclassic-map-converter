package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class FomapTile {
    @Getter @Setter private String type;
    @Getter @Setter private Integer hexX;
    @Getter @Setter private Integer hexY;
    @Getter @Setter private Integer unknownParam1;
    @Getter @Setter private String file;

    @Override
    public String toString() {
        if ("tile".equalsIgnoreCase(type) || "roof".equalsIgnoreCase(type)) {
            return String.format("%s       %-4d %-4d           %s", type, hexX, hexY, file);
        } else {
            return String.format("%s     %-4d %-4d         %d %s", type, hexX, hexY, unknownParam1, file);
        }
    }
}
