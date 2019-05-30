package moongod;


import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class Ship extends GameObject {


    public static final int TILE_SIZE = 1280 / 80;
    private final int R = 2;
    private final int ROTATIONSPEED = 1;
    int health = 1000;
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int oldX;
    private int oldY;
    private int angle;
    private int score = 0;
    private int speed = 1;
    private boolean isDocked = false;
    private boolean isLaunched;
    private boolean isOver = false;
    private BufferedImage tankImg;
    private BufferedImage tankMainImg;
    private Collision shipCollision = new Collision();

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    Ship(int x, int y, int vx, int vy, int angle, BufferedImage tankImg) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.tankImg = tankImg;
        this.tankMainImg = tankImg;
        this.isDocked = false;
        this.isLaunched = false;
    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }


    public void update() {
        oldX = x;
        oldY = y;
        shipCollision.playerVSObjects(GameWorld.ship);

        if (!isDocked) {    //always keep moving forward
            this.moveForwards();
            if (this.DownPressed) {
                this.slowDown();
            }
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }

        if (this.RightPressed) {
            this.rotateRight();
        }

        if (isDocked) { //if docked then press enter to launch from moon
            if (this.ShootPressed) {
                this.launch();
            }
        }

    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void slowDown() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle))) - speed;
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle))) - speed;
        x += vx;
        y += vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void launch() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
        isDocked = false;
        isLaunched = true;

    }

    public boolean getOver(){
        return this.isOver;
    }

    public void drawHealth(Graphics2D g2) { //changes color and size depending on health

        g2.setColor(Color.blue);

        if (health > 800) {
            g2.setColor(Color.blue);
        }
        else if (health > 600){
            g2.setColor(Color.green);
        }
        else if (health > 400){
            g2.setColor(Color.yellow);
        }
        else if (health > 200){
            g2.setColor(Color.orange);
        }
        else if (health <= 200){
            g2.setColor(Color.red);
        }

        g2.fill(new Rectangle2D.Double(x, y + 50, health / 15, 10));

    }

    public int getHealth() {
        return health;
    }

    public void isHit() {
        health -= 200;   //reduces health by 1 when it hit

        if (health > 0) {   //as long as health in greater than zero
            try {
                int i = 0;
                while (i < 200) {
                    tankImg = read(new File("resources/explosion.png"));
                    i++;
                }
                tankImg = tankMainImg;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (health <= 0) {  //mark end of game when health is at or below 0
            try {
                tankImg = read(new File("resources/dead.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            isOver = true;
        }
    }


    public void Collision(boolean isXCollidable, boolean isYCollidable, boolean rightCollidable, boolean leftCollidable, int pos) { //used in collisions

        if (isXCollidable) {
            x = pos - getWidth();
        }
        if (isYCollidable) {
            y = pos - getHeight();
        }
        if (rightCollidable) {
            y = pos;
        }
        if (leftCollidable) {
            x = pos;
        }
    }

    public void pickUpHealth() {
        if (health <= 5) {
            health++;
        }
    }

    public Rectangle getRect() {
        return new Rectangle(x + 5, y + 5, tankImg.getWidth() - 10, tankImg.getHeight() - 10);
    }

    private void checkBorder() {
        if (x < 0) {
            x = GameWorld.SCREEN_WIDTH - 100;
        }
        if (x >= GameWorld.SCREEN_WIDTH) {
            x = 0;
        }
        if (y < 0) {
            y = GameWorld.SCREEN_HEIGHT - 100;
        }
        if (y >= GameWorld.SCREEN_HEIGHT) {
            y = 0;
        }
    }


    public int getX() {
        return this.x;
    }

    public void setX(int a) {
        this.x = a;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int b) {
        this.y = b;
    }

    public int getWidth() {
        return tankImg.getWidth();
    }

    public int getHeight() {
        return tankImg.getHeight();
    }

    public boolean getLaunched(){
        return this.isLaunched;
    }

    public void setLaunched(boolean isLaunched){
        this.isLaunched = isLaunched;
    }

    public boolean getDocked(){return this.isDocked;}

    public void setDocked(boolean isDocked) {
        if (!this.isDocked){
            score++;
        }
        this.isDocked = isDocked;
//        score++;
    }

    public void addScore(){
        this.score++;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public String toString() {
        return "x= " + x + ", y= " + y + ", angle= " + angle;
    }


    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.tankImg.getWidth() / 2.0, this.tankImg.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImg, rotation, null);
        drawHealth(g2d);
//        g.setColor(Color.blue);
//        g.drawRect(this.x + 5, this.y + 5, tankImg.getWidth() - 10, tankImg.getHeight() - 10);
    }


}
