package com.adrjan.platformer.objects.player;

import com.adrjan.platformer.configuration.GameConfig;
import com.adrjan.platformer.framework.Handler;
import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.framework.visual.Animations;
import com.adrjan.platformer.objects.Directions;
import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.pick_ups.Coin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Player extends GameObject  {

    private final float WIDTH = 128;
    private final float HEIGHT = 128;

    Directions dir = Directions.NO_DIR;
    Directions lastDir = Directions.RIGHT;

    public boolean noKeyPressed = true;
    private boolean keyPressedA = false;
    private boolean keyPressedD = false;
    private boolean keyPressedW = false;

    private final Animations animationsIdle;
    private final Animations animationsJump;
    private final Animations animationsMove;

    private BufferedImage displayedImage;

    protected boolean falling = true;
    protected boolean jumping = false;

    public Player(float x, float y, ObjectId id) {
        super(x, y, id);
        animationsIdle = new Animations(15);
        animationsIdle.setImages(
                "bald_idle1.png",
                "bald_idle2.png"
        );
        animationsJump = new Animations(60);
        animationsJump.setImages(
                "bald_jump1.png"
        );
        animationsMove = new Animations(10);
        animationsMove.setImages(
                "bald_move1.png",
                "bald_move2.png"
        );
    }

    public void tick(LinkedList<GameObject> objects) {
        move();
        collision(objects);
        animate();
    }

    public void animate() {
        if (this.isJumping())
            displayedImage = animationsJump.getImage();
        else if (this.velX != 0.0)
            displayedImage = animationsMove.getImage();
        else
            displayedImage = animationsIdle.getImage();

        if (velX < 0 || lastDir == Directions.LEFT)
            displayedImage = BufferedImageLoader.rotateImageHorizontally(displayedImage);
    }

    private void collision(LinkedList<GameObject> object) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = Handler.gameObjects.get(i);

            if (tempObject.getId() == ObjectId.Block) {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + (HEIGHT / 2);
                    velY = 0;
                }
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - HEIGHT + 1;
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
                    velX = 0;
                    x = tempObject.getX() + tempObject.getWidth();
                }
            }

            if (tempObject.getId() == ObjectId.Coin) {
                if (getBounds().intersects(tempObject.getBounds()) || getBoundsTop().intersects(tempObject.getBounds())) {
                    Coin coin = (Coin) tempObject;
                    coin.setTaken(true);
                }
            }
        }
    }

    private void move() {
        x += velX;
        y += velY;

        if (x <= 0) {
            velX = 0;
            x = 0;
        }

        if (falling || jumping) {
            velY += GameConfig.PLAYER_GRAVITY;

            if (velY > GameConfig.PLAYER_MAX_FALL_SPEED)
                velY = GameConfig.PLAYER_MAX_FALL_SPEED;
        }

        if (this.keyPressedD) {
            this.setVelX(this.approach(GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.RIGHT);
            this.lastDir = Directions.RIGHT;
            this.noKeyPressed = false;
        }
        if (this.keyPressedA) {
            this.setVelX(this.approach(-GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.LEFT);
            this.lastDir = Directions.LEFT;
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && !this.jumping) {
            y = y - 1;
            velY = -GameConfig.PLAYER_JUMP_SPEED;
            this.setJumping(true);
        }
        if (this.keyPressedW && this.keyPressedD) {
            this.setVelX(approach(GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.RIGHT);
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && this.keyPressedA) {
            this.setVelX(approach(-GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.LEFT);
            this.noKeyPressed = false;
        }
        if (!this.keyPressedA && !this.keyPressedD) {
            this.setVelX(this.approach(0, this.velX, 0.30f));
            this.setDir(Directions.NO_DIR);
            this.noKeyPressed = true;
        }
    }

    public void render(Graphics g) {
        g.drawImage(displayedImage, (int) x, (int) y, 128, 128, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(((int) ((int) x + (WIDTH / 4))), (int) ((int) y + (HEIGHT / 2)), (int) WIDTH / 2, (int) HEIGHT / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(((int) ((int) x + (WIDTH / 4))), (int) y, (int) WIDTH / 2, (int) HEIGHT / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int) x + WIDTH - 20), (int) y + 10, 5, (int) HEIGHT - 20);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x + 20, (int) y + 10, 5, (int) HEIGHT - 20);
    }

    public float approach(float G, float C, float diff) {
        float D = G - C;
        if (D > diff)
            return C + diff;
        if (D < -diff)
            return C - diff;
        return G;
    }

    public void setDir(Directions dir) {
        this.dir = dir;
    }

    public void setKeyPressedA(boolean keyPressedA) {
        this.keyPressedA = keyPressedA;
    }

    public void setKeyPressedD(boolean keyPressedD) {
        this.keyPressedD = keyPressedD;
    }

    public void setKeyPressedW(boolean keyPressedW) {
        this.keyPressedW = keyPressedW;
    }
}
