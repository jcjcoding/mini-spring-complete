public class Solution6 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] res = new int[n+m];
        int pointer1 = 0;
        int pointer2 = 0;
        int resNum = 0;
        /*int[] nums3Test1 = {5,6,9,0,0};
        int nums3Len = 3;
        int[] nums4Test2 = {2,3}; */
        while(pointer2 < n){
            int pointer1Start = pointer1;
            while (pointer1 < m && nums2[pointer2] > nums1[pointer1]) { 
                pointer1++;
            }
            for(int i = pointer1Start; i < pointer1; i++){
                res[resNum] = nums1[i];
                resNum++;
            }
            res[resNum] = nums2[pointer2];
            resNum++;
            pointer2++;
        }
        for(int i = resNum;i< m+n; i++){
            res[i] = nums1[pointer1];
            System.out.println("nums "+nums1[pointer1]+" po "+pointer1+" res "+res[resNum]+" resNum "+resNum);
            pointer1++; 
        }
        System.arraycopy(res, 0, nums1,0, n+m);
    }
    public static void main(String[] args) {
        int[] nums1Test1 = {1,2,3,0,0};
        int nums1Len = 3;
        int[] nums2Test2 = {5,6};
        int nums2Len = 2;
        int[] nums3Test1 = {5,6,9,0,0};
        int nums3Len = 3;
        int[] nums4Test2 = {2,3};
        int nums4Len = 2;
        Solution6 solution6 = new Solution6();
        solution6.merge(nums1Test1, nums1Len, nums2Test2, nums2Len);
        solution6.merge(nums3Test1, nums3Len, nums4Test2, nums4Len);
        for(int i:nums1Test1){
            System.out.print(i+"");
        }
        System.out.println("");
        for(int i:nums3Test1){
            System.out.print(i+"");
        }
    }
}
