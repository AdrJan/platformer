package com.adrjan.platformer.objects;

import com.adrjan.platformer.BufferedImageLoader;
import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class Pavement extends GameObject {

    private int width = 16;
    private int height = 16;

    public Pavement(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName(String.format("pavement.png")),
                (int) x, (int) y, width, height,
                null
        );
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }
}
