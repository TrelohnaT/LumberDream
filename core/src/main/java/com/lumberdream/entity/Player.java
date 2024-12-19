package com.lumberdream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lumberdream.handlers.InputHandler;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Player implements Entity {

    private final String idleAnimation = "idle_animation";
    private final String frontWalk = "front_walk";
    private final String backWalk = "back_walk";
    private final String leftWalk = "left_walk";
    private final String rightWalk = "right_walk";

    private String id = "";
    private float x = 0; // not pixels but tiles
    private float y = 0; // not pixels but tiles
    private final float speed = 1.5f;
    private float textureHeight = 0;
    private float textureWidth = 0;

    private final float frameTime = 1 / 15f;
    private float elapseTime = 0;

    private final String atlasPath;
    private TextureAtlas atlas;
    private String currentAnimation = idleAnimation;

    public Player(String id, String atlasPath) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.load();

    }

    public void update(Map<String, Boolean> inputMap) {

        float deltaTime = Gdx.graphics.getDeltaTime();
        boolean idle = true;
        if (inputMap.get(InputHandler.keyW)) {
            this.y += this.speed * deltaTime;
            currentAnimation = backWalk;
            idle = false;
        } else if (inputMap.get(InputHandler.keyS)) {
            this.y += this.speed * deltaTime * (-1);
            currentAnimation = frontWalk;
            idle = false;
        }

        if (inputMap.get(InputHandler.keyA)) {
            this.x += this.speed * deltaTime * (-1);
            currentAnimation = leftWalk;
            idle = false;
        } else if (inputMap.get(InputHandler.keyD)) {
            this.x += this.speed * deltaTime;
            currentAnimation = rightWalk;
            idle = false;
        }

        // if no movement, switch to idle animation
        if (idle) {
            currentAnimation = idleAnimation;
        }

    }

    @Override
    public Sprite getSprite() {
        elapseTime += Gdx.graphics.getDeltaTime();
        Animation<TextureRegion> animation = new Animation<>(frameTime, atlas.findRegions(currentAnimation));
        TextureRegion tr = animation.getKeyFrame(elapseTime, true);
        Sprite tmp = new Sprite(tr);
        tmp.setSize(1, 1);
        tmp.translateX(this.x);
        tmp.translateY(this.y);
        return tmp;


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
    public void load() {
        this.atlas = new TextureAtlas(atlasPath);
        if (this.atlas.getRegions().size != 0) {
            this.textureHeight = this.atlas.getRegions().get(0).getTexture().getTextureData().getHeight();
            this.textureWidth = this.atlas.getRegions().get(0).getTexture().getTextureData().getWidth();
            System.out.println(this.textureHeight);
        } else {
            System.out.println("no textures present");
        }
    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}
