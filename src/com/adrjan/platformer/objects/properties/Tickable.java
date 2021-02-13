package com.adrjan.platformer.objects.properties;

import com.adrjan.platformer.objects.GameObject;

import java.util.List;

public interface Tickable {

    void tick(List<GameObject> object);
}
