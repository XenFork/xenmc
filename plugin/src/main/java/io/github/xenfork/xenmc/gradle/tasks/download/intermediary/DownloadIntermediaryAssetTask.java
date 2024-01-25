package io.github.xenfork.xenmc.gradle.tasks.download.intermediary;

import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.assets.AssetEntry;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.tasks.XenonMCTask;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadAssetsTask;
import org.gradle.api.tasks.TaskAction;

import java.util.Map;

public class DownloadIntermediaryAssetTask extends DownloadAssetsTask {
    @TaskAction
    @Override
    public void download() {
        for (Version version : minecraft.getExtendedVersions().get()) {
            Map<String, AssetEntry> indexes = ProgressListen.downloadAssetsIndexesDownload(getProject(), minecraft.getIndexes(), version);
            ProgressListen.downloadAssetsObjects(getProject(), minecraft.getObjects(), indexes);
        }
    }
}
