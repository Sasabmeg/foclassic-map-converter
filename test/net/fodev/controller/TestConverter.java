package net.fodev.controller;

import net.fodev.model.CritterProto;
import net.fodev.model.Fomap;
import net.fodev.model.ItemProto;
import net.fodev.model.ItemProtoMapping;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestConverter {
    Converter converter = new Converter();
    FomapParser fomapParser = new FomapParser();
    ItemProtoParser itemProtoParser = new ItemProtoParser();
    CritterProtoParser critterProtoParser =  new CritterProtoParser();

    @BeforeEach
    public void Init() {
        converter.setLogLevel("warn");
        fomapParser.setLogLevel("warn");
        itemProtoParser.setLogLevel("warn");

    }

    @Test
    public void convertFromVerboseFile_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("out/lastConversion.log"));
        Files.deleteIfExists(Paths.get("out/cath_level1_converted.fomap"));
        converter.mapFromFile("out/all_in_one_verbose.mapping", "out/lastConversion.log");
        converter.convertFile("resources/tlamk2/cath_level1.fomap", "out/cath_level1_converted.fomap", "out/lastConversion.log", "ignore", 0);
    }

    @Test
    public void convertFromVerboseFile_Test2_Valid() throws IOException {
        String map = "lf0_lvl_broken1";
        String input = "resources/tlamk2/maps/" + map + ".fomap";
        String outputIgnored = "out/" + map + "_ignored.fomap";
        String outputRemoved = "out/" + map + "_removed.fomap";

        Files.deleteIfExists(Paths.get("out/lastConversion.log"));
        Files.deleteIfExists(Paths.get("out/lastItemParse.log"));
        Files.deleteIfExists(Paths.get("out/generic_verbose.mapping"));
        Files.deleteIfExists(Paths.get(outputIgnored));
        Files.deleteIfExists(Paths.get(outputRemoved));

        //  Generic/Item proto mapping

        List<String> sources = new ArrayList<>();
        sources.add("resources/tlamk2/proto/items/ammo.fopro");
        sources.add("resources/tlamk2/proto/items/animals.fopro");
        sources.add("resources/tlamk2/proto/items/armor.fopro");
        sources.add("resources/tlamk2/proto/items/book.fopro");
        sources.add("resources/tlamk2/proto/items/car.fopro");
        sources.add("resources/tlamk2/proto/items/container.fopro");
        sources.add("resources/tlamk2/proto/items/door.fopro");
        sources.add("resources/tlamk2/proto/items/drug.fopro");
        sources.add("resources/tlamk2/proto/items/generic.fopro");
        sources.add("resources/tlamk2/proto/items/grid.fopro");
        sources.add("resources/tlamk2/proto/items/key.fopro");
        sources.add("resources/tlamk2/proto/items/misc.fopro");
        sources.add("resources/tlamk2/proto/items/wall.fopro");
        sources.add("resources/tlamk2/proto/items/weapon.fopro");
        List<ItemProto> source_all_in_one = itemProtoParser.parseFromMultipleFiles(sources, "out/lastParse.log");

        List<String> targets = new ArrayList<>();
        targets.add("resources/foclassic/proto/items/ammo.fopro");
        targets.add("resources/foclassic/proto/items/armor.fopro");
        targets.add("resources/foclassic/proto/items/blueprint.fopro");
        targets.add("resources/foclassic/proto/items/car.fopro");
        targets.add("resources/foclassic/proto/items/container.fopro");
        targets.add("resources/foclassic/proto/items/door.fopro");
        targets.add("resources/foclassic/proto/items/drug.fopro");
        targets.add("resources/foclassic/proto/items/dynamic.fopro");
        targets.add("resources/foclassic/proto/items/generic.fopro");
        targets.add("resources/foclassic/proto/items/grid.fopro");
        targets.add("resources/foclassic/proto/items/helmet.fopro");
        targets.add("resources/foclassic/proto/items/key.fopro");
        targets.add("resources/foclassic/proto/items/map_object.fopro");
        targets.add("resources/foclassic/proto/items/misc.fopro");
        targets.add("resources/foclassic/proto/items/movable_container.fopro");
        targets.add("resources/foclassic/proto/items/smo.fopro");
        targets.add("resources/foclassic/proto/items/spot.fopro");
        targets.add("resources/foclassic/proto/items/transfer.fopro");
        targets.add("resources/foclassic/proto/items/trigger.fopro");
        targets.add("resources/foclassic/proto/items/wall.fopro");
        targets.add("resources/foclassic/proto/items/weapon.fopro");
        List<ItemProto> target_all_in_one = itemProtoParser.parseFromMultipleFiles(targets, "out/lastParse.log");

        List<ItemProtoMapping> itemProtoMapping = itemProtoParser.compareProtosVerbose(source_all_in_one, target_all_in_one, "out/lastParse.log");
        itemProtoParser.generateMappingVerboseToFile(itemProtoMapping, "out/generic_verbose.mapping", "out/lastParse.log");
        converter.setGenericProtoMapping(itemProtoParser.generateMapping(itemProtoMapping));

        //  Critter Proto mapping

        String logFileName = "out/lastCritterParse.log";
        String outFoclassic = "out/all_foclassic_critters";
        String outTlamk2 = "out/all_tlamk2_critters";
        String outCritterMapping = "out/critter_proto.mapping";
        Files.deleteIfExists(Paths.get(logFileName));
        Files.deleteIfExists(Paths.get(outFoclassic));
        Files.deleteIfExists(Paths.get(outTlamk2));
        Files.deleteIfExists(Paths.get(outCritterMapping));
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

        Map<Integer, Integer> critterProtoMapping = critterProtoParser.generateProtoMapping(critterProtosTlamk2, critterProtosFoclassic, logFileName);
        critterProtoParser.printMappingToFile(critterProtoMapping, outCritterMapping);
        converter.setCritterProtoMapping(critterProtoMapping);

        //  Map parsing and conversion

        Fomap fomapIgnore = fomapParser.parseFromFile(input, "out/lastParse.log");
        converter.convertFomap(fomapIgnore, outputIgnored, "out/lastConversion.log", "ignore", 0);

        Fomap fomapRemove = fomapParser.parseFromFile(input, "out/lastParse.log");
        converter.convertFomap(fomapRemove, outputRemoved, "out/lastConversion.log", "remove", 0);
    }

}
