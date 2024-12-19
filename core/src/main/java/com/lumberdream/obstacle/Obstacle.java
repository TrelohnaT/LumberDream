package com.lumberdream.obstacle;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public interface Obstacle {

    String getId();

    float getX();

    float getY();

    Sprite getSprite();

    Rectangle getHitBox();

    ShapeRenderer drawHitBox(ShapeRenderer sr);

    void load();

    void clear();


}
