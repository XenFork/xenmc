package io.github.xenfork.xenmc.gradle.tasks.download.intermediary;

import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadMappingsTask;
import org.gradle.api.tasks.TaskAction;

public class DownloadIntermediaryMappingsTask extends DownloadMappingsTask {
    @TaskAction
    @Override
    public void download() {
        for (Version version : minecraft.getExtendedVersions().get()) {
            ProgressListen.downloadGameMappingDownload(getProject(), minecraft.getMappings(), version);
        }
    }
}
