
import java.util.HashMap;
import java.util.Map;

public class Solution28 {
    public int[] findErrorNums(int[] nums) {
        Map<Integer,Integer> hashmapA = new HashMap<>();
        for (int i = 1; i <= nums.length; i++) {
            int count = hashmapA.getOrDefault(i, 0);
            hashmapA.put(i,count+1);
        }
        
        for(int j:nums){
            int count = hashmapA.getOrDefault(j, 0);
            hashmapA.put(j,count+1);
        }

        int[] ans = new int[2];

        for(Map.Entry<Integer,Integer> entryA:hashmapA.entrySet()){
            if(entryA.getValue() == 1){
                ans[1]=entryA.getKey();
            }
            if(entryA.getValue() == 3){
                ans[0]=entryA.getKey();
            }
        }
        return ans;
    }
}
