public class Solution27 {
    public int[] shuffle(int[] nums, int n) {
        int[] ans = new int[n*2];
        for (int i = 0;i < n*2; i++) {
            if (i%2 == 0) ans[i] = nums[i/2];    
            else ans[i] = nums[i/2+n];        
        }
        return ans;
    }
}