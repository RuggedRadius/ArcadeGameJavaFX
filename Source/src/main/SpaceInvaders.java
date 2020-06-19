/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */


package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import main.highScores.FileHandling;
import main.highScores.HighScore;
import main.highScores.HighScoreManager;
import main.mergeSort.MergeSort;
import main.musicPlayer.MusicPlayer;
import main.tools.Settings;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;


import static main.GameScore.score;
import static main.tools.Settings.*;
import static main.tools.Utilities.randyMcRando;
import static main.binarySearch.BinarySearch.binarySearch;

public class SpaceInvaders extends Application
{
    // FX Controls
    @FXML public Label lblScore;
    @FXML public ListView lstPlaylist;
    @FXML public TableView tblHighScores;
    @FXML public TextField txtSearch;
    @FXML public Button btnSearch;
    @FXML public Button btnPlay;
    @FXML public Button btnStop;
    @FXML public Button btnNext;
    @FXML public Button btnPrevious;
    @FXML public Button btnSortName;
    @FXML public Button btnSortScore;
    @FXML public Button btnSortDate;

    // Variables
    boolean gameOver = false;
    boolean gameOverPrinted = false;
    private GraphicsContext gc;

    // Game objects
    PlayerShip player;
    List<Missile> missiles;
    List<Star> univ;
    static List<EnemyShip> enemyShips;

    // UI
    public static Stage stage;
    Canvas gameCanvas;
    private double mouseX;
    MusicPlayer musicPlayer;
    HighScoreManager scoreManager;

    // State
    boolean movingLeft;
    boolean movingRight;
    boolean paused;

    // Sorting
    @FXML public boolean sortScoresByName() {
        System.out.println("Sorting by name");
        HighScoreManager.highScores = MergeSort.Sort_Names(HighScoreManager.highScores);
        tblHighScores.setItems(HighScoreManager.highScores);
        return true;
    }
    @FXML public boolean sortScoresByScore() {
        System.out.println("Sorting by score");
        HighScoreManager.highScores = MergeSort.Sort_Scores(HighScoreManager.highScores);
        tblHighScores.setItems(HighScoreManager.highScores);
        return true;
    }
    @FXML public boolean sortScoresByDate() {
        System.out.println("Sorting by date");
        HighScoreManager.highScores = MergeSort.Sort_Date(HighScoreManager.highScores);
        tblHighScores.setItems(HighScoreManager.highScores);
        return true;
    }

    // Search
    @FXML private void search() {
        // Sort list of music files
        Collections.sort((lstPlaylist.getItems()));

        // Get array of music files
        String[] musicFiles = new String[lstPlaylist.getItems().size()];
        for (int i = 0; i < musicFiles.length; i++)
        {
            musicFiles[i] = lstPlaylist.getItems().get(i).toString();
        }

        // Get key for search
        String key = txtSearch.getText();

        // Get index of key
        int foundIndex = binarySearch(musicFiles, 0, musicFiles.length - 1, key);

        // Determine if song is found
        if (foundIndex >= 0)
        {
            // Select song
            lstPlaylist.getSelectionModel().select(foundIndex);

            // play song
            playSong(key);
        }
        else
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Song not found, please check the title.",
                    "Song Title Not Found",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // General
    @Override public void start(Stage stage) throws Exception {
        System.out.println("Initialising game...");

        // Background Music
        System.out.println("Starting background music...");
        musicPlayer = new MusicPlayer(this);

        // High scores manager
        scoreManager = new HighScoreManager();

        // Load scene from FXML file
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        // Init UI elements
        initHighScoreTable();

        // Initialise window
        stage.setTitle("TAFE Invaders");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        // Add handler to stop all threads on exit
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        // Call shutdown method
                        shutdownGame();

                        // Exit app
                        System.exit(0);
                    }
                });
            }
        });

        // Get target FX control for game area to reside in
        HBox targetFrame = (HBox) root.lookup("#gameFrame");
        Settings.CANVAS_WIDTH = (int) targetFrame.getWidth();
        Settings.CANVAS_HEIGHT = (int) targetFrame.getHeight();

        // Create game canvas
        gameCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);

        gameCanvas.setFocusTraversable(true);

        turnOffFocusTraversableUiElements();

        gc = gameCanvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), e -> run(gc)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add input handlers to canvas
        addHandlersToCanvas(gameCanvas);

        // Set up game canvas
        setup();

        // Add canvas to window
        targetFrame.getChildren().add(gameCanvas);

        // Populate playlist list view
        String[] musicFiles = musicPlayer.playlist.traverseFilenames();
        for (String musicFile : musicFiles)
        {
            lstPlaylist.getItems().add(musicFile);
        }

        // Select first item in playlist
        lstPlaylist.getSelectionModel().select(musicPlayer.playlist.currentNode.getFileName());

        // Focus on game area for controls
        gameCanvas.requestFocus();
    }
    @FXML private void shutdownGame() {
        // Stop music
        musicPlayer.stop();

        // Save highscores to file
        FileHandling.saveDataFile();

        // Exit app
        System.exit(0);
    }
    private void setup() {
        // Initialise objects
        univ = new ArrayList<>();
        missiles = new ArrayList<>();
        enemyShips = new ArrayList<>();
        player = new PlayerShip(gc, (int)CANVAS_WIDTH / 2, (int)CANVAS_HEIGHT - PLAYER_SIZE, PLAYER_SIZE, PLAYER_IMG);
        score = 0;

        // Create enemies
        IntStream.range(0, MAX_ENEMY_COUNT).mapToObj(i -> this.newEnemy()).forEach(enemyShips::add);

        // Lambda Runnable
        Runnable pressEnterText = () -> {
            player.shieldEngaged = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player.shieldEngaged = false;
        };
        new Thread(pressEnterText).start();
    }
    public static void main(String[] args) { launch(args); }

    // Music
    private void playSong(String fileName) {
        if (!musicPlayer.playlist.currentNode.getFileName().equals(fileName))
        {
            // Stop the music
            musicPlayer.stop();

            // Start at the beginning
            musicPlayer.playlist.currentNode = musicPlayer.playlist.getFirst();

            while (!musicPlayer.playlist.currentNode.getFileName().equals(fileName)) {
                musicPlayer.playlist.currentNode = musicPlayer.playlist.currentNode.getNext();
            }

            // Play the music
            musicPlayer.play();
        }
    }
    public void selectPlaylistItem(String playlistItem) {
        lstPlaylist.getSelectionModel().select(playlistItem);
    }
    @FXML public void musicPlay() {
        musicPlayer.play();
    }
    @FXML public void musicStop() {
        musicPlayer.stop();
    }
    @FXML public void musicNext() {
        musicPlayer.next(); }
    @FXML public void musicPrevious() {
        musicPlayer.previous(); }

    // UI
    private void initHighScoreTable() {
        System.out.println("Initialising high score table...");

        TableColumn<String, HighScore> colName = new TableColumn<>("Player Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("playerName"));

        TableColumn<String, HighScore> colScore = new TableColumn<>("Score");
        colScore.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<String, HighScore> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        colName.prefWidthProperty().bind(tblHighScores.widthProperty().multiply(0.4));
        colScore.prefWidthProperty().bind(tblHighScores.widthProperty().multiply(0.2));
        colDate.prefWidthProperty().bind(tblHighScores.widthProperty().multiply(0.4));

        tblHighScores.getColumns().addAll(colName, colScore, colDate);

        tblHighScores.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        tblHighScores.setEditable(false);

        FileHandling.loadDataFile();

        tblHighScores.setItems(scoreManager.highScores);
    }
    @FXML private void clearAllHighscores () {
        HighScoreManager.highScores.clear();
    }
    private void turnOffFocusTraversableUiElements() {
        lstPlaylist.setFocusTraversable(false);
        tblHighScores.setFocusTraversable(false);
        btnNext.setFocusTraversable(false);
        btnPlay.setFocusTraversable(false);
        btnPrevious.setFocusTraversable(false);
        btnSortDate.setFocusTraversable(false);
        btnSortName.setFocusTraversable(false);
        btnSortScore.setFocusTraversable(false);
        btnStop.setFocusTraversable(false);
        btnSearch.setFocusTraversable(false);
        lblScore.setFocusTraversable(false);
        lstPlaylist.setFocusTraversable(false);
        txtSearch.setFocusTraversable(false);
    }
    private void addHandlersToCanvas(Canvas gameCanvas) {
        gameCanvas.setCursor(Cursor.MOVE);
        gameCanvas.setOnMouseMoved(e -> { mouseX = e.getX(); });
        gameCanvas.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT && !movingLeft)
            {
                // Lambda Runnable
                Runnable moveLeftRun = () -> {
                    movingRight = true;
                    while (movingRight)
                    {
                        player.posX -= 1;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                new Thread(moveLeftRun).start();
            }
            if (e.getCode() == KeyCode.RIGHT && !movingRight)
            {

                // Lambda Runnable
                Runnable moveRightRun = () -> {
                    movingRight = true;
                    while (movingRight)
                    {
                        player.posX += 1;

                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                new Thread(moveRightRun).start();
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
            } });
        gameCanvas.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                movingLeft = false;
                movingRight = false;
            }

            if (e.getCode() == KeyCode.RIGHT)
            {
                movingRight = false;
                movingLeft = false;
            }

        });
        gameCanvas.setOnMouseClicked(e -> { handleMouseClick(e); });
        gameCanvas.setOnMouseClicked(event -> {
            gameCanvas.requestFocus();
            paused = false;
        });
    }
    private String getPlayerName() {
        String playerName = null;
        while (playerName == null || playerName.isBlank() || playerName.isEmpty())
        {
            playerName = JOptionPane.showInputDialog(null, "Enter your name: ", "High Score", -1);
        }
        return playerName;
    }

    // Input
    private void handleMouseClick(MouseEvent e) {
        if(missiles.size() < MAX_MISSILES_ONSCREEN) missiles.add(player.shoot());
    }
    private void handleInputKeys(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT)
        {
            gameCanvas.requestFocus();
            player.posX -= Settings.PLAYER_SPEED_HORIZONTAL;
        }
        if (e.getCode() == KeyCode.RIGHT)
        {
            gameCanvas.requestFocus();
            player.posX += Settings.PLAYER_SPEED_HORIZONTAL;
        }
        if (e.getCode() == KeyCode.SPACE) {

            if(missiles.size() < MAX_MISSILES_ONSCREEN)
            {
                missiles.add(player.shoot());
            }
        }
        if (e.getCode() == KeyCode.ENTER) {
            System.out.println("Enter pressed");
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

    // Gameplay
    @FXML private void togglePause() {
        paused = !paused;

        if (!paused)
        {
            gameCanvas.requestFocus();
        }
        else
        {
            txtSearch.requestFocus();
        }
    }
    public static void rumbleScreen() {

        // Lambda Runnable
        Runnable task2 = () -> {
            for (int i = 0; i < 10; i++) {
                int jumpSize = 50;
                double xPos = stage.getX() + ((randyMcRando.nextDouble() * jumpSize) - (jumpSize / 2));
                double yPos = stage.getY() + ((randyMcRando.nextDouble() * jumpSize) - (jumpSize / 2));
                stage.setX(xPos);
                stage.setY(yPos);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Reset screen position
            stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
            stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
//            stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
//            stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        };

        // start the thread
        new Thread(task2).start();
    }

    // Graphics
    private void run(GraphicsContext gc) {
        if (!gameCanvas.isFocused())
            paused = true;


        if (gameOver)
        {
            if (!gameOverPrinted) {
                destroyAllEnemies();
                printGameOver();
            }
        }
        else if (paused) {
            // Draw base
            drawBlackScreen();

            // Draw stars underneath spaceships
            univ.forEach(Star::draw);

            // Write paused
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ss");
            int date = LocalDateTime.now().getSecond();
            if (date%2 == 0) {
                gc.setFill(Color.WHITE);
                gc.setFont(new Font(gc.getFont().getName(), 100));
                gc.fillText("|| PAUSED", 250, 100);
            }

            // Replenish stars
            if(randyMcRando.nextInt(10) > 2) {
                univ.add(new Star(gc));
            }
            for (int i = 0; i < univ.size(); i++) {
                if(univ.get(i).posY > CANVAS_HEIGHT)
                    univ.remove(i);
            }
        }
        else
        {
            gameCanvas.requestFocus();

            // Paint black base
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

            // Draw stars underneath spaceships
            univ.forEach(Star::draw);

            player.update();
            player.draw();

            if (player.shieldEngaged)
            {
                // Random
                RadialGradient gradient1 = new RadialGradient(
                        0,
                        0, //0.1
                        0.5,
                        0.5,
                        1,
                        true,
                        CycleMethod.REPEAT,
                        new Stop[] {
                                new Stop(0, Color.TRANSPARENT),
                                new Stop(1, Color.GREEN),
                                new Stop(2, Color.TRANSPARENT)
                        });

                gc.setFill(gradient1);
                int shieldRadius = 100;
                gc.fillOval(player.posX - 20, player.posY - 20, shieldRadius, shieldRadius);
            }

            clampPlayerBounds();

            // Check player is alive
            enemyShips.stream().peek(SpaceShip::update).peek(SpaceShip::draw).forEach(e ->
            {
                if (player.collide(e) && !player.exploding)
                {
                    if (player.shieldEngaged)
                    {
                        // Explode ship instead
                        e.explode();
                    }
                    else
                    {
                        // Player Death
                        player.explode();
                    }
                }
            });

            for (int i = missiles.size() - 1; i >=0 ; i--)
            {
                Missile missile = missiles.get(i);
                if(missile.posY < 0 || missile.toRemove)
                {
                    missiles.remove(i);
                    continue;
                }
                missile.update();
                missile.draw();
                for (EnemyShip enemy : enemyShips)
                {
                    if(missile.collide(enemy) && !enemy.exploding)
                    {
                        score += enemy.scoreValue;
                        enemy.explode();
                        missile.toRemove = true;
                    }
                }
            }

            // Replace destroyed enemies
            for (int i = enemyShips.size() - 1; i >= 0; i--)
            {
                if(enemyShips.get(i).destroyed && !gameOver)
                {
                    enemyShips.set(i, newEnemy()) ;
                }
            }

            gameOver = player.destroyed;


            // Replenish stars
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
    }
    private void drawBlackScreen() {
        // Paint black base
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    }
    private void printGameOver() {
        // Start printing thread
        Runnable gameOverText = () -> {
            gameOverPrinted = true;

            // Write game over text
            gc.setTextAlign(TextAlignment.CENTER);

            // Print "Game Over"
            Runnable printGameOverText = () -> {
                for (int i = 0; i < 1; i++) {
                    int startPos = (int) (CANVAS_HEIGHT / 2);
                    int fontSize = 30;
                    int initR = 0;
                    int initG = 0;
                    int initB = 255;

                    for (int j = 0; j < 5; j++)
                    {
                        gc.setFont(Font.font(fontSize));
                        gc.setFill(Color.rgb(initR, initG, initB));
                        gc.fillText("Game Over", CANVAS_WIDTH / 2, startPos);

                        initG += 25;
                        fontSize += 8;
                        startPos -= (i * 5) + 25;

                        try
                        {
                            Thread.sleep(100);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        drawBlackScreen();

                    }
                    gc.setFont(Font.font(fontSize));
                    gc.setFill(Color.rgb(initR, initG, initB));
                    gc.fillText("Game Over", CANVAS_WIDTH / 2, startPos);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(printGameOverText).start();

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            // Lambda Runnable
            Runnable pressEnterText = () -> {
                gc.setFont(Font.font(20));

                while (gameOver) {
                    drawBlackScreen();

                    // Print game over
                    gc.setFont(Font.font(80));
                    gc.setFill(Color.rgb(0, 125, 255));
                    gc.fillText("Game Over", CANVAS_WIDTH / 2, (int) (CANVAS_HEIGHT / 2) - 150);

                    // Print 'click here' if out of focus
                    if (!gameCanvas.isFocused()) {
                        gc.setFont(Font.font(40));
                        gc.setFill(Color.rgb(100, 100, 100));
                        gc.fillText("<Click the game screen to continue playing>", CANVAS_WIDTH / 2, (int) (CANVAS_HEIGHT / 2) + 100);
                    }

                    gc.setFill(Color.rgb(0, 125, 255));
                    gc.setFont(Font.font(20));
                    gc.fillText("Press <ENTER> to play again...", CANVAS_WIDTH / 2, (CANVAS_HEIGHT / 2) + 250);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}

                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font(20));
                    gc.fillText("Press <ENTER> to play again...", CANVAS_WIDTH / 2, (CANVAS_HEIGHT / 2) + 250);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {}
                }

            };
            new Thread(pressEnterText).start();


            // Create high score object, Add to high scores list
            scoreManager.addHighScore(new HighScore(getPlayerName(), score));
        };
        new Thread(gameOverText).start();


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

    // Menu
    @FXML public void openHelpFiles() {
        File htmlFile = new File("help/index.html");
        try {
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML public void openAboutDisplay() {
        JOptionPane.showMessageDialog(null,
                "TAFE INVADERS\n=================\n\nThis project was created by Ben Royans.\nJava III in 2020. " +
                        "\n\nEmail any questions or queries to ben.royans@gmail.com",
                "About", JOptionPane.INFORMATION_MESSAGE);
    }
}



