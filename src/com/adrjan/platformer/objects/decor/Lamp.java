package com.adrjan.platformer.objects.decor;

import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.properties.Renderable;

import java.awt.*;

public class Lamp extends GameObject implements Renderable {

    private int width = 64;
    private int height = 192;

    public Lamp(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void render(Graphics g) {
        g.drawImage(
                BufferedImageLoader.getImageByName("lamp.png"),
                (int) x, (int) y, width, height,
                null
        );
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
