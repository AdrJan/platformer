package com.adrjan.platformer.objects;

import com.adrjan.platformer.Handler;
import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Coin extends GameObject {

    private int countAnimY;
    Random rand = new Random();
    private boolean isTaken = false;
    int nPoints = 4;
    int[] xPoints = new int[nPoints];
    int[] yPoints = new int[nPoints];
    int scaleA = 0, scaleB = 12, scaleC = 16, scaleD = 0;

    private Handler handler;


    public Coin(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        countAnimY = rand.nextInt(10);
        this.handler = handler;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        // TODO Auto-generated method stub

        if (!isTaken()) {
            countAnimY++;

            if (countAnimY > 9)
                countAnimY = 0;
            if (countAnimY < 5) {
                y--;
            } else {
                y++;
            }
        } else {
            scaleA++;
            scaleB--;
            scaleC--;
            scaleD++;

            if (scaleA > 7) {
                Player.score += 10;
                handler.removeObject(this);
            }
        }
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean isTaken) {
        this.isTaken = isTaken;
    }

    @Override
    public void render(Graphics g) {
        xPoints[0] = (int) x + 6;
        yPoints[0] = (int) y + scaleA;
        xPoints[1] = (int) x + scaleB;
        yPoints[1] = (int) y + 8;
        xPoints[2] = (int) x + 6;
        yPoints[2] = (int) y + scaleC;
        xPoints[3] = (int) x + scaleD;
        yPoints[3] = (int) y + 8;
        g.setColor(Color.yellow);
        g.fillPolygon(xPoints, yPoints, nPoints);
    }

    @Override
    public Rectangle getBounds() {
        // TODO Auto-generated method stub
        return new Rectangle((int) x, (int) y, 12, 16);
    }
}
