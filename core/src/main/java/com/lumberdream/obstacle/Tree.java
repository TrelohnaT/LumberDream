package com.lumberdream.obstacle;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Tree implements Obstacle {
    private final String id;

    private String atlasPath;
    private float x = 0;
    private float y = 0;
    private float sizeX = 1;
    private float sizeY = 1;

    private Rectangle hitBox;


    private TextureAtlas atlas;

    public Tree(String id, String atlasPath, float x, float y, float sizeX, float sizeY) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // hit box will be where the trunk is
        this.hitBox = new Rectangle(x + sizeX/4, y, 1, 1);

        this.load();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public float getX() {
        return this.x;
    }

    @Override
    public float getY() {
        return this.y;
    }

    @Override
    public Sprite getSprite() {
        //ToDo grass will be animated, this is just place holder
        Sprite tmp = new Sprite(this.atlas.createSprite("oak"));
        tmp.setSize(this.sizeX, this.sizeY);
        tmp.translateX(this.x);
        tmp.translateY(this.y);
        return tmp;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public ShapeRenderer drawHitBox(ShapeRenderer sr) {
        sr.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        return sr;
    }

    @Override
    public void load() {
        this.atlas = new TextureAtlas(this.atlasPath);
    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}