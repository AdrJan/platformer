package com.adrjan.platformer.framework.visual;

import com.adrjan.platformer.framework.Game;
import com.adrjan.platformer.objects.GameObject;

public class Camera {

    private static float x, y;
    private static final int HEIGHT_MARGIN = 50;

    private Camera() {

    }

    public static void tick(GameObject player) {
        x = (player.getX() > Game.WIDTH / 2) ? -player.getX() + Game.WIDTH / 2 : x;
        y = (player.getY() < Game.HEIGHT + HEIGHT_MARGIN) ? -player.getY() + Game.HEIGHT / 2 : y;
    }

    public void resetCamera() {
        x = 0;
        y = 0;
    }

    public static float getX() {
        return x;
    }

    public static float getY() {
        return y;
    }
}
