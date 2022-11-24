package net.fodev.controller;

import net.fodev.model.CritterProto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestCritterProtoParser {
    CritterProtoParser critterProtoParser = new CritterProtoParser();

    @BeforeEach
    public void Init() {
        critterProtoParser.setLogLevel("warn");
    }

    @Test
    public void parseFromFile_Test_Valid() throws IOException {
        String out = "out/2238_converted";
        Files.deleteIfExists(Paths.get("out/lastParse.log"));
        Files.deleteIfExists(Paths.get(out));
        List<CritterProto> critters = critterProtoParser.parseFromFile("resources/foclassic/proto/critters/2238", "lastParse.log");
        critterProtoParser.printToFile(critters, out);
    }

    @Test
    public void parseFromFile_Multi_Valid() throws IOException {
        String logFileName = "out/lastCritterParse.log";
        String out = "out/all_critters";
        Files.deleteIfExists(Paths.get(logFileName));
        Files.deleteIfExists(Paths.get(out));
        String[] critters = {"aliens", "brahmins", "deathclaws", "dogs", "geckos", "ghouls", "insects", "mutants", "plants",
                "radscorpions", "rats", "robots", "bandits", "citizens", "encounter", "guards", "merchants", "slavers",
                "slaves", "tribals", "vips", "2238", "bounty", "companions", "strangers", "invalid", "dungeons",
                "crv_encounter", "docan_critters", "crv_guards", "quests", "mob_dynamic"};
        List<CritterProto> critterProtos = new ArrayList<>();
        for (String critter : critters) {
            critterProtos.addAll(critterProtoParser.parseFromFile("resources/foclassic/proto/critters/" + critter + "", logFileName));
        }
        critterProtos.sort(CritterProto::compareTo);
        critterProtoParser.printToFile(critterProtos, out);
    }

    @Test
    public void compare_Multi_Valid() throws IOException {
        String logFileName = "out/lastCritterParse.log";
        String outFoclassic = "out/all_foclassic_critters";
        String outTlamk2 = "out/all_tlamk2_critters";
        String outMapping = "out/critter_proto.mapping";
        Files.deleteIfExists(Paths.get(logFileName));
        Files.deleteIfExists(Paths.get(outFoclassic));
        Files.deleteIfExists(Paths.get(outTlamk2));
        Files.deleteIfExists(Paths.get(outMapping));
        String[] crittersFoclassic = {"aliens", "brahmins", "deathclaws", "dogs", "geckos", "ghouls", "insects", "mutants", "plants",
                "radscorpions", "rats", "robots", "bandits", "citizens", "encounter", "guards", "merchants", "slavers",
                "slaves", "tribals", "vips", "2238", "bounty", "companions", "strangers", "invalid", "dungeons",
                "crv_encounter", "docan_critters", "crv_guards", "quests", "mob_dynamic"};
        List<CritterProto> critterProtosFoclassic = new ArrayList<>();
        for (String critter : crittersFoclassic) {
            critterProtosFoclassic.addAll(critterProtoParser.parseFromFile("resources/foclassic/proto/critters/" + critter + "", logFileName));
        }
        critterProtosFoclassic.sort(CritterProto::compareTo);
        critterProtoParser.printToFile(critterProtosFoclassic, outFoclassic);

        String[] crittersTlamk2 = {"fallout2", "tla", "tlamk2", "tlamk2dex"};
        List<CritterProto> critterProtosTlamk2 = new ArrayList<>();
        for (String critter : crittersTlamk2) {
            critterProtosTlamk2.addAll(critterProtoParser.parseFromFile("resources/tlamk2/proto/critters/" + critter + ".fopro", logFileName));
        }
        critterProtosTlamk2.sort(CritterProto::compareTo);
        critterProtoParser.printToFile(critterProtosTlamk2, outTlamk2);

        Map<Integer, Integer> mapping = critterProtoParser.generateProtoMapping(critterProtosTlamk2, critterProtosFoclassic, logFileName);
        critterProtoParser.printMappingToFile(mapping, outMapping);

    }
}
