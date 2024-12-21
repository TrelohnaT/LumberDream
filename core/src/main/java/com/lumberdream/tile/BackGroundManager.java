package com.lumberdream.tile;

import java.util.HashMap;
import java.util.Map;

public class BackGroundManager {

    private final int tileCountX;
    private final int tileCountY;

    private final Map<String, Tile> tileMap = new HashMap<>();


    public BackGroundManager(int tileCountX, int tileCountY) {
        this.tileCountX = tileCountX;
        this.tileCountY = tileCountY;




    }

    public void generateBackground(String kind, String atlasPath) {
        for(int y = 0; y < this.tileCountY; y++) {
            for(int x = 0; x < this.tileCountX; x++) {
                String tmp = kind + "_" + x + "_" + y;
                this.tileMap.put(tmp, new Grass(tmp,atlasPath, x, y));//"background/background.atlas"));

            }
        }

    }

    public Map<String, Tile> getTileMap() {
        return this.tileMap;
    }


}
