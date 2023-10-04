package io.github.xenfork.gradle.utils.version;

import com.google.gson.annotations.SerializedName;
import io.github.xenfork.gradle.utils.version.supers.Arguments;
import io.github.xenfork.gradle.utils.version.supers.AssetIndex;

public class VersionGsonReader {
    @SerializedName("arguments")
    public Arguments arguments;

    @SerializedName("assetIndex")
    public AssetIndex assetIndex;
}
