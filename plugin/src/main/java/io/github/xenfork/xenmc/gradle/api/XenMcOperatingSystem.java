package io.github.xenfork.xenmc.gradle.api;

import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries.Rule;
import org.gradle.internal.os.OperatingSystem;

public class XenMcOperatingSystem {
    public final OperatingSystem current;

    public XenMcOperatingSystem(OperatingSystem current) {
        this.current = current;
    }

    public String get() {
        if (current.isMacOsX()) {
            return Rule.Os.NAME_OSX;
        } else if (current.isLinux()) {
            return Rule.Os.NAME_LINUX;
        } else if (current.isWindows()) {
            return Rule.Os.NAME_WINDOWS;
        }
        return null;
    }
}
