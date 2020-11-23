package com.adrjan.platformer.objects;

import com.adrjan.platformer.*;
import com.adrjan.platformer.framework.ObjectId;

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

    private final Handler handler;
    private final Camera camera;

    private final Animations animationsIdle;
    private final Animations animationsJump;
    private final Animations animationsMove;

    private BufferedImage displayedImage;

    public Player(float x, float y, Handler handler, Camera camera, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.camera = camera;
        animationsIdle = new Animations(15);
        animationsIdle.setImages(
                "bald_idle1.png",
                "bald_idle2.png"
        );
        animationsJump = new Animations(60);
        animationsJump.setImages("bald_jump1.png");

        animationsMove = new Animations(10);
        animationsMove.setImages(
                "bald_move1.png",
                "bald_move2.png"
        );
    }

    public void tick(LinkedList<GameObject> object) {
        movement();
        Collision(object);
        animate();
    }

    public void animate() {
        if(this.isJumping())
            displayedImage = animationsJump.getImage();
        else if(this.velX != 0)
            displayedImage = animationsMove.getImage();
        else
            displayedImage = animationsIdle.getImage();

        if(velX < 0 || lastDir == Directions.LEFT)
            displayedImage = BufferedImageLoader.rotateImageHorizontally(displayedImage);
    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

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
                    x = tempObject.getX() - WIDTH;
                    velX = 0;
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + WIDTH;
                    velX = 0;
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
            velY += GameProperties.PLAYER_GRAVITY;

            if (velY > GameProperties.PLAYER_MAX_SPEED)
                velY = GameProperties.PLAYER_MAX_SPEED;
        }

        if (this.keyPressedD) {
            this.setVelX(this.approach(5, this.velX, 0.30f));
            this.setDir(Directions.RIGHT);
            this.lastDir = Directions.RIGHT;
            this.noKeyPressed = false;
        }
        if (this.keyPressedA) {
            this.setVelX(this.approach(-5, this.velX, 0.30f));
            this.setDir(Directions.LEFT);
            this.lastDir = Directions.LEFT;
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && !this.jumping) {
            y = y - 1;
            velY = -5;
            this.setJumping(true);
        }
        if (this.keyPressedW && this.keyPressedD) {
            this.setVelX(this.approach(5, this.velX, 0.30f));
            this.setDir(Directions.RIGHT);
            this.noKeyPressed = false;
        }
        if (this.keyPressedW && this.keyPressedA) {
            this.setVelX(this.approach(-5, this.velX, 0.30f));
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
        return new Rectangle((int) ((int) x + WIDTH - 5), (int) y + 5, (int) 5, (int) HEIGHT - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) WIDTH, (int) HEIGHT - 10);
    }

    public void setDir(Directions dir) {
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

    private void checkLifes() {
        if (--GameState.playerLife <= 0) {
            handler.removeObject(this);
            GameState.playerLife = 0;
        } else {
            camera.resetCamera();
            x = 64;
            y = 690;
        }
    }
}
