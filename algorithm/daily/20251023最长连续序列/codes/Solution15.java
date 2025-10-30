import java.util.*;

public class Solution15 {
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> hashMap = new HashMap<>();
        for (int num : nums) {
            hashMap.put(num, 1);
        }

        List<Integer> keys = new ArrayList<>(hashMap.keySet());
        Collections.sort(keys);

        int maxLen = 1; 
        int currentLen = 1; 

        for (int i = 1; i < keys.size(); i++) {
            
            if (keys.get(i) == keys.get(i - 1) + 1) {
                currentLen++;
                
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                }
            } else {
                currentLen = 1;
            }
        }

        return maxLen;
    }
    public static void main(String[] args) {
        int[] nums = {100,4,200,1,3,2};
        Solution15 solution15 = new Solution15();
        int res = solution15.longestConsecutive(nums);
        System.out.println(res);
    }
}
