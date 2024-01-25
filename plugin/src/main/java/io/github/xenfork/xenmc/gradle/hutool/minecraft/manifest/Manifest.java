package io.github.xenfork.xenmc.gradle.hutool.minecraft.manifest;

import cn.hutool.core.annotation.Alias;

import java.util.ArrayList;

public class Manifest {
    @Alias("latest")
    public Latest latest;
    @Alias("versions")
    public ArrayList<Version> versions;
}
