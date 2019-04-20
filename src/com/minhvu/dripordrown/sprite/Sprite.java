package com.minhvu.dripordrown.sprite;

import java.awt.image.BufferedImage;

public class Sprite {
    private BufferedImage newShipImage;
    private BufferedImage wornShipImage;
    private BufferedImage damagedShipImage;
    private BufferedImage burnedShipImage;

    public Sprite(BufferedImage newShipImage, BufferedImage wornShipImage, BufferedImage damagedShipImage, BufferedImage burnedShipImage) {
        this.newShipImage = newShipImage;
        this.wornShipImage = wornShipImage;
        this.damagedShipImage = damagedShipImage;
        this.burnedShipImage = burnedShipImage;
    }

    public BufferedImage getNewShipImage() {
        return newShipImage;
    }

    public BufferedImage getWornShipImage() {
        return wornShipImage;
    }

    public BufferedImage getDamagedShipImage() {
        return damagedShipImage;
    }

    public BufferedImage getBurnedShipImage() {
        return burnedShipImage;
    }
}
