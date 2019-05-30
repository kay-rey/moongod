package moongod;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import static javax.imageio.ImageIO.read;

public class Moon extends GameObject {

    private final int R = 0;
    private int x, y, vx, vy, angle, width, height;
    private boolean visible;
    private boolean isStation = true;

    private BufferedImage moonImg;

    private int ROTATIONSPEED = 0;

    Moon() {
        x = getRandomNumberInRange(50, 1150);
        y = getRandomNumberInRange(50, 900);
        vx = getRandomNumberInRange(-2, 2);
        vy = getRandomNumberInRange(-2, 2);
        angle = 0;
        try {
            moonImg = read(new File("resources/moon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Moon(BufferedImage moonImg) {
        this.x = getRandomNumberInRange(50, 1150);
        this.y = getRandomNumberInRange(50, 900);
        this.angle = 0;
        this.moonImg = moonImg;
        this.vx = getRandomNumberInRange(-2, 2);
        this.vy = getRandomNumberInRange(-2, 2);
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void update() {
        this.rotate();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return this.moonImg.getWidth();
    }

    public int getHeight() {
        return this.moonImg.getHeight();
    }

    private void rotate() {
        this.angle += this.ROTATIONSPEED;
    }

    public Rectangle getRect() {
        return new Rectangle(x + 5, y + 5, moonImg.getWidth() - 10, moonImg.getHeight() - 10);
    }


    void drawImage(Graphics2D g) {
        AffineTransform asteroid = AffineTransform.getTranslateInstance(x, y);
        asteroid.rotate(Math.toRadians(angle), this.moonImg.getWidth() / 2, this.moonImg.getHeight() / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.moonImg, asteroid, null);
//        g.setColor(Color.blue);
//        g.drawRect(this.x + 5, this.y + 5, moonImg.getWidth()-10, moonImg.getHeight() - 10);
    }

}
