package io.github.xenfork.gradle.utils;

import cn.hutool.core.io.StreamProgress;
import cn.hutool.http.HttpUtil;

import java.io.File;
import java.io.IOException;

public class DownloadImpl {
    public static File download(String url, File path, String fileName) throws IOException {
        File hasFile = new File(path, fileName);
        download(url, path);
        GsonLenient.rewrite(hasFile);
        return hasFile;
    }

    private static void download(String url , File destFile) {
        HttpUtil.downloadFile(url, destFile, new StreamProgress() {
            @Override
            public void start() {
                String[] split = url.split("/");
                System.out.println("download -> " + url + " -> " + destFile + File.separator + split[split.length - 1]);
            }

            @Override
            public void progress(long total, long progressSize) {}

            @Override
            public void finish() {
                System.out.println("download " + url + " done!");
            }
        });
    }
}
