import java.util.ArrayList;
import java.util.List;

public class Solution30 {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        boolean[] exist = new boolean[nums.length+1]; 
        for(int i:nums){
            exist[i] = true;
        }
        for (int i = 1; i <= nums.length; i++) {
            if(exist[i] == false) {
                ans.add(i);
            }
           
        }
        return ans;
    }
}
