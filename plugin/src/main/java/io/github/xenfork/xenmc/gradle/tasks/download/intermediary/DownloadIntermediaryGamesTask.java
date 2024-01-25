package io.github.xenfork.xenmc.gradle.tasks.download.intermediary;

import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadGamesTask;
import org.gradle.api.tasks.TaskAction;

public class DownloadIntermediaryGamesTask extends DownloadGamesTask {
    @TaskAction
    @Override
    public void download() {
        for (Version version : minecraft.getExtendedVersions().get()) {
            ProgressListen.downloadGameDownload(getProject(), minecraft.getGame(), version);
        }
    }
}
