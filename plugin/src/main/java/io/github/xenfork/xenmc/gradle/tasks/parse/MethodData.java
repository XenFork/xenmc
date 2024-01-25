package io.github.xenfork.xenmc.gradle.tasks.parse;

import cn.hutool.core.annotation.Alias;

public final class MethodData {
    @Alias("returnType")
    public final String returnType;
    @Alias("name")
    public final String name;
    @Alias("params")
    public final String params;
    @Alias("obfname")
    public final String obfname;

    public MethodData(String returnType, String name, String params, String obfname) {
        this.returnType = returnType;
        this.name = name;
        this.params = params;
        this.obfname = obfname;
    }
}
