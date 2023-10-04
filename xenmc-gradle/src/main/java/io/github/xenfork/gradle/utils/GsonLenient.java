package io.github.xenfork.gradle.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

/**
 * @apiNote so beautiful
 */
public class GsonLenient {
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

    public static void rewrite(File file) throws IOException {
        BufferedReader json1 = new BufferedReader(new FileReader(file));
        String json = gson.toJson(gson.fromJson(json1, Object.class));
        json1.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.flush();
        bw.write(json);
        bw.close();
    }

    public static <T> T loadJson(File file, Class<T> tClass) throws IOException {
        try(BufferedReader json1 = new BufferedReader(new FileReader(file))) {
            return gson.fromJson(json1, tClass);
        }
    }
}
