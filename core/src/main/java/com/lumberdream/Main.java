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
import com.lumberdream.tile.BackGroundManager;
import com.lumberdream.utils.BackgroundConfig;
import com.lumberdream.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main implements ApplicationListener {

    public static float timeElapsed = 0;

    private final Map<String, Entity> entityMap = new HashMap<>();

    private final Map<String, Obstacle> obstacleMap = new HashMap<>();
    ExtendViewport viewport;

    private SpriteBatch spriteBatch;
    Vector3 cameraVector = new Vector3();

    private BackGroundManager backGroundManager;

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
                    5,
                    5,
                    1,
                    1
                )
            );

            obstacleMap.put(
                "tree",
                new Tree(
                    "tree",
                    "tree/Tree.atlas",
                    1,
                    1,
                    2,
                    2
                )
            );

        } catch (Exception e) {
            System.out.println("exception: " + e);
        }
        spriteBatch = new SpriteBatch();
        backGroundManager = new BackGroundManager(20, 10);
        backGroundManager.generateBackground("grass_bg", "background/background.atlas");

        System.out.println(Utils.getConfing(BackgroundConfig.path, BackgroundConfig.class).str);

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

        for (var entry : this.obstacleMap.entrySet()) {
            if (player.getHitBox().overlaps(entry.getValue().getHitBox())) {
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
        this.backGroundManager.getTileMap().forEach((id, tile) -> tile.getSprite().draw(spriteBatch));

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
