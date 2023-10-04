package io.github.xenfork.gradle;

import cn.hutool.core.comparator.VersionComparator;
import cn.hutool.core.io.FileUtil;
import io.github.xenfork.gradle.ext.Minecraft;
import io.github.xenfork.gradle.ext.XenMC;
import io.github.xenfork.gradle.i18n.I18n;
import io.github.xenfork.gradle.impl.LoaderMode;
import io.github.xenfork.gradle.task.UpdateManifestTask;
import io.github.xenfork.gradle.utils.manifest.ManifestGsonReader;
import io.github.xenfork.gradle.utils.manifest.supers.Version;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.github.xenfork.gradle.utils.DownloadImpl.download;
import static io.github.xenfork.gradle.utils.GsonLenient.gson;

public class XenMcGradle implements Plugin<Project> {

    public static File downloadFile, downloadVersionFile;
    public static final String downloadManifestUrl = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    public static ManifestGsonReader reader;
    public static final VersionComparator versionComparator = VersionComparator.INSTANCE;
    @Override
    public void apply(@NotNull Project target) {
        I18n i18n = target.getExtensions().create("i18n", I18n.class);
        XenMC xenMC = target.getExtensions().create("xenmc", XenMC.class);

        Minecraft minecraft = target.getExtensions().create("minecraft", Minecraft.class);
        target.afterEvaluate(project -> {
            initFilesDir(project);
            i18n.init();// init i18n translate

            File manifestPath = downloadAndSettingsManifest(project);// download https://launchermeta.mojang.com/mc/game/version_manifest.json

            reader = gson.fromJson(new BufferedReader(FileUtil.getReader(manifestPath, StandardCharsets.UTF_8)), ManifestGsonReader.class);
            if (minecraft.version == null) {
                throw new IllegalStateException(i18n.get("set_minecraft_error"));
            }
            checkVersionToDownload(minecraft, reader);
            if (xenMC.loader.get()) {
                LoaderMode.isLoader(project, i18n, xenMC, minecraft);
            } else {
                LoaderMode.isModding(project, i18n, xenMC, minecraft);
            }
        });
    }

    private void checkVersionToDownload(Minecraft minecraft, ManifestGsonReader reader) {
        String minVersion = "", maxVersion = "";
        if (minecraft.version.contains("~")) {
            String[] split = minecraft.version.split("~", 2);
            for (int i = 0; i < split.length; i++) {
                if (i == 0) {
                    minVersion = split[0];
                } else {
                    maxVersion = split[1];
                }
            }
        }
        for (Version version : reader.versions) {
            if (versionComparator.compare(version.id, minVersion) >= 0 && (maxVersion.isEmpty() || versionComparator.compare(version.id, maxVersion) <= 0)) {
                String versionUrl = version.url;
                String[] split = versionUrl.split("/");
                String child = split[split.length - 1];
                File file = new File(downloadVersionFile, child);
                if (!file.exists()) {
                    try {
                       download(versionUrl, downloadVersionFile, child);
                    } catch (IOException ignored) {}
                }
            }

        }

    }

    /**
     * @apiNote pre-first step
     * @param project gradle project after evaluate
     */
    private void initFilesDir(Project project) {
        downloadFile = new File(project.getGradle().getGradleUserHomeDir(), "caches" + File.separator + "xenmc" + File.separator + "download");
        downloadVersionFile = new File(downloadFile, "versions");
        if (!downloadFile.exists())
            downloadFile.mkdirs();
        if (!downloadVersionFile.exists()) {
            downloadVersionFile.mkdirs();
        }
    }


    /**
     * @apiNote first step
     * @param project gradle project after evaluate
     */
    private File downloadAndSettingsManifest(Project project) {
        File manifest = new File(downloadFile, "version_manifest.json");
        if (!manifest.exists()) {
            try {
                return download(downloadManifestUrl, downloadFile, "version_manifest.json");
            } catch (IOException ignored) {}
        }
        project.getTasks().register("update_manifest", UpdateManifestTask.class);
        return manifest;
    }

}
