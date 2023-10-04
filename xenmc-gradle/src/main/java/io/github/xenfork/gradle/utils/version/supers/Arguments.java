package io.github.xenfork.gradle.utils.version.supers;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Arguments {
    @SerializedName("game")
    ArrayList<Object> game;

    @SerializedName("jvm")
    ArrayList<Object> jvm;
}
