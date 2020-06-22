/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import main.tools.Utilities;

import java.util.Random;

import static main.tools.Settings.CANVAS_WIDTH;

public class Star
{
    int posX;
    int posY;

    private int height;
    private int width;

    private int red;
    private int green;
    private int blue;
    private double opacity;

    GraphicsContext gc;

    public Star(GraphicsContext _gc)
    {
        gc = _gc;

        posX = Utilities.randyMcRando.nextInt((int)CANVAS_WIDTH);
        posY = 0;
        width = Utilities.randyMcRando.nextInt(5) + 1;
        height =  Utilities.randyMcRando.nextInt(5) + 1;
        red = Utilities.randyMcRando.nextInt(100) + 150;
        green = Utilities.randyMcRando.nextInt(100) + 150;
        blue = Utilities.randyMcRando.nextInt(100) + 150;
        opacity = Utilities.randyMcRando.nextFloat();
        if(opacity < 0) opacity *=-1;
        if(opacity > 0.5) opacity = 0.5;
    }

    public void draw() {
        if(opacity > 0.8) opacity-=0.01;
        if(opacity < 0.1) opacity+=0.01;
        gc.setFill(Color.rgb(red, green, blue, opacity));
        gc.fillOval(posX, posY, width, height);
        posY+=20;
    }
}
