package io.github.xenfork.gradle.utils.version.supers;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import io.github.xenfork.gradle.gson.GameAdapter;
import io.github.xenfork.gradle.utils.version.supers.supers.Game;

import java.util.ArrayList;

public class Arguments {
    @SerializedName("game")
    @JsonAdapter(GameAdapter.class)
    ArrayList<Game> game;

    @SerializedName("jvm")
    ArrayList<Object> jvm;
}
