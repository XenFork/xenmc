package io.github.xenfork.gradle.task;

import io.github.xenfork.gradle.ext.Minecraft;
import io.github.xenfork.gradle.ext.XenMC;
import io.github.xenfork.gradle.i18n.I18n;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public abstract class ITask extends DefaultTask {
    protected final I18n i18n;
    protected final XenMC xenMC;
    protected final Minecraft minecraft;
    public ITask() {
        i18n = getProject().getExtensions().getByType(I18n.class);
        xenMC = getProject().getExtensions().getByType(XenMC.class);
        minecraft = getProject().getExtensions().getByType(Minecraft.class);
        setGroup("xenmc-gradle-task");
    }

    @TaskAction
    public abstract void runTask();
}
