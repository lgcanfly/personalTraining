package sort.bubble;

import java.util.Arrays;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/20 Created
 */
public class TestBubbleSort {
    public static void sort(int[] arr) {
        int length = arr.length;

        for (int i = length-1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(arr, j, j+1);
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {2, 3, 2, 5, 1, 8, 4, 10};
        System.out.println("Before:" + Arrays.toString(arr));
        sort(arr);
        System.out.println("After:" + Arrays.toString(arr));
    }
}
