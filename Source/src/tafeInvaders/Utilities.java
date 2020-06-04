package tafeInvaders;

import javafx.scene.paint.Color;

import java.util.Random;

public class Utilities
{
    public static Random randyMcRando = new Random();

    public static Color randomColour()
    {
        int red = randyMcRando.nextInt(255);
        int green = randyMcRando.nextInt(255);
        int blue = randyMcRando.nextInt(255);
        return Color.rgb(red, green, blue);
    }

}
