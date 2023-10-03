package io.github.xenfork.gradle.i18n;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.xenfork.gradle.ext.XenMC;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class I18n {

    public static final Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
    public static Map<String, String> defaultTranslate, lang = new HashMap<>();

    public static final String key = "assets/lang/";
    public String language = "en_us";
    public void init() {
        try(InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(  key + "zh_cn.json")) {
            if (resourceAsStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
                defaultTranslate = gson.fromJson(br, new TypeToken<>() {
                });
            }
        } catch (IOException ignored) {
            System.err.println("don,t find default language");
        }
        try(InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(key + language + ".json")) {
            if (resourceAsStream != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
                lang.putAll(gson.fromJson(br, new TypeToken<>() {
                }));
            }
        } catch (IOException ignored) {
            System.err.println("don,t find setting language");
        }
    }

    public String get(String key) {
        return lang.getOrDefault(key, key.replace("_", " "));
    }

    public String get(String... keys) {
        return Arrays.stream(keys).map(s -> lang.getOrDefault(s, s.replace("_", " "))).collect(Collectors.joining());
    }
}
