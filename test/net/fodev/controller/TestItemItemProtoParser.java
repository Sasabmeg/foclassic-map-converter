package net.fodev.controller;


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

public class TestItemItemProtoParser {
    ItemProtoParser itemProtoParser = new ItemProtoParser();

    @BeforeEach
    public void Init() {
        itemProtoParser.setLogLevel("warning");
    }

    @Test
    public void parseFromFile_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("lastParse.log"));
        itemProtoParser.parseFromFile("resources/1.proto", "lastParse.log");
    }

    @Test
    public void compare_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("lastParse.log"));
        List<ItemProto> source = itemProtoParser.parseFromFile("resources/generic_source.proto", "lastParse.log");
        List<ItemProto> target = itemProtoParser.parseFromFile("resources/generic_target.proto", "lastParse.log");
        itemProtoParser.compareProtos(source, target, "lastParse.log");
    }

    @Test
    public void generateMapping_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("lastParse.log"));
        Files.deleteIfExists(Paths.get("generic.mapping"));
        List<ItemProto> source = itemProtoParser.parseFromFile("resources/generic_source.proto", "lastParse.log");
        List<ItemProto> target = itemProtoParser.parseFromFile("resources/generic_target.proto", "lastParse.log");
        Map<Integer, Integer> mapping = itemProtoParser.compareProtos(source, target, "lastParse.log");
        itemProtoParser.generateMapping(mapping, "generic.mapping", "lastParse.log");
    }

    @Test
    public void generateMappingFromMultipleFiles_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("out/lastParse.log"));
        Files.deleteIfExists(Paths.get("out/generic_and_ammo.mapping"));
        List<ItemProto> source_ammo = itemProtoParser.parseFromFile("resources/tlamk2/ammo.fopro", "out/lastParse.log");
        List<ItemProto> source_generic = itemProtoParser.parseFromFile("resources/tlamk2/generic.fopro", "out/lastParse.log");
        List<ItemProto> target_ammo = itemProtoParser.parseFromFile("resources/foclassic/ammo.fopro", "out/lastParse.log");
        List<ItemProto> target_generic = itemProtoParser.parseFromFile("resources/foclassic/generic.fopro", "out/lastParse.log");
        List<ItemProto> source = source_ammo;
        source.addAll(source_generic);
        List<ItemProto> target = target_ammo;
        target.addAll(target_generic);
        Map<Integer, Integer> mapping = itemProtoParser.compareProtos(source, target, "out/lastParse.log");
        itemProtoParser.generateMapping(mapping, "out/generic_and_ammo.mapping", "out/lastParse.log");
    }

    @Test
    public void parseFromMultipleFiles_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("out/lastParse.log"));
        Files.deleteIfExists(Paths.get("out/all_in_one_verbose.mapping"));
        List<String> sources = new ArrayList<>();
        sources.add("resources/tlamk2/ammo.fopro");
        sources.add("resources/tlamk2/animals.fopro");
        sources.add("resources/tlamk2/armor.fopro");
        sources.add("resources/tlamk2/book.fopro");
        sources.add("resources/tlamk2/car.fopro");
        sources.add("resources/tlamk2/container.fopro");
        sources.add("resources/tlamk2/door.fopro");
        sources.add("resources/tlamk2/drug.fopro");
        sources.add("resources/tlamk2/generic.fopro");
        sources.add("resources/tlamk2/grid.fopro");
        sources.add("resources/tlamk2/key.fopro");
        sources.add("resources/tlamk2/misc.fopro");
        sources.add("resources/tlamk2/wall.fopro");
        sources.add("resources/tlamk2/weapon.fopro");
        List<ItemProto> source_all_in_one = itemProtoParser.parseFromMultipleFiles(sources, "out/lastParse.log");

        List<String> targets = new ArrayList<>();
        targets.add("resources/foclassic/ammo.fopro");
        targets.add("resources/foclassic/armor.fopro");
        targets.add("resources/foclassic/blueprint.fopro");
        targets.add("resources/foclassic/car.fopro");
        targets.add("resources/foclassic/container.fopro");
        targets.add("resources/foclassic/door.fopro");
        targets.add("resources/foclassic/drug.fopro");
        targets.add("resources/foclassic/dynamic.fopro");
        targets.add("resources/foclassic/generic.fopro");
        targets.add("resources/foclassic/grid.fopro");
        targets.add("resources/foclassic/helmet.fopro");
        targets.add("resources/foclassic/key.fopro");
        targets.add("resources/foclassic/map_object.fopro");
        targets.add("resources/foclassic/misc.fopro");
        targets.add("resources/foclassic/movable_container.fopro");
        targets.add("resources/foclassic/smo.fopro");
        targets.add("resources/foclassic/spot.fopro");
        targets.add("resources/foclassic/transfer.fopro");
        targets.add("resources/foclassic/trigger.fopro");
        targets.add("resources/foclassic/wall.fopro");
        targets.add("resources/foclassic/weapon.fopro");
        List<ItemProto> target_all_in_one = itemProtoParser.parseFromMultipleFiles(targets, "out/lastParse.log");

        List<ItemProtoMapping> mapping = itemProtoParser.compareProtosVerbose(source_all_in_one, target_all_in_one, "out/lastParse.log");
        itemProtoParser.generateMappingVerboseToFile(mapping, "out/all_in_one_verbose.mapping", "out/lastParse.log");
    }


}
