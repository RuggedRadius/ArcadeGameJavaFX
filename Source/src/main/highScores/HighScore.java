/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main.highScores;

import main.hash.Hash;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HighScore implements Serializable
{
    private String playerName;
    private String playerNameHashed;
    private int score;
    private String date;

    public HighScore(String _playerName, int _score) {
        // Record player's name
        playerName = _playerName;

        // Limit scores below 0 to 0
        if (_score < 0)
            _score = 0;
        score = _score;

        // Get current time and date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format(now);

        // Hash player's name for science
        playerNameHashed = Hash.MD5(_playerName);
    }

    public String getPlayerName() {
        return playerName;
    }
    public int getScore() {
        return score;
    }
    public String getDate() {
        return date;
    }
}
