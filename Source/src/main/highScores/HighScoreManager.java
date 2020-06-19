/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main.highScores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class HighScoreManager
{
    public static ObservableList<HighScore> highScores;

    public HighScoreManager () {
        highScores = FXCollections.observableArrayList();
    }

    public void loadHighScores() {

    }

    public void saveHighScores() {

    }

    public void addHighScore(HighScore highScore)
    {
        // Hash score and/or name first?
        // ....

        // Add to list
        highScores.add(highScore);
    }
}
