package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

import static main.tools.Settings.CANVAS_WIDTH;

public class Star
{
    int posX, posY;
    private int h, w, r, g, b;
    private double opacity;

    Random randyMcRando;
    GraphicsContext gc;

    public Star(GraphicsContext _gc)
    {
        randyMcRando = new Random();
        gc = _gc;

        posX = randyMcRando.nextInt((int)CANVAS_WIDTH);
        posY = 0;
        w = randyMcRando.nextInt(5) + 1;
        h =  randyMcRando.nextInt(5) + 1;
        r = randyMcRando.nextInt(100) + 150;
        g = randyMcRando.nextInt(100) + 150;
        b = randyMcRando.nextInt(100) + 150;
        opacity = randyMcRando.nextFloat();
        if(opacity < 0) opacity *=-1;
        if(opacity > 0.5) opacity = 0.5;
    }

    public void draw() {
        if(opacity > 0.8) opacity-=0.01;
        if(opacity < 0.1) opacity+=0.01;
        gc.setFill(Color.rgb(r, g, b, opacity));
        gc.fillOval(posX, posY, w, h);
        posY+=20;
    }
}
