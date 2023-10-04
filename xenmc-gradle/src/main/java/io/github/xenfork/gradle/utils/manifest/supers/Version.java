package io.github.xenfork.gradle.utils.manifest.supers;

import com.google.gson.annotations.SerializedName;

public class Version {
    @SerializedName("id")
    public String id;
    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String url;

    @SerializedName("time")
    public String time;

    @SerializedName("releaseTime")
    public String releaseTime;
}
