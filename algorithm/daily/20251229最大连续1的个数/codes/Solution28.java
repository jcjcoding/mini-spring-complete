public class Solution28 {
    public int findMaxConsecutiveOnes(int[] nums) {
        int ans = 0;
        int tmp = 0;
        int last = 0;
        for (int num : nums) {
            if(num == 1){
                tmp++;
            }else{
                if(tmp > ans){
                    ans = tmp;
                }
                tmp = 0;
            }
        }
        if(tmp > ans){
            ans = tmp;
        }
        return ans;
    }
}
