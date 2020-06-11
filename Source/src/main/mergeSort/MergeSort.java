package main.mergeSort;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.highScores.HighScore;

public class MergeSort
{
    public static ObservableList<HighScore> Sort_Scores(ObservableList<HighScore> salaries) {
        // Merge Sort
        // Check size of list
        if (salaries.size() <= 1)
        {
            return salaries;
        }

        ObservableList<HighScore> left = FXCollections.observableArrayList(); // new ObservableList<HighScore>();
        ObservableList<HighScore> right = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        int mid = salaries.size() / 2;
        for (int i = 0; i < mid; i++)
        {
            left.add(salaries.get(i));
        }
        for (int i = mid; i < salaries.size(); i++)
        {
            right.add(salaries.get(i));
        }

        left = Sort_Scores(left);
        right = Sort_Scores(right);
        return Merge_Scores(left, right);
    }
    static ObservableList<HighScore> Merge_Scores(ObservableList<HighScore> left, ObservableList<HighScore> right) {
        ObservableList<HighScore> result = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        while (left.size() > 0 || right.size() > 0)
        {
            if (left.size() > 0 && right.size() > 0)
            {
                // Compare elements
                if (left.get(0).getScore() > right.get(0).getScore())
                {
                    result.add(left.get(0));
                    left.remove(left.get(0));
                }
                else
                {
                    result.add(right.get(0));
                    right.remove(right.get(0));
                }
            }
            else if (left.size() > 0)
            {
                result.add(left.get(0));
                left.remove(left.get(0));
            }
            else if (right.size() > 0)
            {
                result.add(right.get(0));
                right.remove(right.get(0));
            }
        }

        // Return result
        return result;
    }

    public static ObservableList<HighScore> Sort_Names(ObservableList<HighScore> salaries) {
        // Merge Sort
        // Check size of list
        if (salaries.size() <= 1)
        {
            return salaries;
        }

        ObservableList<HighScore> left = FXCollections.observableArrayList(); // new ObservableList<HighScore>();
        ObservableList<HighScore> right = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        int mid = salaries.size() / 2;
        for (int i = 0; i < mid; i++)
        {
            left.add(salaries.get(i));
        }
        for (int i = mid; i < salaries.size(); i++)
        {
            right.add(salaries.get(i));
        }

        left = Sort_Names(left);
        right = Sort_Names(right);
        return Merge_Names(left, right);
    }
    static ObservableList<HighScore> Merge_Names(ObservableList<HighScore> left, ObservableList<HighScore> right) {
        ObservableList<HighScore> result = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        while (left.size() > 0 || right.size() > 0)
        {
            if (left.size() > 0 && right.size() > 0)
            {
                // Compare elements
                if (left.get(0).getPlayerName().toLowerCase().compareTo(right.get(0).getPlayerName().toLowerCase()) < 0)
                {
                    result.add(left.get(0));
                    left.remove(left.get(0));
                }
                else
                {
                    result.add(right.get(0));
                    right.remove(right.get(0));
                }
            }
            else if (left.size() > 0)
            {
                result.add(left.get(0));
                left.remove(left.get(0));
            }
            else if (right.size() > 0)
            {
                result.add(right.get(0));
                right.remove(right.get(0));
            }
        }

        // Return result
        return result;
    }

    public static ObservableList<HighScore> Sort_Date(ObservableList<HighScore> salaries) {
        // Merge Sort
        // Check size of list
        if (salaries.size() <= 1)
        {
            return salaries;
        }

        ObservableList<HighScore> left = FXCollections.observableArrayList(); // new ObservableList<HighScore>();
        ObservableList<HighScore> right = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        int mid = salaries.size() / 2;
        for (int i = 0; i < mid; i++)
        {
            left.add(salaries.get(i));
        }
        for (int i = mid; i < salaries.size(); i++)
        {
            right.add(salaries.get(i));
        }

        left = Sort_Date(left);
        right = Sort_Date(right);
        return Merge_Date(left, right);
    }
    static ObservableList<HighScore> Merge_Date(ObservableList<HighScore> left, ObservableList<HighScore> right) {
        ObservableList<HighScore> result = FXCollections.observableArrayList(); //new ObservableList<HighScore>();

        while (left.size() > 0 || right.size() > 0)
        {
            if (left.size() > 0 && right.size() > 0)
            {
                // Compare elements
                if (left.get(0).getDate().compareTo(right.get(0).getDate()) < 0)
                {
                    result.add(left.get(0));
                    left.remove(left.get(0));
                }
                else
                {
                    result.add(right.get(0));
                    right.remove(right.get(0));
                }
            }
            else if (left.size() > 0)
            {
                result.add(left.get(0));
                left.remove(left.get(0));
            }
            else if (right.size() > 0)
            {
                result.add(right.get(0));
                right.remove(right.get(0));
            }
        }

        // Return result
        return result;
    }

}
