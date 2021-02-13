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

public class Player extends GameObject {

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

    protected boolean jumping = false;
    protected boolean falling = true;

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
        if (this.jumping)
            displayedImage = animationsJump.getImage();
        else if (Math.abs(this.velX) > GameConfig.PLAYER_MOVE_ANIMATION_VEL_X)
            displayedImage = animationsMove.getImage();
        else
            displayedImage = animationsIdle.getImage();

        if (velX < 0 || lastDir == Directions.LEFT)
            displayedImage = BufferedImageLoader.rotateImageHorizontally(displayedImage);
    }

    private void collision(LinkedList<GameObject> object) {
        for (int i = 0; i < object.size(); i++) {
            GameObject otherObject = Handler.gameObjects.get(i);

            if (otherObject.getId() == ObjectId.Block) {
                if (getBoundsTop().intersects(otherObject.getBounds())) {
                    y += velY + 2;
                    velY = 0;
                }
                if (getBoundsBottom().intersects(otherObject.getBounds())) {
                    y = otherObject.getY() - HEIGHT;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else
                    falling = true;
                if (getBoundsRight().intersects(otherObject.getBounds())) {
                    x = otherObject.getX() - WIDTH;
                    velX = 0;
                }
                if (getBoundsLeft().intersects(otherObject.getBounds())) {
                    x = otherObject.getX() + 64;
                    velX = 0;
                }
            }

            if (otherObject.getId() == ObjectId.Coin) {
                if (getBounds().intersects(otherObject.getBounds()) || getBoundsTop().intersects(otherObject.getBounds())) {
                    Coin coin = (Coin) otherObject;
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

        System.out.println(falling + " " + jumping + " " + velX + " " + x);

        if (falling || jumping) {
            velY += GameConfig.PLAYER_GRAVITY;
            if (velY > GameConfig.PLAYER_MAX_FALL_SPEED)
                velY = GameConfig.PLAYER_MAX_FALL_SPEED;
        }
        if (this.keyPressedW && !this.jumping) {
            velY -= GameConfig.PLAYER_JUMP_SPEED;
            this.jumping = true;
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
        if (!this.keyPressedA && !this.keyPressedD) {
            this.setVelX(this.approach(0, this.velX, 0.30f));
            this.setDir(Directions.NO_DIR);
            this.noKeyPressed = true;
        }
    }

    private int BORDER_WIDTH = 20;

    public void render(Graphics g) {
        g.drawImage(displayedImage, (int) x, (int) y, 128, 128, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(
                (int) x,
                (int) y,
                (int) WIDTH,
                (int) HEIGHT
        );
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(
                (int) x + BORDER_WIDTH,
                (int) (y + HEIGHT - BORDER_WIDTH),
                (int) WIDTH - 2 * BORDER_WIDTH,
                BORDER_WIDTH
        );
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(
                (int) x + BORDER_WIDTH,
                (int) y,
                (int) WIDTH - BORDER_WIDTH * 2,
                BORDER_WIDTH
        );
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(
                (int) (x + WIDTH - BORDER_WIDTH),
                (int) y + BORDER_WIDTH,
                BORDER_WIDTH,
                (int) HEIGHT - 2 *BORDER_WIDTH
        );
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(
                (int) x,
                (int) y + BORDER_WIDTH,
                BORDER_WIDTH,
                (int) HEIGHT - BORDER_WIDTH * 2
        );
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
