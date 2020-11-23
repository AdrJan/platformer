package com.adrjan.platformer.framework.visual;

import com.adrjan.platformer.framework.Game;
import com.adrjan.platformer.objects.GameObject;

public class Camera {

    private float x, y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void tick(GameObject player) {
        if (player.getX() > 400)
            x = -player.getX() + Game.WIDTH / 2;
        y = -300;
    }

    public void resetCamera() {
        x = 0;
        y = 0;
    }
}
