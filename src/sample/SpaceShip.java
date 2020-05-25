package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;

import static sample.SpaceInvaders.*;
import static sample.Settings.*;

public class SpaceShip
{
    // Properties
    int posX;
    int posY;
    int size;

    // State
    boolean exploding;
    boolean destroyed;

    // Sprite image
    Image img;

    // ...?
    int explosionStep = 0;

    GraphicsContext gc;

    // Constructor
    public SpaceShip(GraphicsContext _gc, int _posX, int _posY, int _size, Image _img)
    {
        posX = _posX;
        posY = _posY;
        size = _size;
        img  = _img;
        gc = _gc;

        exploding = false;
        destroyed = false;
    }

    public Missile shoot()
    {
        // SFX
        // ... TO DO


       return new Missile(gc,posX+size / 2 - Missile.size / 2, posY - Missile.size);
    }

    public void update()
    {
        if (exploding)
        {
            explosionStep++;
            destroyed = explosionStep > EXPLOSION_STEPS;
            System.out.println("Ship destroyed!");

        }

        if (GameScore.score >= 1000)
        {

        }
    }

    public void draw() {
        if (exploding)
        {
            gc.drawImage(EXPLOSION_IMG,
                    explosionStep % EXPLOSION_COL * EXPLOSION_W,
                    (explosionStep / EXPLOSION_ROWS) * EXPLOSION_H + 1,
                    EXPLOSION_W,
                    EXPLOSION_H,
                    posX - (EXPLOSION_W/2),
                    posY - (EXPLOSION_H/2),
                    size * 5, size * 5);
        }
        else
        {
            gc.drawImage(img, posX, posY, size, size);
        }
    }

    public boolean collide(SpaceShip other) {
        int d = distance(this.posX + size / 2, this.posY + size / 2, other.posX + other.size / 2, other.posY + other.size / 2);
        return d < other.size / 2 + this.size / 2;
    }

    public void explode() {
        exploding = true;
        explosionStep = -1;
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }

}
