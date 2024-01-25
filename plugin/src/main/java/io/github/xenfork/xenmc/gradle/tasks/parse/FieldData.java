package io.github.xenfork.xenmc.gradle.tasks.parse;

import cn.hutool.core.annotation.Alias;

public class FieldData {
    @Alias("type")
    public final String type;
    @Alias("name")
    public final String name;
    @Alias("obfname")
    public final String obfname;

    public FieldData(String type, String name, String obfname) {
        this.type = type;
        this.name = name;
        this.obfname = obfname;
    }

}
