package com.adrjan.platformer.objects;

import com.adrjan.platformer.BufferedImageLoader;
import com.adrjan.platformer.Camera;
import com.adrjan.platformer.Handler;
import com.adrjan.platformer.framework.ObjectId;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject {

    private float width = 32, height = 64;
    private float gravity = 0.12f;

    private final float MAX_SPEED = 10.0f;
    public static int score = 0;
    Player.directions dir = Player.directions.NO_DIR;
    public boolean noKeyPressed = true;
    private boolean keyPressedA = false;
    private boolean keyPressedD = false;
    private boolean keyPressedW = false;

    public enum directions {
        LEFT, RIGHT, NO_DIR;
    }

    private Handler handler;
    private Camera camera;
    BufferedImage img;

    private int lifes = 3;

    public Player(float x, float y, Handler handler, Camera camera, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.camera = camera;
    }

    public void tick(LinkedList<GameObject> object) {
        movement();
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block) {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (height / 2);
                    velY = 0;
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height + 1;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else
                    falling = true;
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - width;
                    velX = 0;
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + width;
                    velX = 0;
                }
            }

            if (tempObject.getId() == ObjectId.EnemyTriangle) {

                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    checkLifes();
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    //this.velY = -3;
                }
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    //checkLifes();
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    //checkLifes();
                }
            }

            if (tempObject.getId() == ObjectId.Coin) {
                if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) {
                    Coin coin = (Coin) tempObject;
                    coin.setTaken(true);
                }
            }

            if (tempObject.getId() == ObjectId.DeathTrap) {
                if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) {
                    checkLifes();
                }
            }
        }
    }

    private void movement() {
        x += velX;
        y += velY;

        if (x <= 0) {
            velX = 0;
            x = 0;
        }
        if (y >= 920) {
            checkLifes();
        }

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED)
                velY = MAX_SPEED;
        }

        if (this.keyPressedD) {
            this.setVelX(this.approach(5, this.velX, 0.30f));
            this.setDir(Player.directions.RIGHT);
            this.noKeyPressed = false;
        }
        if (this.keyPressedA) {
            this.setVelX(this.approach(-5, this.velX, 0.30f));
            this.setDir(Player.directions.LEFT);
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && !this.jumping) {
            y = y - 1;
            velY = -5;
            this.setJumping(true);
        }
        if (this.keyPressedW && this.keyPressedD) {
            this.setVelX(this.approach(5, this.velX, 0.30f));
            this.setDir(Player.directions.RIGHT);
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && this.keyPressedA) {
            this.setVelX(this.approach(-5, this.velX, 0.30f));
            this.setDir(Player.directions.LEFT);
            this.noKeyPressed = false;
        }
        if (!this.keyPressedA && !this.keyPressedD) {
            this.setDir(Player.directions.NO_DIR);
            this.noKeyPressed = true;
        }
    }

    public void render(Graphics g) {
        g.drawImage(BufferedImageLoader.getImageByName("robo.gif"), (int) x, (int) y, 32, 64, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(((int) ((int) x + (width / 4))), (int) ((int) y + (height / 2)), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(((int) ((int) x + (width / 4))), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) width, (int) height - 10);
    }

    public void setDir(Player.directions dir) {
        this.dir = dir;
    }

    public boolean isNoKeyPressed() {
        return noKeyPressed;
    }

    public void setNoKeyPressed(boolean noKeyPressed) {
        this.noKeyPressed = noKeyPressed;
    }

    public boolean isKeyPressedA() {
        return keyPressedA;
    }

    public void setKeyPressedA(boolean keyPressedA) {
        this.keyPressedA = keyPressedA;
    }

    public boolean isKeyPressedD() {
        return keyPressedD;
    }

    public void setKeyPressedD(boolean keyPressedD) {
        this.keyPressedD = keyPressedD;
    }

    public boolean isKeyPressedW() {
        return keyPressedW;
    }

    public void setKeyPressedW(boolean keyPressedW) {
        this.keyPressedW = keyPressedW;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    private void checkLifes() {
        if (--lifes <= 0) {
            handler.removeObject(this);
            lifes = 0;
        } else {
            camera.resetCamera();
            x = 64;
            y = 690;
        }
    }
}
