package main.musicPlayer;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MusicPlayerSubLoop implements Runnable {

    public static boolean playing;
    MusicPlayer player;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private Player musicPlayer;

    public MusicPlayerSubLoop(MusicPlayer _player)
    {
        player = _player;
    }

    @Override
    public void run() {
        while (true) {
            while (playing) {
                if (player.playlist.currentNode != null) {
                    System.out.println("Playing: " + player.playlist.currentNode.fileName);


                    try {
                        // Stream file to player
                        fileInputStream = new FileInputStream(player.playlist.currentNode.file);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                        musicPlayer = new Player(bufferedInputStream);

                        // Play the music
                        musicPlayer.play();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                    // Move to next track in playlist
                    player.next();
                }
                System.out.println("end");
            }
        }
    }
}
