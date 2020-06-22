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

public class PlayerShip extends SpaceShip
{
    boolean shieldEngaged = true;

    // Constructor
    public PlayerShip(GraphicsContext _gc, int _posX, int _posY, int _size, Image _img)
    {
        super( _gc,  _posX,  _posY,  _size,  _img);
        posX = _posX;
        posY = _posY;
        size = _size;
        img  = _img;
        gc = _gc;

        exploding = false;
        destroyed = false;
    }

    @Override
    public void explode() {
        // Handle explosion
        exploding = true;
        explosionStep = -1;

        // Destroy all on-screen enemies
        SpaceInvaders.destroyAllEnemies();

        // Screen FX
        SpaceInvaders.rumbleScreen();
    }
}