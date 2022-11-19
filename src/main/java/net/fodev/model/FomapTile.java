package net.fodev.model;

import lombok.Getter;
import lombok.Setter;

public class FomapTile {
    @Getter @Setter private String type;
    @Getter @Setter private Integer hexX;
    @Getter @Setter private Integer hexY;
    @Getter @Setter private String file;

    @Override
    public String toString() {
        return String.format("%s       %-4d %-4d           %s", type, hexX, hexY, file);
    }
}
