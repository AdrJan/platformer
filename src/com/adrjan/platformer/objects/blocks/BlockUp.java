package com.adrjan.platformer.objects.blocks;

import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class BlockUp extends GameObject {

    private static final int WIDTH = 64;
    private static final int HEIGHT = 64;

    public BlockUp(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName(String.format("block2.png")),
                (int) x, (int) y, WIDTH, HEIGHT,
                null
        );
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, WIDTH, HEIGHT);
    }
}
