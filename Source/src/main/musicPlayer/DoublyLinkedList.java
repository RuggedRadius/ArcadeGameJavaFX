package main.musicPlayer;

import java.io.File;
import java.util.ArrayList;

public class DoublyLinkedList
{


    public Node root;
    public Node currentNode;

    public DoublyLinkedList(Node _node) {
        root = _node;
        currentNode = root;
    }

    public void addLast(Node _node) {
        // Start from root node
        Node current = root;

        // Find last node
        while (current.next != null)
        {
            current = current.next;
        }

        // Add node
        current.next = _node;
        _node.previous = current;
    }
    public void addFirst(Node _node) {
        _node.next = root;
        root.previous = _node;

        root = _node;
    }

    public Node getNext() {
        return currentNode.next;
    }
    public Node getPrevious()  {
        return currentNode.previous;
    }

    public Node getFirst() {
        return root;
    }
    public void getLast() {}

    public String[] traverseFilenames()
    {
        ArrayList<String> filenames = new ArrayList<String>();

        Node current = root;

        while (current != null)
        {
            filenames.add(current.fileName);
            current = current.next;
        }

        return filenames.toArray(new String[filenames.size()]);
    }
}
