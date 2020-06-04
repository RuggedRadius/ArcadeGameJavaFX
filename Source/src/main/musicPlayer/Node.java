package main;

public class Node
{
    String playerName;
    int score;
    int level;

    Node previous;
    Node next;

    public Node(String _playerName, int _score, int _level)
    {
        playerName = _playerName;
        score = _score;
        level = _level;
    }
}
