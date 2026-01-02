
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Solution14 {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer,Integer> hashmapA = new HashMap<>();
        Map<Integer,Integer> hashmapB = new HashMap<>();

        for(int i:nums1){
            for(int j:nums2){
                int sum = i+j;
                int count = hashmapA.getOrDefault(sum, 0);
                hashmapA.put(sum,count+1);
            }
        }

        for(int i:nums3){
            for(int j:nums4){
                int sum = i+j;
                int count = hashmapB.getOrDefault(sum, 0);
                hashmapB.put(sum,count+1);
            }
        } 

        int res = 0;
        for(Map.Entry<Integer,Integer> entryA:hashmapA.entrySet()){
            /* 
            for(Map.Entry<Integer,Integer> entryB:hashmapB.entrySet()){
                if(entryA.getKey() + entryB.getKey() ==0){
                    res += entryA.getValue()*entryB.getValue();
                }
            }这里脑子犯混用了遍历当数组用了
            */
            if(hashmapB.getOrDefault(-entryA.getKey(), 0) != 0){
                res += hashmapB.get(-entryA.getKey()) * entryA.getValue();
            }
        }
        return res;
    }
    /**抄的内容还是很有启发的
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : nums1) {
            for (int j : nums2) {
                map.put(i + j, map.getOrDefault(i + j, 0) + 1);
            }
        }
        int sum = 0;
        for (int i : nums3) {
            for (int j : nums4) {
                sum += map.getOrDefault(-i - j, 0);
            }
        }
        return sum;
    }
} 
*/
    public static void main(String[] args) {
        int[] nums1 = {1,1};
        int[] nums2 = {-1,-1};
        int[] nums3 = {-1,-1};
        int[] nums4 = {1,1};
        Solution14 solution14 = new Solution14();
        int res = solution14.fourSumCount(nums1, nums2, nums3, nums4);
        System.out.println(res);
    }
}
