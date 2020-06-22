/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.tools.Settings;

import static main.GameScore.*;
import static main.tools.Settings.*;

public class EnemyShip extends SpaceShip
{
    int scoreValue = 100;
    int SPEED = ((score/1000) + 1) * Settings.ENEMY_SPEED_MULTIPLIER;

    public EnemyShip(GraphicsContext _gc, int posX, int posY, int size, Image img)
    {
        super(_gc, posX, posY, size / 2, img);
    }

    // Called per frame
    public void update()
    {
        super.update();

        if (!exploding && ! destroyed)
        {
            posY += SPEED;
        }
        if (posY > CANVAS_HEIGHT)
        {
            // Destroy enemy ship
            destroyed = true;

            // Decrement score
            score -= scoreValue;
        }
    }
}
