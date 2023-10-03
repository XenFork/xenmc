package io.github.xenfork.gradle;

import com.acgist.snail.Snail;
import com.acgist.snail.context.exception.DownloadException;
import com.acgist.snail.downloader.Downloader;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

import static com.acgist.snail.Snail.SnailBuilder.newBuilder;

public class XenMcGradle implements Plugin<Project> {

    public static final Snail snail =
            newBuilder()
                    //使用所有的协议 ftp hls http magnet torrent
                    .enableAllProtocol()
                    // 加载已有下载任务
                    .loadTask()
                    // 启动监听器
                    .application()
                    // 同步
                    .buildSync();

    @Override
    public void apply(@NotNull Project target) {

    }

    public static void download(String... urls) {
        try {
            for (String url : urls) snail.download(url);
        } catch (DownloadException e) {
            e.printStackTrace();
        }
        snail.lockDownload();
    }
}
