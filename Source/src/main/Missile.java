/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import main.tools.Settings;
import main.tools.Utilities;
//import gameObjects.SpaceShip;


import static main.GameScore.*;

public class Missile
{
    public boolean toRemove;

    int posX = 10;
    int posY = 10;
    static final int size = 40; //25

    GraphicsContext gc;

    Color missileColor;

    // Constructor
    public Missile(GraphicsContext _gc, int _posX, int _posY)
    {
        this.posX = _posX;
        this.posY = _posY;
        this.gc = _gc;

        missileColor = Utilities.randomColour();
    }

    // Called every frame
    public void update()
    {
        posY -= Settings.PLAYER_MISSILE_SPEED;
    }

    // Draw to canvas
    public void draw()
    {
        RadialGradient missileBasic = new RadialGradient(
                0,
                0.1, //0.1
                0.5,
                0.5,
                1,
                true,
                CycleMethod.NO_CYCLE,
                new Stop[] {
                        new Stop(0, Color.TRANSPARENT),
                        new Stop(1, missileColor),
                        new Stop(2, Color.TRANSPARENT)
                });

        if (score >= 2500) {
            RadialGradient spiralGradient = new RadialGradient(
                    0,
                    0, //0.1
                    0.5,
                    0.5,
                    0.1,
                    true,
                    CycleMethod.REPEAT,
                    new Stop[] {
                            new Stop(0, Color.TRANSPARENT),
                            new Stop(1, missileColor),
                            new Stop(2, Color.TRANSPARENT)
                    });
            gc.setFill(spiralGradient);

            gc.fillOval(posX, posY, size + 30, size + 30);
            Settings.PLAYER_MISSILE_SPEED = 100;
        }
        else if (score >= 1000) {
            LinearGradient linear = new LinearGradient(
                    0,
                    0, //0.1
                    0,
                    0.1,
                    true,
                    CycleMethod.REPEAT,
                    new Stop[] {
                            new Stop(0, Color.TRANSPARENT),
                            new Stop(1, missileColor)
                    });
            gc.setFill(linear);

            gc.fillRect(posX, posY, size - 15, size + 30);
            Settings.PLAYER_MISSILE_SPEED = 60;
        }
        else {
            gc.setFill(missileBasic);
            gc.fillOval(posX, posY, size, size);
        }
    }

    // Detect collision with other game objects
    public boolean collide (SpaceShip ship)
    {
        int distance = distance(this.posX + size / 2, this.posY + size / 2, ship.posX + ship.size / 2, ship.posY + ship.size / 2);
        return distance < ship.size / 2 + size / 2;
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
