package com.adrjan.platformer.objects;

import com.adrjan.platformer.objects.properties.Physical;

public abstract class GameObject implements Physical {

    protected ObjectId id;
    protected float x;
    protected float y;
    protected float velX = 0;
    protected float velY = 0;
    protected boolean falling = true;
    protected boolean jumping = false;

    protected GameObject(float x, float y, ObjectId id) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public ObjectId getId() {
        return id;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
