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
                getBufferedImage("/ships/bonesShip1.png"),
                getBufferedImage("/ships/bonesShip2.png"),
                getBufferedImage("/ships/bonesShip3.png"),
                getBufferedImage("/ships/bonesShip4.png")));
        sprites.put("Cross", new Sprite(
                getBufferedImage("/ships/crossShip1.png"),
                getBufferedImage("/ships/crossShip2.png"),
                getBufferedImage("/ships/crossShip3.png"),
                getBufferedImage("/ships/crossShip4.png")));
        sprites.put("Horse", new Sprite(
                getBufferedImage("/ships/horseShip1.png"),
                getBufferedImage("/ships/horseShip2.png"),
                getBufferedImage("/ships/horseShip3.png"),
                getBufferedImage("/ships/horseShip4.png")));
        sprites.put("Skull", new Sprite(
                getBufferedImage("/ships/skullShip1.png"),
                getBufferedImage("/ships/skullShip2.png"),
                getBufferedImage("/ships/skullShip3.png"),
                getBufferedImage("/ships/skullShip4.png")));
        sprites.put("Sword", new Sprite(
                getBufferedImage("/ships/swordShip1.png"),
                getBufferedImage("/ships/swordShip2.png"),
                getBufferedImage("/ships/swordShip3.png"),
                getBufferedImage("/ships/swordShip4.png")));
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