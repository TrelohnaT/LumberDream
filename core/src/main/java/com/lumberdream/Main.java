package com.lumberdream;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.lumberdream.entity.Entity;
import com.lumberdream.entity.Player;
import com.lumberdream.handlers.InputHandler;
import com.lumberdream.tile.Grass;
import com.lumberdream.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main implements ApplicationListener {

    private Map<String, Entity> entityMap = new HashMap<>();
    private Map<String, Tile> tileMap = new HashMap<>();
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
            entityMap.put("player", new Player("player", "atlasPlayerMove/playerMove.atlas"));

            // init tile (background)
            //ToDo this should be in some kind of handler which will unload tile which cannot be seen
            tileMap.put("grass", new Grass("grass", "background/background.atlas", 0, 0));
            tileMap.put("grass1", new Grass("grass1", "background/background.atlas", 1, 0));
            tileMap.put("grass2", new Grass("grass2", "background/background.atlas", 2, 0));

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
        Map<String, Boolean> inputState = InputHandler.getState();

        // update all entities
        this.entityMap.forEach((id, entity) -> entity.update(inputState));

        // camera follows player character
        Entity player = this.entityMap.get("player");
        cameraVector.x = player.getX();
        cameraVector.y = player.getY();
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
        spriteBatch.end();


        // draw borders for textures
        // ToDo for debug reasons. Make way to disable this. (maybe some key press)
        ShapeRenderer sr = new ShapeRenderer();
        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(new Color(0, 0, 1, 0));
        this.entityMap.forEach((id, entity) -> {
            sr.rect(entity.getX(), entity.getY(), 1, 1);
        });
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
