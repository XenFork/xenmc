package io.github.xenfork.xenmc.gradle.extensions.impl;

import cn.hutool.core.util.StrUtil;
import io.github.xenfork.xenmc.gradle.extensions.MinecraftExtension;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.manifest.Manifest;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import org.gradle.api.Project;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

import java.util.ArrayList;

public class MinecraftExtensionImpl implements MinecraftExtension {
    private final Property<String> version;
    private final Property<Boolean> isLoader;
    private final ListProperty<String> extended;
    private final ListProperty<Version> extendedVersions;

    public Manifest manifest;
    public Version versions;
    private final Directory cachesDir, versionsDir, assetsDir, indexesDir, objectsDir, gameDir, mappingsDir;

    public MinecraftExtensionImpl(Project project) {
        this.version = project.getObjects().property(String.class).convention((String) null);
        extended = project.getObjects().listProperty(String.class).convention(new ArrayList<>());
        extendedVersions = project.getObjects().listProperty(Version.class).convention(new ArrayList<>());
        this.isLoader = project.getObjects().property(Boolean.class).convention(false);
        DirectoryProperty gradleUserHomeDir = project.getObjects().directoryProperty();
        gradleUserHomeDir.set(project.getGradle().getGradleUserHomeDir());
        cachesDir = gradleUserHomeDir.dir("xenmc-cache").get();
        versionsDir = cachesDir.dir("versions");
        assetsDir = cachesDir.dir("assets");
        indexesDir = assetsDir.dir("indexes");
        objectsDir = assetsDir.dir("objects");
        gameDir = cachesDir.dir("game");
        mappingsDir = cachesDir.dir("mappings");

        project.afterEvaluate(target ->
                target.getLogger()
                        .lifecycle(StrUtil.format("Using Minecraft {}", version.get())));
    }

    @Override
    public Property<String> getVersion() {
        return version;
    }

    @Override
    public Property<Boolean> isLoader() {
        return isLoader;
    }

    @Override
    public Directory cache() {
        return cachesDir;
    }

    @Override
    public Directory versions() {
        return versionsDir;
    }

    @Override
    public Directory assets() {
        return assetsDir;
    }

    @Override
    public ListProperty<String> getExtended() {
        return extended;
    }

    @Override
    public ListProperty<Version> getExtendedVersions() {
        return extendedVersions;
    }

    @Override
    public Directory getIndexes() {
        return indexesDir;
    }

    @Override
    public Directory getObjects() {
        return objectsDir;
    }

    @Override
    public Directory getGame() {
        return gameDir;
    }

    @Override
    public Directory getMappings() {
        return mappingsDir;
    }
}
