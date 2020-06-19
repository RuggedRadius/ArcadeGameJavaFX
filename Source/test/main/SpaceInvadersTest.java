/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main;

import main.highScores.HighScore;
import main.highScores.HighScoreManager;
import main.mergeSort.MergeSort;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SpaceInvadersTest {

    @Test
    void sortScoresByName() {
        HighScoreManager hsManager = new HighScoreManager();

        for (int i = 0; i < 10; i++) {
            String playerName = Character.toString((char) i);
            for (int j = 0; j < 5; j++) {
                playerName += Character.toString((char) i);
            }
            hsManager.addHighScore(new HighScore(playerName, ((i * 100) + 100)));
        }

        HighScoreManager.highScores = MergeSort.Sort_Names(HighScoreManager.highScores);

        String value1 = HighScoreManager.highScores.get(0).getPlayerName();
        String value2 = HighScoreManager.highScores.get(1).getPlayerName();

        assert (value1.compareTo(value2) < 0);
    }

    @Test
    void sortScoresByScore() {
        Random randy = new Random();
        HighScoreManager hsManager = new HighScoreManager();

        for (int i = 0; i < 10; i++) {
            String playerName = Character.toString((char) i);
            for (int j = 0; j < 5; j++) {
                playerName += Character.toString((char) i);
            }
            hsManager.addHighScore(new HighScore(playerName, ((randy.nextInt(50) * 100) + randy.nextInt(50))));
        }

        HighScoreManager.highScores = MergeSort.Sort_Scores(HighScoreManager.highScores);

        int value1 = HighScoreManager.highScores.get(0).getScore();
        int value2 = HighScoreManager.highScores.get(1).getScore();

        assert (value1 >= value2);
    }

    @Test
    void sortScoresByDate() {
        HighScoreManager hsManager = new HighScoreManager();

        for (int i = 0; i < 10; i++) {
            String playerName = Character.toString((char) i);
            for (int j = 0; j < 5; j++) {
                playerName += Character.toString((char) i);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hsManager.addHighScore(new HighScore(playerName, ((i * 100) + 100)));
        }

        HighScoreManager.highScores = MergeSort.Sort_Date(HighScoreManager.highScores);

        String value1 = HighScoreManager.highScores.get(0).getDate();
        String value2 = HighScoreManager.highScores.get(1).getDate();

        assert (value1.compareTo(value2) < 0);
    }
}