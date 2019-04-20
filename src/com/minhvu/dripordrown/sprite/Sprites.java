package com.minhvu.dripordrown.sprite;

import com.minhvu.dripordrown.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Sprites {
    private HashMap<String, Sprite> sprites = new HashMap<>();

    public Sprites() {
        sprites.put("Bones", new Sprite(
                getBufferedImage("/PNG/Default size/Ships/bonesShip1.png"),
                getBufferedImage("/PNG/Default size/Ships/bonesShip2.png"),
                getBufferedImage("/PNG/Default size/Ships/bonesShip3.png"),
                getBufferedImage("/PNG/Default size/Ships/bonesShip4.png")));
        sprites.put("Cross", new Sprite(
                getBufferedImage("/PNG/Default size/Ships/crossShip1.png"),
                getBufferedImage("/PNG/Default size/Ships/crossShip2.png"),
                getBufferedImage("/PNG/Default size/Ships/crossShip3.png"),
                getBufferedImage("/PNG/Default size/Ships/crossShip4.png")));
        sprites.put("Horse", new Sprite(
                getBufferedImage("/PNG/Default size/Ships/horseShip1.png"),
                getBufferedImage("/PNG/Default size/Ships/horseShip2.png"),
                getBufferedImage("/PNG/Default size/Ships/horseShip3.png"),
                getBufferedImage("/PNG/Default size/Ships/horseShip4.png")));
        sprites.put("Skull", new Sprite(
                getBufferedImage("/PNG/Default size/Ships/skullShip1.png"),
                getBufferedImage("/PNG/Default size/Ships/skullShip2.png"),
                getBufferedImage("/PNG/Default size/Ships/skullShip3.png"),
                getBufferedImage("/PNG/Default size/Ships/skullShip4.png")));
        sprites.put("Sword", new Sprite(
                getBufferedImage("/PNG/Default size/Ships/swordShip1.png"),
                getBufferedImage("/PNG/Default size/Ships/swordShip2.png"),
                getBufferedImage("/PNG/Default size/Ships/swordShip3.png"),
                getBufferedImage("/PNG/Default size/Ships/swordShip4.png")));
    }

    public Sprite getRandomSprite() {
        Object[] sprites = this.sprites.values().toArray();
        return (Sprite) sprites[(int) (this.sprites.size() * Math.random())];
    }

    public Sprite getSprite(String name) {
        return sprites.get(name);
    }

    public static BufferedImage getBufferedImage(String file) {
        BufferedImage sprite = null;

        try {
            sprite = ImageIO.read(Game.getInstance().getClass().getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sprite;
    }
}