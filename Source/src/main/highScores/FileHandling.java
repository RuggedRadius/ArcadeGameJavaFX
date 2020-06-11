package main.highScores;

import java.io.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

public class FileHandling
{
    public static void saveDataFile()
    {
        try {
            // Open streams
            FileOutputStream outputStream = new FileOutputStream(new File("userdata/highscores.dat"));
            ObjectOutputStream objOutStream = new ObjectOutputStream(outputStream);

            // Write objects to file
            objOutStream.writeObject(new ArrayList<HighScore>(HighScoreManager.highScores));

            // Close streams
            objOutStream.close();
            outputStream.close();

            System.out.println("Highscores saved to file");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void loadDataFile()
    {
        try {
            // Open streams
            FileInputStream inputStream = new FileInputStream(new File("userdata/highscores.dat"));
            ObjectInputStream objInputStream = new ObjectInputStream(inputStream);

            // Read objects
            ArrayList<HighScore> scores = (ArrayList<HighScore>) objInputStream.readObject();
            System.out.println(scores.size() + " highscore records loaded");
            HighScoreManager.highScores = FXCollections.observableArrayList(scores);

            // Close streams
            objInputStream.close();
            inputStream.close();

            System.out.println("Highscores loaded from file");
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("No previous highscores found");
        }
        catch (Exception ex) {
            System.err.println("Error loading highscores.dat");
        }
    }

    public static File getLocalFile() {

        FileChooser mrChoosey = new FileChooser();
        return  mrChoosey.showOpenDialog(null);
    }
}
