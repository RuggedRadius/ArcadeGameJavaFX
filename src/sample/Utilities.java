package sample;

import javafx.scene.paint.Color;

import java.util.Random;

public class Utilities
{
    public static Random randy = new Random();

    public static Color randomColour()
    {
        int red = randy.nextInt(255);
        int green = randy.nextInt(255);
        int blue = randy.nextInt(255);
        return Color.rgb(red, green, blue);
    }

}
