package io.github.xenfork.xenmc.gradle.tasks.parse;

import cn.hutool.core.annotation.Alias;

import java.util.ArrayList;
import java.util.List;

public class ClassData {
    @Alias("name")
    public final String name;
    @Alias("obfname")
    public final String obfname;
    @Alias("fields")
    public final List<FieldData> fieldDataList = new ArrayList<>();
    @Alias("methods")
    public final List<MethodData> methodDataList = new ArrayList<>();

    public ClassData(String name, String obfname) {
        this.name = name;
        this.obfname = obfname;
    }
}
