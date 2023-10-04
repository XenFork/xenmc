package io.github.xenfork.gradle.utils.manifest.supers;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.xenfork.gradle.gson.DateAdapter;

import java.util.Date;

public class Version {
    @SerializedName("id")
    public String id;
    @SerializedName("type")
    public String type;

    @SerializedName("url")
    public String url;

    @SerializedName("time")
    @JsonAdapter(DateAdapter.class)
    public Date time;

    @SerializedName("releaseTime")
    @JsonAdapter(DateAdapter.class)
    public Date releaseTime;
}
