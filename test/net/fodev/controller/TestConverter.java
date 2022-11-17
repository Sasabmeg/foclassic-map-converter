package net.fodev.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestConverter {
    Converter converter = new Converter();

    @BeforeEach
    public void Init() {
        converter.setLogLevel("warn");
    }

    @Test
    public void convertFromVerboseFile_Test_Valid() throws IOException {
        Files.deleteIfExists(Paths.get("out/lastConversion.log"));
        Files.deleteIfExists(Paths.get("out/cath_level1_converted.fomap"));
        converter.mapFromFile("out/all_in_one_verbose.mapping", "out/lastConversion.log");
        converter.convertFile("resources/tlamk2/cath_level1.fomap", "out/cath_level1_converted.fomap", "out/lastConversion.log");
    }

}
