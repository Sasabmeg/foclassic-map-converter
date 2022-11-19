package net.fodev.controller;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFomapParser {
    FomapParser parser = new FomapParser();

    @Test
    public void parseFromFile_Simple_Valid() throws IOException {
        String logFileName = "out/lastFomapParse.log";
        Files.deleteIfExists(Paths.get(logFileName));
        //parser.parseFromFile("resources/tlamk2/maps/den.fomap", logFileName);
        parser.parseFromFile("resources/foclassic/maps/arena.fomap", "out/arena_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/barter_ground.fomap", "out/barter_ground_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/bos_lh_0.fomap", "out/bos_lh_0_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/broken.fomap", "out/broken._fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/broken_mine.fomap", "out/broken_mine_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/cath_enter.fomap", "out/cath_enter_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/den.fomap", "out/den_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/frisco.fomap", "out/frisco_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/gecko_power_plant.fomap", "out/gecko_power_plant_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/gecko_settlement.fomap", "out/gecko_settlement_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/hub.fomap", "out/hub_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/junktown.fomap", "out/junktown_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/klamath.fomap", "out/klamath_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/la_ady1.fomap", "out/la_ady1_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/la_gunrunner.fomap", "out/la_gunrunner_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/landersp_Gate.fomap", "out/landersp_Gate_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/modoc.fomap", "out/modoc_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/ncr.fomap", "out/ncr_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/redding.fomap", "out/redding_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/v13_level1.fomap", "out/v13_level1_fomapParse.log");
        parser.parseFromFile("resources/foclassic/maps/vcity.fomap", "out/vcity_fomapParse.log");
    }
}
