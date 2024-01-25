package io.github.xenfork.xenmc.gradle.tasks.parse.intermediary;

import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.tasks.parse.MappingParserTask;

public class IntermediaryMappingParserTask extends MappingParserTask {
    @Override
    public void parse() {
        for (Version version : minecraft.getExtendedVersions().get()) {
            parse(version);
        }
    }
}
