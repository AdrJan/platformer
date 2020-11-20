package com.adrjan.platformer.objects;

import com.adrjan.platformer.BufferedImageLoader;
import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;


public class Block extends GameObject {

    private static Random random = new Random();
    private int imgDrawNum;

    public Block(float x, float y, ObjectId id) {
        super(x, y, id);
        imgDrawNum = random.nextInt(25);
        if (imgDrawNum >= 4 || imgDrawNum == 0) imgDrawNum = 4;
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName(String.format("block%d.png", imgDrawNum)),
                (int) x, (int) y, 32, 32,
                null
        );
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
}
