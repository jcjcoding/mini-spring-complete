import java.util.Arrays;

public class Solution41 {
    public int minPairSum(int[] nums) {
        Arrays.sort(nums);
        int max = -1;
        for(int i = 0;i < nums.length/2 ;i++){
            int sum = nums[i] + nums[nums.length - i - 1];
            if(sum > max){
                max = sum;
            }
        }
        return max;
    }
}

