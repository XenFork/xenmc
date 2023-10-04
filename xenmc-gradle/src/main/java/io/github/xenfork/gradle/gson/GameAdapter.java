package io.github.xenfork.gradle.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.github.xenfork.gradle.utils.version.supers.supers.Game;
import io.github.xenfork.gradle.utils.version.supers.supers.supers.Rules;
import io.github.xenfork.gradle.utils.version.supers.supers.supers.supers.Features;

import java.io.IOException;
import java.util.ArrayList;

public class GameAdapter extends TypeAdapter<Game> {
    @Override
    public void write(JsonWriter out, Game value) throws IOException {
        out.name("game");
        out.beginArray();
        for (String s : value.values) {
            out.value(s);
        }

        for (Rules rule : value.rules) {
            out.beginObject()
                    .name("rules")
                    .beginArray()
                    .beginObject()
                    .name("action")
                    .value(rule.action)
                    .name("features")
                    .beginObject()
                    .name(rule.features.key)
                    .value(rule.features.value)
                    .endObject()
                    .endObject()
                    .endArray();
            if (rule.value.size() == 1) {
                out.name("value").value(rule.value.get(0));
            } else {
                out.name("value").beginArray();
                for (String s : rule.value) {
                    out.value(s);
                }
                out.endArray();
            }
            out.endObject();
        }
        out.endArray();

    }

    @Override
    public Game read(JsonReader in) throws IOException {
        Game game = new Game();
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return game;
        }
        in.beginArray();
        int ri = -1;
        boolean is_features = false;
        while (in.hasNext()) {
            String name = in.nextName();
            if (in.peek() == JsonToken.STRING) {

                game.values = new ArrayList<>();
                game.values.add(in.nextString());

            } else {
                switch (name) {
                    case "rules" -> {
                        game.rules = new ArrayList<>();
                    }
                    case "action" -> {
                        ri++;
                        game.rules.add(ri, new Rules());
                        game.rules.get(ri).action = in.nextString();
                        game.rules.get(ri).features = new Features();
                    }
                    case "features" -> is_features = true;
                    case "value" -> {
                        Rules rules = game.rules.get(ri);
                        if (rules.value == null) {
                            rules.value = new ArrayList<>();
                        }
                        rules.value.add(in.nextString());
                    }
                    default -> {
                        if (is_features) {
                            game.rules.get(ri).features.key = name;
                            game.rules.get(ri).features.value = in.nextBoolean();
                        }
                    }
                }
            }

        }
        return game;
    }
}
