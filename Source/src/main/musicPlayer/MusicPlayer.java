package tafeInvaders.musicPlayer;

import javazoom.jl.player.Player;
import org.apache.commons.io.FilenameUtils;
import tafeInvaders.FileHandling;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class MusicPlayer {

    // Music
    Thread playThread;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    Player musicPlayer;
    File[] soundtrack;





    private void loadMusicFiles() {
        try
        {
            // Load music files from /music directory
            soundtrack = FileHandling.getMusicFiles();

            if (soundtrack.length == 0)
                System.err.println("No music files found!");
            else
                System.out.println("Loading " + soundtrack.length + " music files...");

            for (File file: soundtrack)
            {
                if (FilenameUtils.getExtension(file.getName()) == "mp3")
                {
                    // .mp3 found

                    System.out.println("Loading " + file.getName() + "...");

                    // Add to list view
                    lstPlaylist.getItems().add(file.getName());

                }

            }

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    Runnable runnablePlayMusic = new Runnable() {
        @Override
        public void run() {
            try {

                // Load music files
                loadMusicFiles();

                while (true)
                {
                    for (int i = 0; i < soundtrack.length; i++)
                    {
                        // Stream file to player
                        fileInputStream = new FileInputStream(soundtrack[i]);
                        bufferedInputStream = new BufferedInputStream(fileInputStream);
                        musicPlayer = new Player(bufferedInputStream);

                        // Play the music
                        musicPlayer.play();
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };
}
