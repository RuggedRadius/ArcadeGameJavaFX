package tafeInvaders;

public class DoublyLinkedList
{


    public Node root;
    public Node currentNode;

    public DoublyLinkedList(Node _node)
    {
        root = _node;
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
    public void getPrevious() {}

    public Node getFirst() {
        return root;
    }
    public void getLast() {}

    public void testTraverse()
    {
        Node current = root;

        while (current.next != null)
        {
            System.out.println(current.playerName + " - " + current.score);
            current = current.next;
        }
    }
}
