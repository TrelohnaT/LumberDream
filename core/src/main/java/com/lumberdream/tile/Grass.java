package com.lumberdream.tile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Grass implements Tile{

    private final String id;
    private final int scale = 1;

    private final String atlasPath;
    private float x = 0;
    private float y = 0;

    private TextureAtlas atlas;

    public Grass(String id, String atlasPath) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.load();
    }

    public Grass(String id, String atlasPath, float x, float y) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.load();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public Sprite getSprite() {
        //ToDo grass will be animated, this is just place holder
        Sprite tmp = new Sprite(this.atlas.createSprite("grass_bg"));
        // grass tile was multiplied only 2x
        tmp.setSize(1, 1);
        tmp.translateX(this.x/scale);
        tmp.translateY(this.y/scale);
        return tmp;
    }

    @Override
    public void load() {
        this.atlas = new TextureAtlas(atlasPath);
    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}
