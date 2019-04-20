package com.minhvu.dripordrown.map;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Map {
    private BufferedImage image;
    private int[][] collisionMap;

    public Map(BufferedImage image, int[][] collisionMap) {
        this.image = image;
        this.collisionMap = collisionMap;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public int[][] getCollisionMap() {
        return collisionMap;
    }

    public Dimension getSize() {
        return new Dimension(collisionMap[0].length * 64, collisionMap.length * 64);
    }
}