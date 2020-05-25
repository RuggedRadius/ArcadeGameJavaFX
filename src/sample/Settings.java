package sample;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

public class Settings
{


    // Canvas / Window Settings
    public static double CANVAS_WIDTH;
    public static double CANVAS_HEIGHT;

    // Enemies
    public static int MAX_ENEMY_COUNT = 10;
    public static int ENEMY_SIZE = 100;
    public static int ENEMY_SPEED_MULTIPLIER = 2;

    // Player
    public static int PLAYER_SIZE = 60;
    public static int PLAYER_SPEED_HORIZONTAL = 20;
    public static int PLAYER_MISSILE_SPEED = 20;
    public static int MAX_MISSILES_ONSCREEN = (MAX_ENEMY_COUNT * 2) + 10;



    // Colours
    public static Color COLOUR_MISSILE = Color.GOLDENROD;



    // Images
    public static final Image PLAYER_IMG = new Image("file:/C:/Users/Ben/Documents/GitHub/ArcadeGameJavaFX/data/spaceship.png");
    public static final Image EXPLOSION_IMG = new Image("file:/C:/Users/Ben/Documents/GitHub/ArcadeGameJavaFX/data/X_plosion/PNG/frame0019.png");
    public static final Image BOMBS_IMG[] = {
            new Image("file:/C:/Users/Ben/Documents/GitHub/ArcadeGameJavaFX/data/pepe.png")
    };

    // Explosions
    public static final int EXPLOSION_W = 128;
    public static final int EXPLOSION_ROWS = 3;
    public static final int EXPLOSION_COL = 3;
    public static final int EXPLOSION_H = 128;
    public static final int EXPLOSION_STEPS = 15;
}
