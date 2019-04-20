package com.minhvu.dripordrown.entity;

import com.minhvu.dripordrown.Game;
import com.minhvu.dripordrown.map.Maps;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;

public class CannonBall {
    private Player source;
    private Point initial;
    private Point center;
    private int speed;
    private int range;
    private double angle;
    private int radius;
    private double damage;

    private boolean alive;

    private HashSet collisionValues;

    public CannonBall(Player source, Point center, double damage, int speed, int range, double angle) {
        this.source = source;
        this.center = center;
        initial = (Point) center.clone();
        this.damage = damage;
        this.speed = speed;
        this.range = range;
        this.angle = angle;
        radius = 5;

        alive = true;

        collisionValues = new HashSet<Integer>();

        collisionValues.add(Maps.passable);
        collisionValues.add(Maps.shootable);
    }

    public void paint(Graphics2D g2d) {
        AffineTransform transform = g2d.getTransform();
        g2d.rotate(angle, center.x, center.y);
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(center.x, center.y, radius * 2, radius * 2);
        g2d.setTransform(transform);
    }

    public void update() {
        center.setLocation(center.getX() + speed * Math.cos(angle), center.getY() + speed * Math.sin(angle));

        if (center.distance(initial) >= range || hasCollision()) {
            alive = false;
        }
    }

    public boolean hasCollision() {
        for (int i = 0; i < Game.getInstance().getMaps().getCurrentMap().getCollisionMap().length; ++i) {
            for (int j = 0; j < Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i].length; ++j) {
                if (!collisionValues.contains(Game.getInstance().getMaps().getCurrentMap().getCollisionMap()[i][j]) &&
                        getBounds().intersects(new Rectangle2D.Float(j * 64, i * 64, 64, 64))) {
                    return true;
                }
            }
        }

        for (Object object : Game.getInstance().getPlayers()) {
            Player player = (Player) object;

            if (!source.equals(player) && getBounds().intersects(player.getBounds())) {
                player.damage(damage);
                return true;
            }
        }

        return false;
    }

    public Rectangle getBounds() {
        return new Rectangle(center.x - radius, center.y - radius, radius * 2, radius * 2);
    }

    public boolean isAlive() {
        return alive;
    }
}
