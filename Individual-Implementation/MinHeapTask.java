import java.util.*;

public class MinHeapTask {

    static class MinHeap {

        private int[] heap;
        private int   size;
        private static final int CAPACITY = 100;

        public MinHeap() {
            heap = new int[CAPACITY];
            size = 0;
        }

        private int parent(int i)     { return (i - 1) / 2; }
        private int leftChild(int i)  { return 2 * i + 1;   }
        private int rightChild(int i) { return 2 * i + 2;   }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i]  = heap[j];
            heap[j]  = temp;
        }

        public void insert(int value) {
            heap[size] = value;
            int i = size;
            size++;

            // heapify UP — ensures smallest bubbles to top
            while (i > 0 && heap[i] < heap[parent(i)]) {
                swap(i, parent(i));
                i = parent(i);
            }
            System.out.println("  insert(" + value + ")   → heap: "
                               + heapToString());
        }

        // extractMin() = equivalent of poll() in Java's PriorityQueue
        public int extractMin() {
            int min = heap[0];      // smallest is ALWAYS at root (index 0)
            size--;
            heap[0] = heap[size];   // move last element to root
            heapifyDown(0);         // restore heap order
            System.out.println("  extractMin()  → removed: " + min
                               + " | heap: " + heapToString());
            return min;
        }

        private void heapifyDown(int i) {
            int smallest = i;
            int left     = leftChild(i);
            int right    = rightChild(i);

            if (left  < size && heap[left]  < heap[smallest]) smallest = left;
            if (right < size && heap[right] < heap[smallest]) smallest = right;

            if (smallest != i) {
                swap(i, smallest);
                heapifyDown(smallest);
            }
        }

        private String heapToString() {
            if (size == 0) return "[]";
            StringBuilder sb = new StringBuilder("[");
            for (int k = 0; k < size; k++) {
                sb.append(heap[k]);
                if (k < size - 1) sb.append(", ");
            }
            return sb.append("]").toString();
        }
    }

    public static void main(String[] args) {

        MinHeap heap = new MinHeap();
        heap.insert(10);
        heap.insert(3);
        heap.insert(15);

        System.out.println("Min-Heap Extract Min: " + heap.extractMin());
    }
}