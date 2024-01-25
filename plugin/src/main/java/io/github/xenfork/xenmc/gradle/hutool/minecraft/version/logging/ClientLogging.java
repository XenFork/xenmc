package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.logging;

import cn.hutool.core.annotation.Alias;

public class ClientLogging {
    @Alias("argument")
    public String argument;

    @Alias("file")
    public ClientLoggingFile file;

    @Alias("type")
    public String type;
}
