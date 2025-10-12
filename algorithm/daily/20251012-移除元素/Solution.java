class Solution {
    public int removeElement(int[] nums, int val) {
        int index = 0;
        int[] newNums =new int[nums.length]; 
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                newNums[index] = nums[i];
                index++;
            }
        }
        System.arraycopy(newNums, 0, nums, 0, index);
        return index;
    }

    public static void main(String[] args) {
        Solution solution = new Solution(); 

        // 测试用例1
        int[] nums1 = {3, 3, 2, 2, 3};
        int val1 = 3;
        int length1 = solution.removeElement(nums1, val1);
        System.out.println(length1);
        for(int num:nums1){
            System.out.print(num+" ");
        }
        System.out.println(); 

        // 测试用例2
        int[] nums2 = {5, 2, 3, 34, 4, 21, 33, 2, 3, 2};
        int val2 = 2; // nums2长度为10，val2=10
        int length2 = solution.removeElement(nums2, val2);
        System.out.println("测试用例2新长度: " + length2);
        for(int num:nums2){
            System.out.print(num+" ");
        }
    }
}