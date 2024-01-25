package io.github.xenfork.xenmc.gradle.tasks;

import io.github.xenfork.xenmc.gradle.extensions.MinecraftExtension;
import io.github.xenfork.xenmc.gradle.extensions.impl.MinecraftExtensionImpl;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class XenonMCTask extends DefaultTask {
    public final MinecraftExtensionImpl minecraft;
    public XenonMCTask() {
        setGroup("xenon-mc");
        minecraft = (MinecraftExtensionImpl) getProject().getExtensions().getByType(MinecraftExtension.class);
    }

    @TaskAction
    public void run() {

    }
}
