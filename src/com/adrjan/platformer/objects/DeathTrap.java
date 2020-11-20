package com.adrjan.platformer.objects;

import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

public class DeathTrap extends GameObject {

    int height = 16;

    public DeathTrap(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        --height;
        if (height == 0) height = 16;
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Area a = new Area(new Rectangle((int) x, (int) y, 32, 32));
        Area a2 = new Area(new Ellipse2D.Double((int) x + height, (int) y - 8, 16, height));
        Area a3 = new Area(new Ellipse2D.Double((int) x + 16 - height, (int) y - 8, 16, height));

        g2d.setColor(Color.red);
        a.add(a2);
        a.subtract(a3);
        g2d.fill(a);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
}
