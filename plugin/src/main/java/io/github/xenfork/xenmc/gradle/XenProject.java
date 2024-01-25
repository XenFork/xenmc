package io.github.xenfork.xenmc.gradle;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import io.github.xenfork.xenmc.gradle.api.Info;
import io.github.xenfork.xenmc.gradle.download.ProgressListen;
import io.github.xenfork.xenmc.gradle.extensions.MinecraftExtension;
import io.github.xenfork.xenmc.gradle.extensions.impl.MinecraftExtensionImpl;
import io.github.xenfork.xenmc.gradle.tasks.XenonMCTask;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadAssetsTask;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadGamesTask;
import io.github.xenfork.xenmc.gradle.tasks.download.DownloadMappingsTask;
import io.github.xenfork.xenmc.gradle.tasks.download.intermediary.DownloadIntermediaryAssetTask;
import io.github.xenfork.xenmc.gradle.tasks.download.intermediary.DownloadIntermediaryGamesTask;
import io.github.xenfork.xenmc.gradle.tasks.download.intermediary.DownloadIntermediaryMappingsTask;
import io.github.xenfork.xenmc.gradle.tasks.parse.MappingParserTask;
import io.github.xenfork.xenmc.gradle.tasks.parse.intermediary.IntermediaryMappingParserTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

@SuppressWarnings("unused")
public final class XenProject implements Plugin<Project> {
    public MinecraftExtension minecraft;

    @Override
    public void apply(Project target) {
        minecraft = target.getExtensions().create(MinecraftExtension.class, "minecraft", MinecraftExtensionImpl.class, target);
        Info info = JSONUtil.parse(ResourceUtil.readUtf8Str("./info.json")).toBean(Info.class);

        target.getLogger().lifecycle("Xenon mc gradle version: {}", info.version);
        target.getLogger().lifecycle("Xenon mc authors:");
        for (String author : info.authors.split(",\\s*")) {
            target.getLogger().lifecycle(author);
        }
        target.getLogger().lifecycle("--------------");

        target.afterEvaluate(project -> {
            if (!minecraft.getVersion().isPresent()) {
                throw new RuntimeException("Minecraft version is not specified");
            }
            if (minecraft instanceof MinecraftExtensionImpl impl) {
                impl.manifest = ProgressListen.setupManifest(project, minecraft, minecraft.versions());
                impl.versions = ProgressListen.downloadVersionJson(project, minecraft.getVersion().get(), minecraft.versions(), impl.manifest);;
                for (String ext : impl.getExtended().get()) {
                    impl.getExtendedVersions().add(ProgressListen.downloadVersionJson(project, ext, minecraft.versions(), impl.manifest));
                }
                impl.versions.javaVersion.majorVersion = 21;
                ProgressListen.setupLibraries(project, impl.versions);
            }

            TaskContainer tasks = project.getTasks();
            setupDownload(tasks);
            setupIntermediary(tasks);
        });
    }

    public static void setupDownload(TaskContainer tasks) {
        DownloadGamesTask downloadGamesTask = tasks.create("downloadGames", DownloadGamesTask.class, t -> {
            t.setDescription("Download Minecraft games");
        });
        DownloadMappingsTask downloadMappingsTask = tasks.create("downloadMappings", DownloadMappingsTask.class, t -> {
            t.setDescription("Download Minecraft mappings");
        });
        DownloadAssetsTask downloadAssetsTask = tasks.create("downloadAssets", DownloadAssetsTask.class, t -> {
            t.setDescription("Download Minecraft assets");
        });
        MappingParserTask mappingParser = tasks.create("mappingParser", MappingParserTask.class);
        tasks.create("download", XenonMCTask.class, task -> {
            task.setGroup("build");
            downloadGamesTask.download();
            downloadMappingsTask.download();
            downloadAssetsTask.download();
            mappingParser.parse();
        }).run();
    }

    public static void setupIntermediary(TaskContainer tasks) {
        DownloadIntermediaryGamesTask intermediaryDownloadGames = tasks.create("intermediaryDownloadGames", DownloadIntermediaryGamesTask.class);
        DownloadIntermediaryMappingsTask intermediaryDownloadMappings = tasks.create("intermediaryDownloadMappings", DownloadIntermediaryMappingsTask.class);
        DownloadIntermediaryAssetTask intermediaryDownloadAssets = tasks.create("intermediaryDownloadAssets", DownloadIntermediaryAssetTask.class);
        IntermediaryMappingParserTask intermediaryMappingParser = tasks.create("intermediaryMappingParser", IntermediaryMappingParserTask.class);
        tasks.create("intermediary", XenonMCTask.class, task -> {
            task.setGroup("build");
            intermediaryDownloadGames.download();
            intermediaryDownloadMappings.download();
            intermediaryDownloadAssets.download();
            intermediaryMappingParser.parse();
        }).run();
    }
}
