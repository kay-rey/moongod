/*
Kevin Baltazar Reyes
 */
package moongod;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public abstract class GameObject {
    private int x, y, vx, vy, angle, width, height;
    private boolean visible;
    private Image img;

    public GameObject() {}  //Default Constructor

    public GameObject(int x, int y, int vx, int vy, int angle, BufferedImage img){
        this.img = img;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = angle;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setX(int a){
        this.x = a;
    }

    public void setY(int b){
        this.y = b;
    }

    public void draw(Graphics g, ImageObserver obs){
        g.drawImage(img, x, y, obs);

    }
}
