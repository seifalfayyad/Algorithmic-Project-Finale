import java.util.*;

public class SortingAndSearching {

    //  INSERTION SORT

    public static void insertionSort(int[] arr) {
        int n = arr.length;

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          INSERTION SORT              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Initial Array : " + Arrays.toString(arr));
        System.out.println();

        // Outer loop: i is the index of the element being inserted
        // into the sorted region [0 .. i-1]
        for (int i = 1; i < n; i++) {

            int key = arr[i];   // element to insert into sorted region
            int j   = i - 1;   // start comparing from rightmost sorted element

            // Inner loop: shift elements one position to the RIGHT
            // as long as they are greater than 'key'
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];    // shift element right
                j--;
            }

            // Drop 'key' into the gap that was opened up
            arr[j + 1] = key;

            // Print the array state after each pass
            System.out.println("  Pass " + i + " (key=" + key + ") : "
                               + Arrays.toString(arr));
        }

        System.out.println();
        System.out.println("  Sorted Array  : " + Arrays.toString(arr));
        System.out.println();
    }

    //  BINARY SEARCH

    public static int binarySearch(int[] arr, int target) {
        int low  = 0;
        int high = arr.length - 1;
        int step = 1;

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           BINARY SEARCH              ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("  Array  : " + Arrays.toString(arr));
        System.out.println("  Target : " + target);
        System.out.println();

        while (low <= high) {

            // Use (low + (high - low) / 2) instead of (low + high) / 2
            // to prevent integer overflow when indices are very large
            int mid = low + (high - low) / 2;

            System.out.println("  Step " + step + " : low=" + low
                               + ", high=" + high + ", mid=" + mid
                               + ", arr[mid]=" + arr[mid]);

            if (arr[mid] == target) {
                // ── FOUND ────────────────────────────────────────────────
                System.out.println();
                System.out.println("  ✔ Target " + target
                                   + " found at index " + mid);
                return mid;

            } else if (arr[mid] < target) {
                // Target is in the RIGHT half — discard left half
                System.out.println("    arr[mid] < target → search RIGHT half");
                low = mid + 1;

            } else {
                // Target is in the LEFT half — discard right half
                System.out.println("    arr[mid] > target → search LEFT half");
                high = mid - 1;
            }

            step++;
        }

        // ── NOT FOUND ────────────────────────────────────────────────────
        System.out.println();
        System.out.println("  ✘ Target " + target + " not found in array.");
        return -1;
    }

    //  DRIVER METHOD
    public static void main(String[] args) {

        // ── TEST 1: Insertion Sort ───────────────────────────────────────
        int[] arr = {8, 3, 5, 1, 9, 2};
        insertionSort(arr);

        // ── TEST 2: Binary Search — target EXISTS ────────────────────────
        System.out.println("────────────────────────────────────────");
        int index1 = binarySearch(arr, 5);
        System.out.println("  Result: Binary Search (5) → index " + index1);

        // ── TEST 3: Binary Search — target DOES NOT EXIST ────────────────
        System.out.println();
        System.out.println("────────────────────────────────────────");
        int index2 = binarySearch(arr, 7);
        System.out.println("  Result: Binary Search (7) → index " + index2);
    }
}