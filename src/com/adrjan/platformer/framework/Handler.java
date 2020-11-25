package com.adrjan.platformer.framework;

import com.adrjan.platformer.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    public static LinkedList<GameObject> gameObjects = new LinkedList<>();

    public static void tick() {
        for (int i = 0; i < gameObjects.size(); i++)
            gameObjects.get(i).tick(gameObjects);
    }

    public static void render(Graphics g) {
        for (GameObject gameObject : gameObjects)
            gameObject.render(g);
    }

    public static void addObject(GameObject object) {
        gameObjects.add(object);
    }

    public static void removeObject(GameObject object) {
        gameObjects.remove(object);
    }
}
