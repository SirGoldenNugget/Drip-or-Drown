package com.minhvu.dripordrown.entity;

import com.minhvu.dripordrown.Game;
import com.minhvu.dripordrown.essentials.HealthBar;
import com.minhvu.dripordrown.essentials.Position;
import com.minhvu.dripordrown.essentials.Scoreboard;
import com.minhvu.dripordrown.sprite.Sprite;
import com.minhvu.dripordrown.sprite.Sprites;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Player extends Entity {
    private Sprite sprite;

    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    private double angle;
    private double acceleration;
    private double maxSpeed;
    private double turnSpeed;

    private double damage;
    private int reloadTime;
    private boolean reloading;

    private long regenTimer;
    private int regenTime;
    private int regen;

    private boolean alive;

    public Player() {
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
        reloading = false;

        regenTimer = System.currentTimeMillis();
        regenTime = 500;
        regen = 1;

        alive = true;
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();

        HealthBar.paint(g2d, this);

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

    public void fireCannons() {

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_Q) {
            fireCannons();
        }

        if (e.getKeyCode() == KeyEvent.VK_E) {

        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            upPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            downPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftPressed = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    private void reload() {
        if (!reloading) {
            reloading = true;

            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            reloading = false;

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

    @Override
    public void damage(double damage) {
        health -= damage;

        if (health <= 0) {
            image = sprite.getBurnedShipImage();
            Scoreboard.output();
            alive = false;
            Game.getInstance().setState(Game.State.menu);
        }
    }

    @Override
    public Point getCenter() {
        return new Point(location.getX() + getDimensions().width / 2, location.getY() + getDimensions().height / 2);
    }

    @Override
    protected Dimension getDimensions() {
        return new Dimension(image.getWidth(Game.getInstance()), image.getHeight(Game.getInstance()));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(location.getX(), location.getY(), getDimensions().width, getDimensions().height);
    }

    public boolean isAlive() {
        return alive;
    }
}

