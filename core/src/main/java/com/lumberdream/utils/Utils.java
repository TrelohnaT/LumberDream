package com.lumberdream.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.nio.file.Path;

public class Utils {

    /**
     * Returns instance of particular config.
     * @param path path to config (Gdx.files.internal)
     * @param clazz class of said config.
     * @return instance
     * @param <T> idk.
     */
    public static <T> T getConfing(String path, Class<T> clazz) {
        JsonValue value = new JsonReader().parse(Gdx.files.internal(path));
        return new Json().fromJson(clazz,
            value.toJson(JsonWriter.OutputType.json));
    }

}
