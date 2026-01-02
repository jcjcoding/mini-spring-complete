import java.util.HashMap;
import java.util.Map;
public class Solution10 {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> hashMap = new HashMap<>();
        int index = 0;
        int[] res = new int[2];
        for (int i : nums) {
            Integer needKey = (Integer)target - i;
            Integer ans = hashMap.get(needKey);
            if(ans != null){
                res[0] = ans;
                res[1] = index;
                return res;
            }
            hashMap.put(i,index);
            index++;    
        }
        
        
        return null;
    }
    public static void main(String[] args) {
        int[] nums1Test = {3,3};
        int target1Test = 6;
        Solution10 solution10 = new Solution10();
        int[] res = solution10.twoSum(nums1Test, target1Test);
        for(int i:res){
            System.out.println(i);
        }
    }
}
