package io.github.xenfork.gradle.utils.manifest;

import com.google.gson.annotations.SerializedName;
import io.github.xenfork.gradle.utils.manifest.supers.Latest;
import io.github.xenfork.gradle.utils.manifest.supers.Version;

import java.util.ArrayList;

public class ManifestGsonReader {
    @SerializedName("latest")
    public Latest latest;

    @SerializedName("versions")
    public ArrayList<Version> versions;

}
