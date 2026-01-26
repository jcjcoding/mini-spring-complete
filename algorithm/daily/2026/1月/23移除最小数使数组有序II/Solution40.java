import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Solution40 {
    public int minimumPairRemoval(int[] nums) {
        Map<Integer,Integer> indexToValueMap = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            indexToValueMap.put(i,nums[i]);
        }

        int time = 0;

        int[] minNums = new int[2];
        

        while(check(indexToValueMap)){
            List<Integer> sortedList = new ArrayList<>(indexToValueMap.keySet());
            long sum = Integer.MAX_VALUE;

            minNums[0] = sortedList.get(0);
            minNums[1] = sortedList.get(1);
            for(int i = 1; i < sortedList.size();i++){
                Integer prevKey = sortedList.get(i-1);
                Integer currKey = sortedList.get(i);
                Integer prevVal = indexToValueMap.get(prevKey);
                Integer currVal = indexToValueMap.get(currKey);
                if(sum > prevVal+currVal){
                    sum = prevVal+currVal;
                    minNums[0] = prevKey;
                    minNums[1] = currKey;
                }
            }
            Integer sumToGet = indexToValueMap.get(minNums[0])
                        +indexToValueMap.get(minNums[1]);
            indexToValueMap.put(minNums[0],sumToGet);
            indexToValueMap.remove(minNums[1]);
            time++;
        }
        
        return time;
    }

    public boolean check(Map<Integer,Integer> indexToValueMap){
        List<Integer> sortedList = new ArrayList<>(indexToValueMap.keySet());

        if(sortedList.size() < 2){
            return false;
        }

        for(int i = 1; i < sortedList.size();i++){
            Integer prevKey = sortedList.get(i-1);
            Integer currKey = sortedList.get(i);
            Integer prevVal = indexToValueMap.get(prevKey);
            Integer currVal = indexToValueMap.get(currKey);
            if(currVal < prevVal){
                return true;
            }
        }
        return false;
    }
}
