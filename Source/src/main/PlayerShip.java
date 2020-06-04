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
        System.out.println("Player destroyed!");

        exploding = true;
        explosionStep = -1;

        SpaceInvaders.destroyAllEnemies();
        SpaceInvaders.rumbleScreen();
    }
}



// Rumble screen FX
//                rumbleScreen();