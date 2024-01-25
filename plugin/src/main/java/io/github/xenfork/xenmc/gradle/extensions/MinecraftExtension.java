package io.github.xenfork.xenmc.gradle.extensions;

import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import org.gradle.api.file.Directory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public interface MinecraftExtension {
    String manifestUrl = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    String librariesUrl = "https://libraries.minecraft.net/";
    String resourcesUrl = "https://resources.download.minecraft.net/";
    Property<String> getVersion();
    Property<Boolean> isLoader();
    Directory cache();
    Directory versions();

    Directory assets();

    ListProperty<String> getExtended();

    ListProperty<Version> getExtendedVersions();

    Directory getIndexes();

    Directory getObjects();

    Directory getGame();

    Directory getMappings();
}
