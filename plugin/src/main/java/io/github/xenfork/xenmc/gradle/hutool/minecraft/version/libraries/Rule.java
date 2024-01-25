package io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries;

public class Rule {
    public static final String ACTION_ALLOW = "allow";
    public String action;
    public Os os;

    public static class Os {
        public static final String NAME_WINDOWS = "windows";
        public static final String NAME_LINUX = "linux";
        public static final String NAME_OSX = "osx";
        public String name;
    }
}
