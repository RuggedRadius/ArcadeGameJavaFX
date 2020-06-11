package main.binarySearch;

public class BinarySearch {

    public static int binarySearch(String arr[], int first, int last, String key)
    {
        System.out.println("Binary searching");

        if (last>=first)
        {
            int mid = first + (last - first)/2;
            if (arr[mid].equals(key))
            {
                return mid;
            }
            if (arr[mid].compareTo(key) >= 0) {
                return binarySearch(arr, first, mid-1, key);//search in left subarray
            }
            else {
                return binarySearch(arr, mid+1, last, key);//search in right subarray
            }
        }
        return -1;
    }
}
