package io.github.xenfork.gradle.task;

import io.github.xenfork.gradle.utils.manifest.ManifestGsonReader;

import java.io.IOException;

import static io.github.xenfork.gradle.XenMcGradle.*;
import static io.github.xenfork.gradle.utils.DownloadImpl.download;

public class UpdateManifestTask extends ITask {

    @Override
    public void runTask() {
        try {
            download(downloadManifestUrl, downloadFile, "version_manifest.json");
        } catch (IOException ignored) {}
    }
}
