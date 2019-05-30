package moongod;

import java.awt.*;


public class Collision {

    public void playerVSObjects(Ship ship) {   //checks what objects the ship hits
        Rectangle tankBox = ship.getRect();

        for (int i = 0; i < GameWorld.moonArrayList.size(); ++i){   //first checks all the moons
            Rectangle moonBox = GameWorld.moonArrayList.get(i).getRect();
            if (moonBox.intersects(tankBox)){   //if ship hits the moon then dock it and change the position to the asteroid position
//                ship.setDocked(true);
                ship.setX(GameWorld.moonArrayList.get(i).getX());
                ship.setY(GameWorld.moonArrayList.get(i).getY());
                if (ship.getLaunched()){    //once ship is launched
                    GameWorld.moonArrayList.remove(i);
                    ship.setLaunched(false);
                }
                ship.setDocked(true);
            }
        }

        for (int i = 0; i < GameWorld.asteroidArrayList.size(); ++i){
            Rectangle asteroidBox = GameWorld.asteroidArrayList.get(i).getRect();
            if (asteroidBox.intersects(tankBox) && !ship.getDocked()) {  //if ship hits asteroid then cal isHit on the ship, then remove the ship and make another random one
                ship.isHit();
                GameWorld.asteroidArrayList.remove(i);
                GameWorld.asteroidArrayList.add(new Asteroid());
            }
        }
    }
}
