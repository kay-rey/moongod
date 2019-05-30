package moongod;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static javax.imageio.ImageIO.read;

public class Asteroid extends GameObject {

    private int x, y, vx, vy, angle, width, height;
    private boolean visible;

    private final int R = 0;

    private BufferedImage asteroidImg;

    private ArrayList<Asteroid> asteroidArrayList = new ArrayList<>();

    private final int ROTATIONSPEED = 1;

    Asteroid() {
        this.x = getRandomNumberInRange(50, 1150);
        this.y = getRandomNumberInRange(50, 900);
        this.vx = getRandomNumberInRange(-2, 2);
        this.vy = getRandomNumberInRange(-2, 2);
        this.angle = 0;
        try {
            asteroidImg = read(new File("resources/asteroid.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Asteroid(BufferedImage asteroidImg) {
        this.x = getRandomNumberInRange(50, 1150);
        this.y = getRandomNumberInRange(50, 900);
        this.vx = getRandomNumberInRange(-2, 2);
        this.vy = getRandomNumberInRange(-2, 2);
        this.angle = 0;
        this.asteroidImg = asteroidImg;
    }

    Asteroid(BufferedImage asteroidImg, int x, int y) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.asteroidImg = asteroidImg;
        this.vx = getRandomNumberInRange(-2, 2);
        this.vy = getRandomNumberInRange(-2, 2);

    }


    public void update() {

        this.move();
        this.rotate();

    }

    public ArrayList<Asteroid> getAsteroidArrayList() {
        return this.asteroidArrayList;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.asteroidImg.getWidth();
    }

    public int getHeight() {
        return this.asteroidImg.getHeight();
    }

    private void rotate() {
        this.angle += this.ROTATIONSPEED;
    }


    private void move() {
        if (vx == 0 || vy == 0) {
            this.vx = getRandomNumberInRange(-2, 2);
            this.vy = getRandomNumberInRange(-2, 2);
        }

        x += vx;
        y += vy;

        checkWorldBorder();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    private void checkWorldBorder() {
        if (x < 0) {
            x = GameWorld.SCREEN_WIDTH - (this.asteroidImg.getWidth() / 2);
        }
        if (x >= GameWorld.SCREEN_WIDTH) {
            x = 0;
        }
        if (y < 0) {
            y = GameWorld.SCREEN_HEIGHT - (this.asteroidImg.getHeight() / 2);
        }
        if (y >= GameWorld.SCREEN_HEIGHT) {
            y = 0;
        }
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, asteroidImg.getWidth(), asteroidImg.getHeight());
    }


    void drawImage(Graphics2D g) {
        AffineTransform asteroid = AffineTransform.getTranslateInstance(x, y);
        asteroid.rotate(Math.toRadians(angle), this.asteroidImg.getWidth() / 2, this.asteroidImg.getHeight() / 2);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.asteroidImg, asteroid, null);
    }

}
