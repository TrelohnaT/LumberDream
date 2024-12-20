package com.lumberdream.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.Map;

public interface Entity {
    String getId();

    float getX();

    float getY();

    Sprite getSprite();

    Rectangle getHitBox();

    /**
     * If some obstacle is hit, jump to the before position
     */
    void hitObstacle();

    void update(Map<String, Boolean> inputMap);

    void load();

    void clear();
}
