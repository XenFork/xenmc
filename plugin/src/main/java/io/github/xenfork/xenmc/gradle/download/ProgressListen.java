package io.github.xenfork.xenmc.gradle.download;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.thread.AsyncUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.xenmc.gradle.api.XenMcOperatingSystem;
import io.github.xenfork.xenmc.gradle.extensions.MinecraftExtension;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.assets.AssetEntry;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.manifest.Manifest;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.Version;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries.Library;
import io.github.xenfork.xenmc.gradle.hutool.minecraft.version.libraries.Rule;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.file.Directory;
import org.gradle.api.logging.Logger;
import org.gradle.internal.os.OperatingSystem;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class ProgressListen implements StreamProgress {
    private static final String manifestUrl = "https://launchermeta.mojang.com/mc/game/version_manifest.json";
    private static final String librariesUrl = "https://libraries.minecraft.net/";
    private static final String resourcesUrl = "https://resources.download.minecraft.net/";

    public static final XenMcOperatingSystem xenMcOperatingSystem = new XenMcOperatingSystem(OperatingSystem.current());
    @NotNull
    private final String url;
    private final Logger logger;

    public ProgressListen(@NotNull Project target, @NotNull String url) {
        this.url = url;
        logger = target.getLogger();
    }

    @Override
    public void start() {
//        logger.lifecycle("Downloading: {}", url);
    }

    @Override
    public void progress(long total, long progressSize) {
    }

    @Override
    public void finish() {
        logger.lifecycle("Downloaded successfully: {}", url);
    }

    public static Manifest setupManifest(@NotNull Project target, MinecraftExtension minecraft, Directory targetDirectory) {
        File manifestFile = targetDirectory.file("version_manifest.json").getAsFile();
        if (!manifestFile.exists()) {
            download(manifestFile, manifestUrl, target);
        }
        Manifest manifest = JSONUtil
                .readJSON(manifestFile, StandardCharsets.UTF_8)
                .toBean(Manifest.class);
        boolean hasVersion = verifyManifest(minecraft, manifest);
        if (!hasVersion) {
            download(manifestFile, manifestUrl, target);
            manifest = JSONUtil
                    .readJSON(manifestFile, StandardCharsets.UTF_8)
                    .toBean(Manifest.class);
            hasVersion = verifyManifest(minecraft, manifest);
        }
        if (!hasVersion) {
            throw new RuntimeException(StrUtil.format("Couldn't find Minecraft version {}", minecraft.getVersion().get()));
        }

        return manifest;
    }

    public static Version downloadVersionJson(Project project, String mcVersion, Directory targetDirectory, Manifest manifest) {
        for (var manifestVersion : manifest.versions) {
            if (manifestVersion.id.equals(mcVersion)) {
                File versionFile = targetDirectory.file(manifestVersion.id + ".json").getAsFile();
                if (!versionFile.exists()) {
                    download(versionFile, manifestVersion.url, project);
                }
                return JSONUtil
                        .readJSON(versionFile, StandardCharsets.UTF_8)
                        .toBean(Version.class);
            }
        }
        throw new RuntimeException(StrUtil.format("The version {} doesn't exist in the versions_manifest.json", mcVersion));
    }

    public static void downloadGameDownload(Project project, Directory targetDirectory, Version version) {
        File client = targetDirectory.file(version.id + "-client.jar").getAsFile();
        File server = targetDirectory.file(version.id + "-server.jar").getAsFile();
        if (!client.exists()) download(client, version.downloads.client.url, project);
        if (!server.exists()) download(server, version.downloads.server.url, project);
    }

    public static void downloadGameMappingDownload(Project project, Directory targetDirectory, Version version) {
        File clientMapping = targetDirectory.file(version.id + "-client.mappings").getAsFile();
        File serverMapping = targetDirectory.file(version.id + "-server.mappings").getAsFile();
        if (!clientMapping.exists() || !verifySha1(clientMapping, version.downloads.client_mappings.sha1)) download(clientMapping, version.downloads.client_mappings.url, project);
        if (!serverMapping.exists() || !verifySha1(serverMapping, version.downloads.server_mappings.sha1)) download(serverMapping, version.downloads.server_mappings.url, project);
    }

    public static Map<String, AssetEntry> downloadAssetsIndexesDownload(Project project, Directory targetDirectory, Version version) {
        File indexes = targetDirectory.file(version.assets + ".json").getAsFile();
        if (!indexes.exists() || !verifySha1(indexes, version.assetIndex.sha1)) download(indexes, version.assetIndex.url, project);
        JSONObject json = JSONUtil.readJSONObject(indexes, StandardCharsets.UTF_8);

        return json.getJSONObject("objects").toBean(new TypeReference<>() {
        });
    }

    public static void downloadAssetsObjects(Project project, Directory targetDirectory, Map<String, AssetEntry> entryMap) {
        try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            List<List<Map.Entry<String, AssetEntry>>> partition = ListUtil.partition(entryMap.entrySet().stream().toList(), 100);
            for (List<Map.Entry<String, AssetEntry>> list : partition) {
                CompletableFuture<?>[] futures = new CompletableFuture[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    int finalI = i;
                    futures[i] = CompletableFuture.runAsync(() -> {
                        AssetEntry assetEntry = list.get(finalI).getValue();
                        String hash = assetEntry.hash;
                        String prefix = hash.substring(0, 2);
                        File assetFile = targetDirectory
                                        .dir(prefix)
                                        .file(hash).getAsFile();
                        String url = resourcesUrl + prefix + "/" + hash;

                        if (!assetFile.exists() || !verifySha1(assetFile, hash)) {
                            download(assetFile, url, project);
                        }
                    }, executor);
                }
                AsyncUtil.waitAll(futures);
            }
        }
    }

    public static void setupLibraries(Project project, Version version) {
        ArrayList<Library> libraries = version.libraries;
        RepositoryHandler repositories = project.getRepositories();
        DependencyHandler dependencies = project.getDependencies();
        repositories.maven(mvn -> {
            mvn.setUrl(librariesUrl);
            mvn.setName("minecraftLibraries");
        });
        for (Library library : libraries) {
            if (library.rules != null) {
                for (Rule rule : library.rules) {
                    if (Rule.ACTION_ALLOW.equals(rule.action) &&
                            rule.os.name != null &&
                            rule.os.name.equals(xenMcOperatingSystem.get())) {
                        dependencies.add("implementation", library.name);
                    }
                }
            } else {
                dependencies.add("implementation", library.name);
            }
        }
    }

    public static boolean verifyManifest(MinecraftExtension minecraft, Manifest manifest) {
        for (var version : manifest.versions) {
            if (version.id.equals(minecraft.getVersion().get())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean verifySha1(File verifyFile, String sha1) {
        return DigestUtil.sha1Hex(verifyFile).equalsIgnoreCase(sha1);
    }

    public static void download(File file, URL url, @NotNull Project project) {
        download(file, url.toString(), project);
    }

    public static void download(File file, String url, @NotNull Project project) {
        HttpUtil.download(url, FileUtil.getOutputStream(FileUtil.touch(file)), true, new ProgressListen(project, url));
    }
}
