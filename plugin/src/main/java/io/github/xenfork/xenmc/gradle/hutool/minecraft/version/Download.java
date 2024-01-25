package io.github.xenfork.xenmc.gradle.hutool.minecraft.version;

import cn.hutool.core.annotation.Alias;

public class Download {
    @Alias("sha1")
    public String sha1;

    @Alias("size")
    public long size;

    @Alias("url")
    public String url;
}
