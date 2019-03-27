package sort.select;

import java.util.Arrays;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/1/20 Created
 */
public class TestSelectSort {
    public static void sort(int[] arr) {
        int length = arr.length;
        int value;
        int index;
        for (int i = 0; i < length; i++) {
            value = arr[i];
            index = i;
            for (int j = i+1; j < length; j++) {
                if (value > arr[j]) {
                    value = arr[j];
                    index = j;
                }
            }

            if (index != i) {
                swap(arr, i, index);
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
