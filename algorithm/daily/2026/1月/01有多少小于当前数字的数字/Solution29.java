class Solution29 {
     public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] ans = new int[nums.length];
        for(int i = 0;i < nums.length;i++){
            for(int j = i+1;j < nums.length;j++){
                if(nums[i] > nums[j]){
                    ans[i]+=1;
                }
                if(nums[i] < nums[j]){
                    ans[j]+=1;
                }
            }
        }
        return ans;
    }
}