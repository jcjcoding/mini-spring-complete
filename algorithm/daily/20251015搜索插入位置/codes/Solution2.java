class Solution2 {
    //二分
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while(left <= right){
            int mid = left + (right - left) / 2;
            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }
    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        int[] testNums1 = {1,3,5,25,34,67};
        int result1 = solution.searchInsert(testNums1, 5);
        int result2 = solution.searchInsert(testNums1, 7);
        System.out.println(result1+" "+result2);
    }
}