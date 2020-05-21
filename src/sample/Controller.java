package sample;

import com.sun.corba.se.impl.orb.PrefixParserAction;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller extends Pane implements KeyListener
{
    // Attributes / Properties
    GameObject test;

    // Constructor
    public Controller()
    {
        System.out.println("Controller is being constructed.");
    }

    // Automatically called after constructor
    public void initialize()
    {
        System.out.println("Loading game data...");
        // Loading all game data here
        // ...

        // Add test gameobject with image

        try
        {
            test = new GameObject();
            test.img = new Image(new FileInputStream("data/test.jpg"));
            System.out.println("Test added.");
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Image not found.");
        }
    }



    public void paintScreen()
    {
        // Update the pane
        update();
    }

    // Called every 'frame'
    public void update()
    {

    }

    // Key Press Events
    public void keyPressed(KeyEvent k)
    {
        System.out.println("Key pressed.");
    }
    public void keyReleased(KeyEvent k)
    {
        System.out.println("Key released.");
    }
    public void keyTyped(KeyEvent k)
    {
        System.out.println("Key typed.");
    }

    public void moveTest(KeyEvent k)
    {
        switch (k.getKeyChar())
        {
            case 'w':
                test.setY(test.getY() - 5);

                break;

            case 's':
                test.setY(test.getY() + 5);

                break;

            case 'a':
                test.setX(test.getX() - 5);

                break;

            case 'd':
                test.setX(test.getX() + 5);

                break;
        }
    }
}
