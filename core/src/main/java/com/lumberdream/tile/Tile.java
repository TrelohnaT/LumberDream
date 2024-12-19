package com.lumberdream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;

public interface Tile {

    String getId();
    float getX();
    float getY();

    Sprite getSprite();

    void load();
    void clear();

}
