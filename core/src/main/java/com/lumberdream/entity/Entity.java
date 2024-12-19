package com.lumberdream.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Map;

public interface Entity {
    String getId();

    float getX();

    float getY();

    Sprite getSprite();

    void update(Map<String, Boolean> inputMap);

    void load();

    void clear();
}
