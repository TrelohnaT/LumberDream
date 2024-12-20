package com.lumberdream;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lumberdream.entity.Entity;
import com.lumberdream.entity.Player;
import com.lumberdream.handlers.InputHandler;
import com.lumberdream.obstacle.Obstacle;
import com.lumberdream.obstacle.Tree;
import com.lumberdream.tile.Grass;
import com.lumberdream.tile.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main implements ApplicationListener {

    public static float timeElapsed = 0;

    private final Map<String, Entity> entityMap = new HashMap<>();
    private final Map<String, Tile> tileMap = new HashMap<>();
    private final Map<String, Obstacle> obstacleMap = new HashMap<>();
    ExtendViewport viewport;

    private SpriteBatch spriteBatch;

    Vector3 cameraVector = new Vector3();

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_NONE);
        viewport = new ExtendViewport(8, 6);
        // loading entities
        try {
            // init entities
            entityMap.put(
                "player",
                new Player(
                    "player",
                    "atlasPlayerMove/playerMove.atlas",
                    -1,
                    0,
                    1,
                    1
                )
            );

            // init tile (background)
            //ToDo this should be in some kind of handler which will unload tile which cannot be seen
            tileMap.put("grass00", new Grass("grass00", "background/background.atlas", 0, 0));
            tileMap.put("grass10", new Grass("grass10", "background/background.atlas", 1, 0));
            tileMap.put("grass01", new Grass("grass01", "background/background.atlas", 0, 1));
            tileMap.put("grass11", new Grass("grass11", "background/background.atlas", 1, 1));
            tileMap.put("grass20", new Grass("grass20", "background/background.atlas", 2, 0));
            tileMap.put("grass21", new Grass("grass21", "background/background.atlas", 2, 1));
            tileMap.put("grass02", new Grass("grass02", "background/background.atlas", 0, 2));
            tileMap.put("grass12", new Grass("grass12", "background/background.atlas", 1, 2));
            tileMap.put("grass22", new Grass("grass22", "background/background.atlas", 2, 2));

            obstacleMap.put(
                "tree",
                new Tree(
                    "tree",
                    "tree/Tree.atlas",
                    0,
                    0,
                    2,
                    2
                )
            );

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        timeElapsed += Gdx.graphics.getDeltaTime();
        Map<String, Boolean> inputState = InputHandler.getState();

        // update all entities
        this.entityMap.forEach((id, entity) -> entity.update(inputState));

        // camera follows player character
        Entity player = this.entityMap.get("player");
        cameraVector.x = player.getX();
        cameraVector.y = player.getY();

        // handle collision

        for(var entry : this.obstacleMap.entrySet()) {
            if(player.getHitBox().overlaps(entry.getValue().getHitBox())) {
                System.out.println("hit");
                player.hitObstacle();
            }
        }


        // alpha means speed which camera follow character
        viewport.getCamera().position.lerp(cameraVector, 0.1f);

        // Draw your application here.
        ScreenUtils.clear(Color.WHITE);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        // background will be draw first
        this.tileMap.forEach((id, tile) -> tile.getSprite().draw(spriteBatch));

        // draw entities on screen
        this.entityMap.forEach((id, entity) -> entity.getSprite().draw(spriteBatch));

        // obstacles will be draw last
        this.obstacleMap.forEach((id, obstacle) -> obstacle.getSprite().draw(spriteBatch));
        spriteBatch.end();


        // draw borders for textures
        // ToDo for debug reasons. Make way to disable this. (maybe some key press)
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0, 0, 1, 0));
        for (var entry : entityMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }

        sr.setColor(new Color(1, 0, 0, 0));
        for (var entry : obstacleMap.entrySet()) {
            Rectangle tmp = entry.getValue().getHitBox();
            sr.rect(tmp.x, tmp.y, tmp.width, tmp.height);
        }

        sr.end();

    }


    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        entityMap.forEach((id, entity) -> entity.clear());

    }

}
