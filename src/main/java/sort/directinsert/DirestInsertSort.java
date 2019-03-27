package sort.directinsert;

/**
 * @Description:
 * @author: QuLiang
 * @since: 2019/2/26 Created
 */
public class DirestInsertSort {
    public static int[] sort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        int len = nums.length;
        int[] sorted = new int[len];
        for (int i = 0; i < len; i++) {
            sorted[i] = nums[i];
        }

        int j, k;
        int temp;
        for (j = 1; j < len; j++) {
            for (k = j-1; k >= 0; k--) {
                if (sorted[k+1] >= sorted[k]) {
                    break;
                }

                temp = sorted[k+1];
                sorted[k+1] = sorted[k];
                sorted[k] = temp;
            }

        }

        return sorted;
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
