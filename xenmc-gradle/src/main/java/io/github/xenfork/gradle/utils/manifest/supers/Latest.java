package io.github.xenfork.gradle.utils.manifest.supers;

import com.google.gson.annotations.SerializedName;

public class Latest {
    @SerializedName("release")
    private String release;
    @SerializedName("snapshot")
    private String snapsot;
}
