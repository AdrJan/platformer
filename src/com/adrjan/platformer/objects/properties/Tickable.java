package com.adrjan.platformer.objects.properties;

import com.adrjan.platformer.objects.GameObject;

import java.util.LinkedList;

public interface Tickable {

    void tick(LinkedList<GameObject> object);
}
