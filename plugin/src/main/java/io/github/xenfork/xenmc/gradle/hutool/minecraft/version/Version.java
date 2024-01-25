package io.github.xenfork.xenmc.gradle.hutool.minecraft.version;

import cn.hutool.core.annotation.Alias;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.assets.AssetIndex;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.game.Downloads;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries.Library;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.logging.Logging;

import java.util.ArrayList;
import java.util.Date;

public class Version {
    @Alias("arguments")
    public Arguments arguments;

    @Alias("assetIndex")
    public AssetIndex assetIndex;

    @Alias("assets")
    public String assets;

    @Alias("downloads")
    public Downloads downloads;

    @Alias("id")
    public String id;

    @Alias("javaVersion")
    public JavaVersion javaVersion;

    @Alias("libraries")
    public ArrayList<Library> libraries;

    @Alias("logging")
    public Logging logging;

    @Alias("mainClass")
    public String mainClass;

    @Alias("minimumLauncherVersion")
    public int minimumLauncherVersion;

    @Alias("releaseTime")
    public Date releaseTime;

    @Alias("time")
    public Date time;

    @Alias("type")
    public String type;
}
