import java.util.HashSet;
import java.util.Set;

public class Solution31 {
    public int repeatedNTimes(int[] nums) {
        Set<Integer> existSet = new HashSet<>();
        for (int elem : nums) {
            if(existSet.contains(elem)){
                return elem;
            }else{
                existSet.add(elem);
            }
        }
        return 0;
    }
}
