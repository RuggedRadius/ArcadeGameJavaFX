package main.tools;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Settings
{


    // Canvas / Window Settings
    public static double CANVAS_WIDTH;
    public static double CANVAS_HEIGHT;

    // Enemies
    public static int MAX_ENEMY_COUNT = 10;
    public static int ENEMY_SIZE = 150;
    public static int ENEMY_SPEED_MULTIPLIER = 2;

    // Player
    public static int PLAYER_SIZE = 60;
    public static int PLAYER_SPEED_HORIZONTAL = 20;
    public static int PLAYER_MISSILE_SPEED = 20;
    public static int MAX_MISSILES_ONSCREEN = (MAX_ENEMY_COUNT * 2) + 10;



    // Colours
    public static Color COLOUR_MISSILE = Color.GOLDENROD;



    // Images
    public static final Image PLAYER_IMG = new Image("file:data/spaceship.png");
    public static final Image EXPLOSION_IMG = new Image("file:data/X_plosion/PNG/frame0019.png");
    public static final Image BOMBS_IMG[] = {
            new Image("file:data/enemies/tile001.png"),
            new Image("file:data/enemies/tile002.png"),
            new Image("file:data/enemies/tile003.png"),
            new Image("file:data/enemies/tile004.png"),
            new Image("file:data/enemies/tile005.png"),
            new Image("file:data/enemies/tile006.png"),
            new Image("file:data/enemies/tile007.png"),
            new Image("file:data/enemies/tile008.png"),
            new Image("file:data/enemies/tile009.png"),
            new Image("file:data/enemies/tile010.png"),
            new Image("file:data/enemies/tile011.png"),
            new Image("file:data/enemies/tile012.png"),
            new Image("file:data/enemies/tile013.png"),
            new Image("file:data/enemies/tile014.png"),
            new Image("file:data/enemies/tile015.png"),
            new Image("file:data/enemies/tile016.png"),
            new Image("file:data/enemies/tile017.png"),
            new Image("file:data/enemies/tile018.png"),
            new Image("file:data/enemies/tile019.png"),
            new Image("file:data/enemies/tile020.png"),
            new Image("file:data/enemies/tile021.png"),
            new Image("file:data/enemies/tile022.png"),
            new Image("file:data/enemies/tile023.png"),
            new Image("file:data/enemies/tile024.png"),
            new Image("file:data/enemies/tile025.png"),
            new Image("file:data/enemies/tile026.png"),
            new Image("file:data/enemies/tile027.png"),
            new Image("file:data/enemies/tile028.png"),
            new Image("file:data/enemies/tile029.png"),
            new Image("file:data/enemies/tile030.png"),
            new Image("file:data/enemies/tile031.png"),
            new Image("file:data/enemies/tile032.png"),
            new Image("file:data/enemies/tile033.png"),
            new Image("file:data/enemies/tile034.png"),
            new Image("file:data/enemies/tile035.png")
    };

    // Explosions
    public static final int EXPLOSION_W = 128;
    public static final int EXPLOSION_ROWS = 3;
    public static final int EXPLOSION_COL = 3;
    public static final int EXPLOSION_H = 128;
    public static final int EXPLOSION_STEPS = 15;
}
