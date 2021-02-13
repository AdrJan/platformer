package com.adrjan.platformer.objects.player;

import com.adrjan.platformer.configuration.GameConfig;
import com.adrjan.platformer.framework.Handler;
import com.adrjan.platformer.framework.data_loaders.BufferedImageLoader;
import com.adrjan.platformer.framework.visual.Animations;
import com.adrjan.platformer.objects.Directions;
import com.adrjan.platformer.objects.GameObject;
import com.adrjan.platformer.objects.ObjectId;
import com.adrjan.platformer.objects.pick_ups.Coin;
import com.adrjan.platformer.objects.properties.Animated;
import com.adrjan.platformer.objects.properties.Movable;
import com.adrjan.platformer.objects.properties.Renderable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Player extends GameObject implements Renderable, Animated, Movable {

    private static final int WIDTH = 128;
    private static final int HEIGHT = 128;
    private static final int BORDER_WIDTH = 20;

    Directions dir = Directions.NO_DIR;
    Directions lastDir = Directions.RIGHT;

    private boolean keyPressedA = false;
    private boolean keyPressedD = false;
    private boolean keyPressedW = false;

    private final Animations animationsIdle;
    private final Animations animationsJump;
    private final Animations animationsMove;

    private BufferedImage displayedImage;

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

    public void tick(List<GameObject> objects) {
        move();
        collide(objects);
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

    private void collide(List<GameObject> object) {
        for (int i = 0; i < object.size(); i++) {
            GameObject otherObject = Handler.gameObjects.get(i);
            switch (otherObject.getId()) {
                case Block:
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
                    break;
                case Coin:
                    if(getBounds().intersects(otherObject.getBounds())) {
                        Coin coin = (Coin) otherObject;
                        coin.setTaken(true);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void move() {
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
        if (this.keyPressedW && !this.jumping) {
            velY -= GameConfig.PLAYER_JUMP_SPEED;
            this.jumping = true;
        }
        if (this.keyPressedD) {
            this.setVelX(this.approach(GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.RIGHT);
            this.lastDir = Directions.RIGHT;
        }
        if (this.keyPressedA) {
            this.setVelX(this.approach(-GameConfig.PLAYER_SPEED, this.velX, 0.30f));
            this.setDir(Directions.LEFT);
            this.lastDir = Directions.LEFT;
        }
        if (!this.keyPressedA && !this.keyPressedD) {
            this.setVelX(this.approach(0, this.velX, 0.30f));
            this.setDir(Directions.NO_DIR);
        }
    }

    public void render(Graphics g) {
        g.drawImage(displayedImage, (int) x, (int) y, 128, 128, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(
                (int) x,
                (int) y,
                WIDTH,
                HEIGHT
        );
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(
                (int) (x + BORDER_WIDTH),
                (int) (y + HEIGHT - BORDER_WIDTH),
                WIDTH - 2 * BORDER_WIDTH,
                BORDER_WIDTH
        );
    }

    public Rectangle getBoundsTop() {
        return new Rectangle(
                (int) (x + BORDER_WIDTH),
                (int) y,
                WIDTH - BORDER_WIDTH * 2,
                BORDER_WIDTH
        );
    }

    public Rectangle getBoundsRight() {
        return new Rectangle(
                (int) (x + WIDTH - BORDER_WIDTH),
                (int) (y + BORDER_WIDTH),
                BORDER_WIDTH,
                HEIGHT - 2 * BORDER_WIDTH
        );
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle(
                (int) x,
                (int) (y + BORDER_WIDTH),
                BORDER_WIDTH,
                HEIGHT - BORDER_WIDTH * 2
        );
    }

    public float approach(float goalVel, float currVel, float diff) {
        float d = goalVel - currVel;
        if (d > diff)
            return currVel + diff;
        if (d < -diff)
            return currVel - diff;
        return goalVel;
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
