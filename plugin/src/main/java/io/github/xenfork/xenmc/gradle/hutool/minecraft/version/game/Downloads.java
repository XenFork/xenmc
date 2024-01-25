package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.game;

import cn.hutool.core.annotation.Alias;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Download;

public class Downloads {
    @Alias("client")
    public Download client;

    @Alias("client_mappings")
    public Download client_mappings;

    @Alias("server")
    public Download server;

    @Alias("server_mappings")
    public Download server_mappings;
}
