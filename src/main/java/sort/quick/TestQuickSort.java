package sort.quick;

import java.util.Arrays;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/20 Created
 */
public class TestQuickSort {
    public static void sort(int[] arr, int low, int high) {
        if (low > high) {
            return;
        }

        int i, j;
        int temp, t;

        i = low;
        j = high;
        temp = arr[low];
        while (i < j) {
            //
            while (arr[j] >= temp && i < j) {
                j--;
            }

            //
            while (arr[i] <= temp && i < j) {
                i++;
            }

            if (i < j) {
                t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }

        arr[low] = arr[i];
        arr[i] = temp;

        sort(arr, low, i-1);
        sort(arr, i+1, high);
    }

    public static void main(String[] args) {
        int[] arr = {6, 3, 2, 5, 1, 8, 4, 10};
        System.out.println("Before:" + Arrays.toString(arr));
        sort(arr, 0, arr.length-1);
        System.out.println("After:" + Arrays.toString(arr));
    }
}
