package io.github.xenfork.gradle.utils.version.supers;

import com.google.gson.annotations.SerializedName;

import java.security.MessageDigest;

public class AssetIndex {
    @SerializedName("id")
    public String id;

    @SerializedName("sha1")
    public String sha1;
}
