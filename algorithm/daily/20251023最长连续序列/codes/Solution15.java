import java.util.*;

public class Solution15 {
    public int longestConsecutive(int[] nums) {
        Map<Integer,Integer> hashMap = new HashMap<>();
        for(int i:nums){
            hashMap.put(i, 1);
        }
        int lastOne = -999999;
        boolean start = false;
        int max = 1;
        int count = 1;
        for(Map.Entry<Integer,Integer> entry:hashMap.entrySet()){
            System.out.println(entry.getKey());
            if(!start){
                lastOne = entry.getKey();
                start = true;
                continue;
            }

            if(lastOne == entry.getKey()-1){
                count ++;
            }else{
                count = 1;
            }
            if(count > max){
               max = count;
            }
            
            lastOne = entry.getKey();
        }
        return max;
    }
    public static void main(String[] args) {
        int[] nums = {100,4,200,1,3,2};
        Solution15 solution15 = new Solution15();
        int res = solution15.longestConsecutive(nums);
        System.out.println(res);
    }
}
