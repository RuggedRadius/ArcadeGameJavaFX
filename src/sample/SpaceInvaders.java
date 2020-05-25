package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static sample.Settings.*;
import static sample.GameScore.*;


public class SpaceInvaders extends Application
{
    // FX Controls
    @FXML public Label lblScore;

    // Variables
    private static final Random randyMcRando = new Random();

    boolean gameOver = false;
    boolean gameOverPrinted = false;
    private GraphicsContext gc;

    PlayerShip player;
    List<Missile> missiles;
    List<Star> univ;
    static List<EnemyShip> enemyShips;

    private double mouseX;

    public static Stage stage;
    Canvas gameCanvas;
    MediaPlayer musicPlayer;

    // General
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Initialising game...");

        // Load scene from FXML file
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        // Initialise window
        stage.setTitle("TAFE Invaders");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        // Get target FX control for game area to reside in
        HBox targetFrame = (HBox) root.lookup("#gameFrame");
        Settings.CANVAS_WIDTH = (int) targetFrame.getWidth();
        Settings.CANVAS_HEIGHT = (int) targetFrame.getHeight();


        // Create game canvas
        gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = gameCanvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add input handlers to canvas
        gameCanvas.setCursor(Cursor.MOVE);
        gameCanvas.setOnMouseMoved(e -> { mouseX = e.getX(); });
        gameCanvas.setOnKeyPressed(e -> { handleInputKeys(e); });
        gameCanvas.setOnMouseClicked(e -> { handleMouseClick(e); });

        // Set up game canvas
        setup();

        // Add canvas to window
        targetFrame.getChildren().add(gameCanvas);

        // Music
        System.out.println("Starting music...");
        Media media = new Media(getClass().getResource("Space_Riddle.mp3").toURI().toString());
        musicPlayer = new MediaPlayer(media);
        musicPlayer.setAutoPlay(true);
        musicPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                musicPlayer.seek(Duration.ZERO);
            }
        });

        // Focus on game area for controls
        gameCanvas.requestFocus();
    }
    private void setup() {
        // Initialise objects
        univ = new ArrayList<>();
        missiles = new ArrayList<>();
        enemyShips = new ArrayList<>();
        player = new PlayerShip(gc, (int)CANVAS_WIDTH / 2, (int)CANVAS_HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;

        IntStream.range(0, MAX_ENEMY_COUNT).mapToObj(i -> this.newEnemy()).forEach(enemyShips::add);
    }
    public static void main(String[] args) {

//        DoublyLinkedList dll = new DoublyLinkedList(new Node("Tester", 50000, 5));
//        dll.addFirst(new Node("New First", 3000, 21));
//        for (int i = 0; i < 10; i++) {
//            dll.addLast(new Node("Player " + i, 40000, 3));
//        }
//        dll.testTraverse();


        launch(args);


    }

    // Input
    private void handleMouseClick(MouseEvent e) {
        if(missiles.size() < MAX_MISSILES_ONSCREEN) missiles.add(player.shoot());
        if(gameOver) {
            gameOver = false;
            gameOverPrinted = false;
            setup();
        }
    }
    private void handleInputKeys(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT)
        {
            player.posX -= Settings.PLAYER_SPEED_HORIZONTAL;
        }
        if (e.getCode() == KeyCode.RIGHT)
        {
            player.posX += Settings.PLAYER_SPEED_HORIZONTAL;
        }
        if (e.getCode() == KeyCode.SPACE)
        {
            if(missiles.size() < MAX_MISSILES_ONSCREEN)
            {
                missiles.add(player.shoot());
            }
        }
        if (gameOver)
        {
            if (e.getCode() == KeyCode.ENTER) {
                System.out.println("Enter pressed.");
                gameOver = false;
                gameOverPrinted = false;
                setup();
            }
        }
    }

    //run Graphics
    private void run(GraphicsContext gc) {
        if (gameOver)
        {
            destroyAllEnemies();
            if (!gameOverPrinted) {
                printGameOver();
            }
        }
        else
        {
            // Paint black base
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

            // Draw stars underneath spaceships
            univ.forEach(Star::draw);

            player.update();
            player.draw();
//            player.posX = (int) mouseX;
            clampPlayerBounds();

            // Check player is alive
            enemyShips.stream().peek(SpaceShip::update).peek(SpaceShip::draw).forEach(e ->
            {
                if (player.collide(e) && !player.exploding)
                {
                    // Player Death
                    player.explode();
                }
            });

            for (int i = missiles.size() - 1; i >=0 ; i--) {
                Missile shot = missiles.get(i);
                if(shot.posY < 0 || shot.toRemove)  {
                    missiles.remove(i);
                    continue;
                }
                shot.update();
                shot.draw();
                for (EnemyShip bomb : enemyShips) {
                    if(shot.collide(bomb) && !bomb.exploding) {
                        score += bomb.scoreValue;
                        bomb.explode();
                        shot.toRemove = true;
                    }
                }
            }

            // Replace destroyed enemies
            for (int i = enemyShips.size() - 1; i >= 0; i--)
            {
                if(enemyShips.get(i).destroyed && !gameOver)
                {
                    System.out.println("Spawning enemy...");
                    enemyShips.set(i, newEnemy());
                }
            }

            gameOver = player.destroyed;
            if(randyMcRando.nextInt(10) > 2) {
                univ.add(new Star(gc));
            }
            for (int i = 0; i < univ.size(); i++) {
                if(univ.get(i).posY > CANVAS_HEIGHT)
                    univ.remove(i);
            }
        }

        // Update UI
        lblScore.setText(String.valueOf(score));
        gameCanvas.requestFocus();
    }
    private void printGameOver() {
        System.out.println("Printing game over..");
        Runnable gameOverText = () -> {

            // Paint black base
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

            // FX
            // .. Vignette maybe?

            // Write game over text
            gc.setTextAlign(TextAlignment.CENTER);


            // Print "Game Over"
            Runnable printGameOverText = () -> {
                int startPos = (int) (CANVAS_HEIGHT / 2);
                int fontSize = 30;
                int initR = 0;
                int initG = 55;
                int initB = 255;

                for (int i = 0; i < 5; i++) {
                    gc.setFont(Font.font(fontSize));
                    gc.setFill(Color.rgb(initR, initG, initB));
                    gc.fillText("Game Over", CANVAS_WIDTH / 2, startPos);

                    initG += 50;
                    fontSize += 8;
                    startPos -= (i * 5) + 25;

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(printGameOverText).start();


            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }


            // Lambda Runnable
            Runnable pressEnterText = () -> {
                gc.setFont(Font.font(20));

                while (gameOver) {
                    gc.setFill(Color.GREEN);
                    gc.setFont(Font.font(20));
                    gc.fillText("Press <ENTER> to play again...", CANVAS_WIDTH / 2, (CANVAS_HEIGHT / 2) + 250);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}

                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font(20));
                    gc.fillText("Press <ENTER> to play again...", CANVAS_WIDTH / 2, (CANVAS_HEIGHT / 2) + 250);
                }

            };
            new Thread(pressEnterText).start();

            gameOverPrinted = true;
        };
        if (!gameOverPrinted) {
            new Thread(gameOverText).start();
        }

    }

    private void clampPlayerBounds() {
        int lowerBound = 0;
        int upperBound = (int) (CANVAS_WIDTH - PLAYER_SIZE);

        if (player.posX < lowerBound)
        {
            player.posX = 0;
        }
        if (player.posX > upperBound)
        {
            player.posX = upperBound;
        }
    }


    // Enemies
    EnemyShip newEnemy() {
        Image img = BOMBS_IMG[randyMcRando.nextInt(BOMBS_IMG.length)];
        return new EnemyShip(gc, 50 + randyMcRando.nextInt((int)CANVAS_WIDTH - 100), 0, ENEMY_SIZE, img);
    }
    private void destroyEnemyShip(EnemyShip enemyShip) {
        System.out.println("Destroying enemy ship");

        // Increment score
        score += enemyShip.scoreValue;

        // Display score increase
        gc.setFont(Font.font(20));
        gc.setFill(Color.GREEN);
        gc.fillText(String.valueOf(enemyShip.scoreValue), enemyShip.posX, enemyShip.posY);

        // Explode enemy ship
        enemyShip.explode();
    }
    public static void destroyAllEnemies() {
        for (EnemyShip enemy : enemyShips) {
            enemy.explode();
        }
    }

    // FX
    public static void rumbleScreen() {

        // Lambda Runnable
        Runnable task2 = () -> {
            for (int i = 0; i < 10; i++) {
                double xPos = stage.getX() + ((randyMcRando.nextDouble() * 20) - 10);
                double yPos = stage.getY() + ((randyMcRando.nextDouble() * 20) - 10);
                stage.setX(xPos);
                stage.setY(yPos);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // start the thread
        new Thread(task2).start();
    }
}



