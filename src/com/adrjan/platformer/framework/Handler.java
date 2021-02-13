package com.adrjan.platformer.framework;

import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.properties.Renderable;
import com.adrjan.platformer.objects.properties.Tickable;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Handler {

    private Handler() {
        throw new UnsupportedOperationException();
    }

    public static List<GameObject> gameObjects = new LinkedList<>();

    public static void tick() {
        Tickable tickable;
        for (int i = 0; i < gameObjects.size(); i++)
            if(gameObjects.get(i) instanceof Tickable) {
                tickable = (Tickable) (gameObjects.get(i));
                tickable.tick(gameObjects);
            }
    }

    public static void render(Graphics g) {
        Renderable renderable;
        for (GameObject gameObject : gameObjects)
            if(gameObject instanceof Renderable) {
                renderable = (Renderable) gameObject;
                renderable.render(g);
            }
    }

    public static void addObject(GameObject object) {
        gameObjects.add(object);
    }

    public static void removeObject(GameObject object) {
        gameObjects.remove(object);
    }
}
