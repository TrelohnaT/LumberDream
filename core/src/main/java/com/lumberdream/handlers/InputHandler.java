package com.lumberdream.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;
import java.util.Map;

public class InputHandler {

    public static final String keyW = "w";
    public static final String keyA = "a";
    public static final String keyS = "s";
    public static final String keyD = "d";
    public static final String keySpace = "space";

    public static Map<String, Boolean> getState() {
        Map<String, Boolean> tmp = new HashMap<>();
        tmp.put(InputHandler.keyW, Gdx.input.isKeyPressed(Input.Keys.W));
        tmp.put(InputHandler.keyA, Gdx.input.isKeyPressed(Input.Keys.A));
        tmp.put(InputHandler.keyS, Gdx.input.isKeyPressed(Input.Keys.S));
        tmp.put(InputHandler.keyD, Gdx.input.isKeyPressed(Input.Keys.D));
        tmp.put(InputHandler.keySpace, Gdx.input.isKeyPressed(Input.Keys.SPACE));
        return tmp;
    }

}
