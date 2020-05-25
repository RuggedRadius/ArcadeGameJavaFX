package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static sample.GameScore.*;
import static sample.Settings.*;

public class EnemyShip extends SpaceShip
{
    int scoreValue = 100;

//    int SPEED = ((score/1000) + 1) * Settings.ENEMY_SPEED_MULTIPLIER;
    int SPEED = ((score/1000) + 10) * Settings.ENEMY_SPEED_MULTIPLIER; // DEBUG VERSION!!

    public EnemyShip(GraphicsContext _gc, int posX, int posY, int size, Image img)
    {
        super(_gc, posX, posY, size / 2, img);
    }

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
