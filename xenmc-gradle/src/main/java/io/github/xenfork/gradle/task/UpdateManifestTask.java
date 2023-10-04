package io.github.xenfork.gradle.task;

import static io.github.xenfork.gradle.XenMcGradle.*;

public class UpdateManifestTask extends ITask {

    @Override
    public void runTask() {
        download(downloadManifestUrl, destFile, "version_manifest.json");
    }
}
