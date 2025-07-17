import java.util.*;
import java.util.concurrent.*;

class Solution {
    public int arraySum(int[] arr) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i];
        }
        return total;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        int arr[] = {1, 2, 3, 4, 5, 6};
        int mid = arr.length / 2;

        // Split array into two halves
        int[] firstHalf = Arrays.copyOfRange(arr, 0, mid);  
        int[] secondHalf = Arrays.copyOfRange(arr, mid, arr.length); 

        Solution sol = new Solution();

        // Create an ExecutorService that uses virtual threads
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        // Create 2 tasks that compute the sum of each half
        Callable<Integer> task1 = () -> sol.arraySum(firstHalf);  // Callable returns a value
        Callable<Integer> task2 = () -> sol.arraySum(secondHalf);

        // Submit tasks to executor and get Future objects
        Future<Integer> future1 = executor.submit(task1);  
        Future<Integer> future2 = executor.submit(task2);  
        // Get the results from the futures main thread waits here
        int total = future1.get() + future2.get();

        System.out.println(total);  

        executor.shutdown();  // Clean up resources
    }
}
