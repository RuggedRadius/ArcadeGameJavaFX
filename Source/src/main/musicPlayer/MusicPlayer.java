package main.musicPlayer;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import main.SpaceInvaders;
import org.apache.commons.io.FilenameUtils;
import main.FileHandling;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer
{
    // Properties
    private Thread playThread;

    public DoublyLinkedList playlist;
    public SpaceInvaders controller;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private Player musicPlayer;
    private boolean playing;

    public MusicPlayer (SpaceInvaders _controller) {
        // Load music files from directory
        loadMusicFiles();

        // Controller for updating UI
        controller = _controller;

//        playing = true;
        playThread = new Thread(runnablePlayMusic);
        playThread.start();
    }





    public void play() {
        System.out.println("Playing");
        playing = true;

        try
        {
            // Stream file to player
            fileInputStream = new FileInputStream(playlist.currentNode.file);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            musicPlayer = new Player(bufferedInputStream);
        }
        catch (JavaLayerException | FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("Stopping...");
        playing = false;
        musicPlayer.close();
    }

    public void next() {
        // Stop playing
        stop();

        if (playlist.currentNode.next != null)
        {
            playlist.currentNode = playlist.currentNode.next;
        }
        else
        {
            System.out.println("No next track!\nRestarting playlist...");

            // Reset to root node
            playlist.currentNode = playlist.root;
        }

        // Update playlist
        updatePlaylist();

        // Play the music
        play();
    }


    public void previous() {
        // Stop playing
        stop();

        if (playlist.currentNode.previous != null)
        {
            playlist.currentNode = playlist.currentNode.previous;
        }
        else
        {
            System.out.println("No previous track!");
        }

        // Update playlist
        updatePlaylist();

        // Play the music
        play();
    }


    private void updatePlaylist() {
        controller.selectPlaylistItem(playlist.currentNode.getFileName());
    }

    public void createPlaylist(File[] soundtrack) {

        System.out.println("Creating playlist...");
        int counter = 0;

        // If empty, initialise
        if (playlist == null)
        {
            // Create root node
            Node root = new Node(soundtrack[counter++]);

            // Create new doubly linked list with root node
            playlist = new DoublyLinkedList(root);
        }

        // Populate
        while (counter < soundtrack.length)
        {
            // Create new node
            Node newNode = new Node(soundtrack[counter++]);

            // Add node to linked list
            playlist.addLast(newNode);
        }
    }

    public void loadMusicFiles() {
        try
        {
            // Load music files from /music directory
            System.out.println("Searching music folder...");

            File folder = new File("music/");
            File[] foundFiles = folder.listFiles();

            // Create doubly linked list from array list of sound files (playlist)
            createPlaylist(foundFiles);
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
                while (true)
                {
                    // This little pause is vital, otherwise music wont play.... and i'm not sure why
                    Thread.sleep(50);
                    
                    if (playing && playlist.currentNode != null && musicPlayer != null)
                    {
                        System.out.println("Playing: " + playlist.currentNode.fileName);

                        // Play music
                        while (playing && !musicPlayer.isComplete())
                        {
                            musicPlayer.play(1);
                        }
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    };
}
