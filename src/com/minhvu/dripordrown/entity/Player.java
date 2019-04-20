package com.minhvu.dripordrown.entity;

import com.minhvu.dripordrown.Game;
import com.minhvu.dripordrown.essentials.Position;
import com.minhvu.dripordrown.essentials.Scoreboard;
import com.minhvu.dripordrown.map.Maps;
import com.minhvu.dripordrown.sprite.Sprite;
import com.minhvu.dripordrown.sprite.Sprites;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player {
    private BufferedImage image;
    private Sprite sprite;

    private int keyAccelerate;
    private int keyDecelerate;
    private int keyTurnLeft;
    private int keyTurnRight;
    private int keyLeftCannons;
    private int keyRightCannons;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private Position location;
    private double angle;
    private double acceleration;
    private double speed;
    private double maxSpeed;
    private double turnSpeed;

    private double health;
    private double maxHealth;
    private double damage;
    private int reloadTime;
    private boolean leftReloading;
    private boolean rightReloading;

    private long regenTimer;
    private int regenTime;
    private int regen;

    private boolean alive;

    public Player(int keyAccelerate, int keyDecelerate, int keyTurnLeft, int keyTurnRight, int keyLeftCannons, int keyRightCannons) {
        this.keyAccelerate = keyAccelerate;
        this.keyDecelerate = keyDecelerate;
        this.keyTurnLeft = keyTurnLeft;
        this.keyTurnRight = keyTurnRight;
        this.keyLeftCannons = keyLeftCannons;
        this.keyRightCannons = keyRightCannons;

        sprite = new Sprites().getRandomSprite();

        image = sprite.getNewShipImage();

        location = new Position(100, 100);
        speed = 0;
        acceleration = 0.05;
        maxSpeed = 3;
        turnSpeed = 0.3;
        angle = 0;

        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;

        maxHealth = 100;
        health = maxHealth;

        damage = 10;
        reloadTime = 886;
        leftReloading = false;
        rightReloading = false;

        regenTimer = System.currentTimeMillis();
        regenTime = 500;
        regen = 1;

        alive = true;
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

//        HealthBar.paint(g2d, this);

        g2d.rotate(Math.toRadians(angle - 90), getCenter().x, getCenter().y);
        g2d.drawImage(image, location.getX(), location.getY(), Game.getInstance());
        g2d.setTransform(transform);
    }

    public void update() {
        if (upPressed) {
            speed += acceleration;

            if (speed > maxSpeed) {
                speed = maxSpeed;
            }
        }

        if (downPressed) {
            speed -= acceleration;

            if (speed < 0) {
                speed = 0;
            }
        }

        if (leftPressed) {
            angle -= turnSpeed * speed;
        }

        if (rightPressed) {
            angle += turnSpeed * speed;
        }

        // Update Health
        if (health < maxHealth && System.currentTimeMillis() - regenTimer > regenTime) {
            health += regen;
            regenTimer = System.currentTimeMillis();
        }

        if (health > maxHealth) {
            health = maxHealth;
        }

        location.x += speed * Math.cos(Math.toRadians(angle));
        location.y += speed * Math.sin(Math.toRadians(angle));
    }

    public void fireLeftCannons() {

    }

    public void fireRightCannons() {

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == keyAccelerate) {
            upPressed = false;
        }

        if (e.getKeyCode() == keyDecelerate) {
            downPressed = false;
        }

        if (e.getKeyCode() == keyTurnLeft) {
            leftPressed = false;
        }

        if (e.getKeyCode() == keyTurnRight) {
            rightPressed = false;
        }

        if (e.getKeyCode() == keyLeftCannons) {
            fireLeftCannons();
        }

        if (e.getKeyCode() == keyRightCannons) {
            fireRightCannons();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == keyAccelerate) {
            upPressed = true;
        }

        if (e.getKeyCode() == keyDecelerate) {
            downPressed = true;
        }

        if (e.getKeyCode() == keyTurnLeft) {
            leftPressed = true;
        }

        if (e.getKeyCode() == keyTurnRight) {
            rightPressed = true;
        }
    }

    private void reloadLeft() {
        if (!leftReloading) {
            leftReloading = true;

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            leftReloading = false;

                        }
                    }, reloadTime
            );
        }
    }

    private void reloadRight() {
        if (!rightReloading) {
            rightReloading = true;

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            rightReloading = false;

                        }
                    }, reloadTime
            );
        }
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void damage(double damage) {
        health -= damage;

        if (health <= 0) {
            image = sprite.getBurnedShipImage();
            Scoreboard.output();
            alive = false;
            Game.getInstance().setState(Game.State.menu);
        }
    }

    public Point getLocation() {
        return new Point(location.getX(), location.getY());
    }

    protected boolean hasCollision() {
        for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i) {
            for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j) {
                int value = Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j];

                if ((value == Maps.unpassable || value == Maps.shootable) && getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64))) {
                    return true;
                }
            }
        }

        return false;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Point getCenter() {
        return new Point(location.getX() + getDimensions().width / 2, location.getY() + getDimensions().height / 2);
    }

    protected Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getDimensions().width, getDimensions().height);
    }

    public boolean isAlive() {
        return alive;
    }
}

