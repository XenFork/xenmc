package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.logging;

import cn.hutool.core.annotation.Alias;

import java.net.URL;

public class ClientLoggingFile {
    @Alias("id")
    public String id;
    @Alias("sha1")
    public String sha1;
    @Alias("size")
    public int size;

    @Alias("url")
    public URL url;
}
