package com.adrjan.platformer.objects.blocks;

import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.properties.Renderable;

import java.awt.*;

public class Block extends GameObject implements Renderable {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;

    public Block(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }

    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName("block1.png"),
                (int) x, (int) y, WIDTH, HEIGHT,
                null
        );
    }
}
