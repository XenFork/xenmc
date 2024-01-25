package io.github.xenfork.xenmc.gradle.tasks.parse;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.tasks.XenonMCTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MappingParserTask extends XenonMCTask {
    private static final Pattern CLASS_NAME_MAPPING =
            Pattern.compile("^(?<name>[\\w+.$]+) -> (?<obfname>[\\w$]+):$");
    private static final Pattern FIELD_NAME_MAPPING =
            Pattern.compile("^(?<type>[\\w.$]+) (?<name>\\w+) -> (?<obfname>[\\w$]+)$");
    private static final Pattern METHOD_NAME_MAPPING =
            Pattern.compile("^(?<from>\\d+):(?<to>\\d+):(?<type>[\\w.$]+) (?<name><init>|<clinit>|\\w+)\\((?<params>[\\w.$,]+)\\) -> (?<obfname><init>|<clinit>|\\w+)$");



    @TaskAction
    public void parse() {
        parse(minecraft.versions);
    }

    protected void parse(Version version) {
        File clientMappings = minecraft.cache()
                .dir("mappings")
                .file(StrUtil.format("{}-client.mappings", version.id))
                .getAsFile();
        File clientMappingsJson = minecraft.cache()
                .dir("mappings").dir("json")
                .file(StrUtil.format("{}-client.json", version.id))
                .getAsFile();
        List<ClassData> classDataList = new ArrayList<>();
        AtomicReference<ClassData> currentClass = new AtomicReference<>();
        FileUtil.readUtf8String(clientMappings)
                .lines()
                .map(String::trim)
                .filter(s -> !s.startsWith("#"))
                .forEach(s -> {
                    Matcher classMather = CLASS_NAME_MAPPING.matcher(s);
                    if (classMather.matches()) {
                        ClassData data = currentClass.getAndSet(new ClassData(classMather.group("name"),
                                classMather.group("obfname")));
                        if (data != null) {
                            classDataList.add(data);
                        }
                    } else {
                        Matcher fieldMather = FIELD_NAME_MAPPING.matcher(s);
                        if (fieldMather.matches()) {
                            currentClass.get().fieldDataList.add(new FieldData(fieldMather.group("type"),
                                    fieldMather.group("name"),
                                    fieldMather.group("obfname")));
                        } else {
                            Matcher methodMatcher = METHOD_NAME_MAPPING.matcher(s);
                            if (methodMatcher.matches()) {
                                currentClass.get().methodDataList.add(new MethodData(methodMatcher.group("type"),
                                        methodMatcher.group("name"),
                                        methodMatcher.group("params"),
                                        methodMatcher.group("obfname")));
                            }
                        }
                    }
                });
        JSONUtil.parse(classDataList).write(FileUtil.getWriter(FileUtil.touch(clientMappingsJson), StandardCharsets.UTF_8, false));

        // 1. mojang mappings -> intermediate mappings
        // 2. intermediate mappings -> transformed name
        // 3. decompile
        // 4. replace obfuscated name with transformed name
        // 5. patch
        // 6. recompile

    }
}
