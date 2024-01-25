package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries;

import cn.hutool.core.annotation.Alias;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class Library {
    @Alias("downloads")
    public Downloads downloads;

    @Alias("name")
    public String name;

    @Alias("rules")
    @Nullable
    public ArrayList<Rule> rules;

}
