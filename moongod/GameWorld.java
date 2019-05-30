/*
 * Kevin Baltazar Reyes
 *
 *
 */

package moongod;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

public class GameWorld extends JPanel {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static Ship ship;
    public static Asteroid asteroid;
    public static Moon moon;
    public static ArrayList<Moon> moonArrayList = new ArrayList<>();
    public static ArrayList<Asteroid> asteroidArrayList = new ArrayList<>();
    public boolean isOver = false;
    public boolean nextLevel = false;
    Image backgroundImg;
    private int asteroidAmount = 5; //used to tell starting amount for asteroids
    private int moonAmount = 1;
    private int level = 1;
    private BufferedImage world, asteroidImg, moonImg, deadImg;
    private Graphics2D buffer;
    private JFrame jf;

    public static void main(String[] args) {
        Thread x;
        GameWorld trex = new GameWorld();
        trex.init();
        try {

            while (true) {
                trex.ship.update(); //updates ship everytime in the game
                for (int i = 0; i < moonArrayList.size(); ++i) {
                    trex.moonArrayList.get(i).update();
                }
                for (int i = 0; i < asteroidArrayList.size(); ++i) {
                    trex.asteroidArrayList.get(i).update();
                }
                trex.repaint();
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
        }
    }

    public static ArrayList<Moon> getMoonArrayList() {
        return moonArrayList;
    }

    public static void setMoonArrayList(ArrayList<Moon> moonArrayList) {
        GameWorld.moonArrayList = moonArrayList;
    }

    private void init() {
        this.jf = new JFrame("MoonGod");
        this.world = new BufferedImage(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        BufferedImage shipImg = null;
        File openingSong = null;
        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * Loads everything from the resources folder
             */
            shipImg = read(new File("resources/ship1.png")); //resources/ship1.png
            backgroundImg = read(new File("resources/backgroundAlt2.png"));    //resources/backgroundAlt2.png
            asteroidImg = read(new File("resources/asteroid.png"));    //resources/asteroid.png
            moonImg = read(new File("resources/moon.png"));    //resources/moon.png
            deadImg = read(new File("resources/youdied.png")); //resources/youdied.png
            openingSong = new File("resources/music/thembones.wav");   //resources/music/thembones.wav


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        ship = new Ship(100, 100, 0, 0, 0, shipImg);    //creates the ship that the player controls
        TankControl tc = new TankControl(ship, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);    //controls for first tank(arrow keys) enter to fire

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.addKeyListener(tc);
        this.jf.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

        asteroidMaker();    // used to make asteroids
        moonMaker();    //iterates through and makes all moons

        new GameSound(openingSong);
    }

    public void asteroidMaker() {
        for (int i = 0; i < asteroidAmount; ++i) {
            asteroidArrayList.add(asteroid = new Asteroid(asteroidImg));
        }
    }

    public void moonMaker() {
        for (int i = 0; i < moonAmount; ++i) {
            moonArrayList.add(moon = new Moon(moonImg));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        backgroundImg.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);
        drawWorld();    //used a method to clean up the code that draws everything
        g2.drawImage(world, 0, 0, null);

        drawText(g2);
    }

    private void drawWorld() {
        if (ship.getOver()) {    //if game is over then display game over screen
            buffer.drawImage(deadImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
        } else {   //if its not the end of game then draw background imagge
            drawBackGroundImage();

        }
        drawObjects();  //draw images after background so they are visible
    }

    private void drawText(Graphics2D g2) {  //prints all the text of the game
        String scoreTxt = "Game Score: " + ship.getScore();
        String levelTxt = "Level: " + level;
        String healthTxt = "Health: " + ship.getHealth();
        Font font = new Font("Copperplate", Font.BOLD, 40);
        FontMetrics metr = getFontMetrics(font);
        g2.setColor(Color.white);
        g2.setFont(font);
        g2.drawString(scoreTxt, SCREEN_WIDTH - 350, 40);
        g2.drawString(levelTxt, 50, 40);
        g2.drawString(healthTxt, (SCREEN_WIDTH / 2) - 150, SCREEN_HEIGHT - 30);
    }

    private void drawObjects() {
        if (moonArrayList.size() == 0) {    //if there are no more moons then mark next level, add another moon and asteroid and make asteroid objects again
            nextLevel = true;
            moonAmount++;
            asteroidAmount++;
            asteroidArrayList.add(new Asteroid());
            moonMaker();
            level++;
        }
        for (int i = 0; i < moonArrayList.size(); ++i) {
            moonArrayList.get(i).drawImage(buffer);
        }
        this.ship.drawImage(buffer);
        for (int i = 0; i < asteroidArrayList.size(); ++i) {
            asteroidArrayList.get(i).drawImage(buffer);
        }
    }

    public void drawBackGroundImage() {

        buffer.drawImage(backgroundImg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);

    }

    public boolean isNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(boolean nextLevel) {
        this.nextLevel = nextLevel;
    }

}

