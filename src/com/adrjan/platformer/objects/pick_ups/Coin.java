package com.adrjan.platformer.objects.pick_ups;

import com.adrjan.platformer.framework.Handler;
import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.framework.state.GameState;
import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.properties.Renderable;
import com.adrjan.platformer.objects.properties.Tickable;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class Coin extends GameObject implements Tickable, Renderable {

    static Random rand = new Random();

    private boolean isTaken = false;
    private int countAnimY;

    public Coin(float x, float y, ObjectId id) {
        super(x, y, id);
        countAnimY = rand.nextInt(10);
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean isTaken) {
        this.isTaken = isTaken;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 64, 64);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(BufferedImageLoader.getImageByName("bottle.png"), (int) x, (int) y, 64, 64, null);
    }

    @Override
    public void tick(List<GameObject> object) {
        if (!isTaken()) {
            if (++countAnimY > 8) {
                countAnimY = 0;
            } else if (countAnimY < 5) {
                y--;
            } else {
                y++;
            }
        } else {
            GameState.playerScore += 10;
            Handler.removeObject(this);
        }
    }
}
