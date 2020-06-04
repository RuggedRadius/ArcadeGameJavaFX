package main.musicPlayer;

import java.io.File;

public class Node
{
    String fileName;
    String path;
    File file;

    Node previous;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    Node next;

    public Node(File file)
    {
        this.file = file;
        fileName = file.getName();
        path = file.getPath();
    }
}
