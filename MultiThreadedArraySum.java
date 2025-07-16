import java.util.*;
class Solution {
	public int arraySum(int[] arr) {
		int total=0;
		for(int i=0; i<arr.length; i++) {
			total+=arr[i];
		}
		return total;
	}
}

public class Main
{
	public static void main(String[] args) throws InterruptedException {
		int arr[]= {1,2,3,4,5,6};
		int mid = arr.length/2;
		int[] firstHalf = Arrays.copyOfRange(arr,0,mid);
		int[] secondHalf = Arrays.copyOfRange(arr,mid,arr.length);
		Solution sol = new Solution();
		final int[] sum1 = new int[1];
		final int[] sum2 = new int[1];
		Thread t1 = new Thread(()-> sum1[0]=sol.arraySum(firstHalf));
		Thread t2 = new Thread(()-> sum2[0]=sol.arraySum(secondHalf));
		t1.start();
		t2.start();
		t2.join();
		int total = sum1[0]+sum2[0];
		System.out.println(total);

	}
}
