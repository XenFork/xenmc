package io.github.xenfork.xenmc.gradle.tasks.download;

import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.tasks.XenonMCTask;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.TaskAction;

public class DownloadMappingsTask extends XenonMCTask {

    @TaskAction
    public void download() {
        ProgressListen.downloadGameMappingDownload(getProject(), minecraft.getMappings(), minecraft.versions);
    }
}
