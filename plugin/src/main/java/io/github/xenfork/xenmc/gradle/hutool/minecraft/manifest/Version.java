package io.github.xenfork.xenmc.gradle.hutool.minecraft.manifest;

import cn.hutool.core.annotation.Alias;

import java.net.URL;
import java.util.Date;

public class Version {
    @Alias("id")
    public String id;

    @Alias("type")
    public String type;

    @Alias("url")
    public URL url;

    @Alias("time")
    public Date time;

    @Alias("releaseTime")
    public Date releaseTime;

}
