package com.adrjan.platformer.objects.blocks;

import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Block extends GameObject {

    private int width = 64;
    private int height = 64;

    public Block(float x, float y, ObjectId id) {
        super(x, y, id);
        super.height = height;
        super.width = width;
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName(String.format("block1.png")),
                (int) x, (int) y, width, height,
                null
        );
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
