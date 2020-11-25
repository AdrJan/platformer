package com.adrjan.platformer.framework.visual;

import com.adrjan.platformer.framework.Game;
import com.adrjan.platformer.objects.GameObject;

public class Camera {

    static Camera camera;
    private static float x, y;

    public static Camera getCamera() {
        if (camera == null) {
            camera = new Camera();
        }
        return camera;
    }

    public static void tick(GameObject player) {
        if (player.getX() > 400)
            x = -player.getX() + Game.WIDTH / 2;
        y = -300;
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
