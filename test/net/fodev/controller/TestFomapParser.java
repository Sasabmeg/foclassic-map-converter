package net.fodev.controller;

import net.fodev.model.Fomap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFomapParser {
    FomapParser fomapParser = new FomapParser();

    @Test
    public void parseFromFile_Multi_Valid() throws IOException {
        String logFileName = "out/lastFomapParse.log";
        Files.deleteIfExists(Paths.get(logFileName));
        fomapParser.setLogLevel(0);
        //parser.parseFromFile("resources/tlamk2/maps/den.fomap", logFileName);
        String[] maps = {"arena", "barter_ground", "bos_lh_0", "broken", "broken_mine", "cath_enter", "den", "frisco", "gecko_power_plant",
                "gecko_settlement", "hub", "junktown", "klamath", "la_ady1", "la_gunrunner", "landersp_Gate", "modoc", "ncr",
                "redding", "v13_level1", "vcity"};
        for (String map : maps) {
            Files.deleteIfExists(Paths.get("out/" + map + "_1.fomap"));
            Fomap fomap = fomapParser.parseFromFile("resources/foclassic/maps/" + map + ".fomap", logFileName);
            fomapParser.fomapToFile(fomap, "out/" + map + "_1.fomap", logFileName);
        }
    }

    @Test
    public void parseFromFileAndOutput_Single_Valid() throws IOException {
        String logFileName = "out/lastFomapParse.log";
        String outFileName = "out/test_parsed.fomap";
        Files.deleteIfExists(Paths.get(logFileName));
        Files.deleteIfExists(Paths.get(outFileName));
        fomapParser.setLogLevel(1);

        Fomap fomap = fomapParser.parseFromFile("resources/foclassic/maps/test.fomap", "out/lastFomapParse.log");
        fomapParser.fomapToFile(fomap, outFileName, logFileName);

    }
}
