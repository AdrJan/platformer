package com.adrjan.platformer.framework.visual;

import com.adrjan.platformer.framework.Game;
import com.adrjan.platformer.objects.GameObject;

public class Camera {

    private static float x;
    private static float y;
    private static final int HEIGHT_MARGIN = 50;

    private Camera() {

    }

    public static void tick(Game game, GameObject player) {
        x = (player.getX() > game.windowWidth / 2) ? -player.getX() + game.windowWidth / 2 : x;
        y = (player.getY() < game.windowHeight + HEIGHT_MARGIN) ? -player.getY() + game.windowHeight / 2 : y;
    }

    public static void resetCamera() {
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
