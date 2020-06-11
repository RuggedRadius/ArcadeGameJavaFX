package main.musicPlayer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import main.SpaceInvaders;

import java.io.*;

public class MusicPlayer
{
    // Properties
    private Thread playThread;

    public DoublyLinkedList playlist;
    public SpaceInvaders controller;

    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private AdvancedPlayer musicPlayer;
    private boolean playing;

    public MusicPlayer (SpaceInvaders _controller) {
        // Load music files from directory
        loadMusicFiles();

        // Controller for updating UI
        controller = _controller;

        // Start music loop
        play();
    }





    public void play() {

        if (!playing) {
            if (playThread == null) {

                playThread = new Thread(runnableMusicLoop);
            }
            playThread.start();
            playing = true;
        }
    }

    public void stop() {
        // Pack up
        try {
            playing = false;

            musicPlayer.close();
            fileInputStream.close();
            bufferedInputStream.close();

            playThread.interrupt();
            playThread = null;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
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

    Runnable runnableMusicLoop = new Runnable() {
        @Override
        public void run() {
            while (playing) {
                try {
                    // Make sure current node isnt empty
                    if (playlist.currentNode == null) {
                        System.out.println("Empty node, starting from root node");
                        playlist.currentNode = playlist.getFirst();
                    }

                    System.out.println("Playing: " + playlist.currentNode.fileName);

                    // Setup
                    fileInputStream = new FileInputStream(playlist.currentNode.file);
                    bufferedInputStream = new BufferedInputStream(fileInputStream);
                    musicPlayer = new AdvancedPlayer(bufferedInputStream);

                    // Set handler on new player
                    musicPlayer.setPlayBackListener(new PlaybackListener() {
                        @Override
                        public void playbackFinished(PlaybackEvent event) {
                            playlist.currentNode = playlist.currentNode.getNext();
                        }
                    });

                    // Play the music
                    musicPlayer.play();

                    // Pack up
                    fileInputStream.close();
                    bufferedInputStream.close();
                    musicPlayer.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
