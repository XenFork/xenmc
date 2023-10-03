package io.github.xenfork.gradle;

//import com.acgist.snail.Snail;
//import com.acgist.snail.context.ProtocolContext;
//import com.acgist.snail.context.exception.DownloadException;
//import com.acgist.snail.downloader.Downloader;
//import com.acgist.snail.downloader.IDownloader;
//import com.acgist.snail.gui.event.adapter.MultifileEventAdapter;
//import com.acgist.snail.pojo.ITaskSession;
//import com.acgist.snail.pojo.message.ApplicationMessage;
//import com.acgist.snail.protocol.Protocol;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpUtil;
import io.github.xenfork.gradle.ext.Minecraft;
import io.github.xenfork.gradle.ext.XenMC;
import io.github.xenfork.gradle.i18n.I18n;
import io.github.xenfork.gradle.impl.LoaderMode;
import io.github.xenfork.gradle.utils.GsonLenient;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class XenMcGradle implements Plugin<Project> {
    @Override
    public void apply(@NotNull Project target) {
        I18n i18n = target.getExtensions().create("i18n", I18n.class);
        XenMC xenMC = target.getExtensions().create("xenmc", XenMC.class);
        Minecraft minecraft = target.getExtensions().create("minecraft", Minecraft.class);
        target.afterEvaluate(project -> {
            i18n.init();
            System.out.println(i18n.get("select_language_is", i18n.language));
            File destFile = new File(project.getGradle().getGradleUserHomeDir(), "caches" + File.separator + "xenmc" + File.separator + "download");
            if (!destFile.exists())
                destFile.mkdirs();
            downlaod("https://launchermeta.mojang.com/mc/game/version_manifest.json", destFile);
            try {
                GsonLenient.rewrite(new File(destFile, "version_manifest.json"));
            } catch (IOException ignored) {}
            if (minecraft.version == null) {
                throw new IllegalStateException(i18n.get("set_minecraft_error"));
            }
            if (xenMC.loader.get()) {
                LoaderMode.isLoader(project, i18n, xenMC, minecraft);
            }
        });
    }

    public static void downlaod(String url ,File destFile) {
        HttpUtil.downloadFile(url, destFile, new StreamProgress() {
            @Override
            public void start() {
                System.out.println("download -> " + url + " -> " + destFile);
            }

            @Override
            public void progress(long total, long progressSize) {

            }

            @Override
            public void finish() {
                System.out.println("download " + url + " done!");
            }
        });
    }

}
