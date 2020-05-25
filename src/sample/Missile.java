package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static sample.GameScore.*;

public class Missile {
    public boolean toRemove;

    int posX = 10;
    int posY = 10;
    static final int size = 6;

    GraphicsContext gc;

    public Missile(GraphicsContext _gc, int _posX, int _posY)
    {
        this.posX = _posX;
        this.posY = _posY;
        this.gc = _gc;
    }

    public void update()
    {
        posY -= Settings.PLAYER_MISSILE_SPEED;
    }

    public void draw()
    {
        gc.setFill(Settings.COLOUR_MISSILE);

        if (score >= 10000) {
            gc.setFill(Color.PURPLE);
            gc.fillRect(posX - 5, posY - 10, size + 10, size + 30);
        }
        else if (score >= 5000) {
            gc.setFill(Color.YELLOWGREEN);
            gc.fillRect(posX - 5, posY - 10, size + 10, size + 30);
        }
        else {
            gc.setFill(Color.HOTPINK);
            gc.fillOval(posX, posY, size, size);
        }
    }

    public boolean collide (SpaceShip ship)
    {
        int distance = distance(this.posX + size / 2, this.posY + size / 2, ship.posX + ship.size / 2, ship.posY + ship.size / 2);
        return distance < ship.size / 2 + size / 2;
    }

    int distance(int x1, int y1, int x2, int y2) {
        return (int) Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
    }
}
