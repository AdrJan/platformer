package com.adrjan.platformer.objects;

import com.adrjan.platformer.BufferedImageLoader;
import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class BlockUp extends GameObject {

    private int width = 64;
    private int height = 64;

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
                (int) x, (int) y, width, height,
                null
        );
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
