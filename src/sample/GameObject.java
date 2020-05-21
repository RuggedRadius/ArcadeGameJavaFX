package sample;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import javax.swing.*;
import java.awt.*;

public class GameObject extends Rectangle
{
    Image img;

    public GameObject ()
    {
        setX(50);
        setY(75);
        setWidth(50);
        setHeight(50);
    }

    public void updateImage (Image _img)
    {
        img = _img;
    }
}
