package main;

import java.io.File;
import javafx.stage.FileChooser;

public class FileHandling
{
    public static void saveDataFile()
    {

    }

    public static void loadDataFile(File file)
    {

    }

    public static File getLocalFile() {

        FileChooser mrChoosey = new FileChooser();
        return  mrChoosey.showOpenDialog(null);
    }

//    public static File[] getMusicFiles() {
//
//
//        return listOfFiles;
//    }
}
