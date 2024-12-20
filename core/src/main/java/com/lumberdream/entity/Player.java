package com.lumberdream.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.lumberdream.Main;
import com.lumberdream.handlers.InputHandler;

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
    private float previousX = 0;
    private float previousY = 0;
    private float sizeX = 1;
    private float sizeY = 1;
    private final float speed = 1.5f;


    private final String atlasPath;
    private TextureAtlas atlas;
    private String currentAnimation = idleAnimation;

    private Rectangle hitBox;

    public Player(
        String id,
        String atlasPath,
        float x,
        float y,
        float sizeX,
        float sizeY
    ) {
        this.id = id;
        this.atlasPath = atlasPath;
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.hitBox = new Rectangle(x, y, sizeX, sizeY);
        this.load();
    }

    public void update(Map<String, Boolean> inputMap) {

        this.previousX = x;
        this.previousY = y;

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
        } else {
            // move hitBox
            this.hitBox = new Rectangle(x, y, sizeX, sizeY);
        }
    }

    @Override
    public Sprite getSprite() {
        Animation<TextureRegion> animation = new Animation<>(1 / 15f, atlas.findRegions(currentAnimation));
        TextureRegion tr = animation.getKeyFrame(Main.timeElapsed, true);
        Sprite tmp = new Sprite(tr);
        tmp.setSize(1, 1);
        tmp.translateX(this.x);
        tmp.translateY(this.y);
        return tmp;
    }

    @Override
    public Rectangle getHitBox() {
        return this.hitBox;
    }

    @Override
    public void hitObstacle() {
        this.x = previousX;
        this.y = previousY;
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

    }

    @Override
    public void clear() {
        this.atlas.dispose();
    }
}
