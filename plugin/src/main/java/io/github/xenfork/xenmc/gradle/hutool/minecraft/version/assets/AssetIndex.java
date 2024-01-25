package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.assets;

import cn.hutool.core.annotation.Alias;

import java.net.URL;

public class AssetIndex {
    @Alias("id")
    public String id;

    @Alias("sha1")
    public String sha1;

    @Alias("size")
    public long size;

    @Alias("totalSize")
    public long totalSize;

    @Alias("url")
    public URL url;
}
