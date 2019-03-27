package sort.bigestheap;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
public class BigestHeapSort {
    public static int[] sort(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }

        int len = nums.length;
        int temp;
        for (int i = len; i > 1; i--) {
            maxHeapFor(nums, i);
            temp = nums[0];
            nums[0] = nums[i-1];
            nums[i-1] = temp;
        }

        return nums;
    }

    public static void maxHeapFor(int[] nums, int n) {
        int temp;
        for (int i = n / 2 - 1; i >= 0; i--) {
            if (nums[2*i+1] > nums[i]) {
                temp = nums[i];
                nums[i] = nums[2*i+1];
                nums[2*i+1] = temp;
            }

            if ((2*i+2) <= (n-1) && nums[2*i+2] > nums[i]) {
                temp = nums[i];
                nums[i] = nums[2*i+2];
                nums[2*i+2] = temp;
            }
        }
    }

    public static void showArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] nums = new int[] {2, -1, 3, 4, 10, -8, 6};
        showArray(sort(nums));
    }
}
