package io.github.xenfork.xenmc.gradle.tasks.download;

import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.assets.AssetEntry;
import io.github.xenfork.xenmc.gradle.tasks.XenonMCTask;
import org.gradle.api.file.Directory;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class DownloadAssetsTask extends XenonMCTask {
    @TaskAction
    public void download() {
        Map<String, AssetEntry> indexes = ProgressListen.downloadAssetsIndexesDownload(getProject(), minecraft.getIndexes(), minecraft.versions);
        ProgressListen.downloadAssetsObjects(getProject(), minecraft.getObjects(), indexes);

    }
}
