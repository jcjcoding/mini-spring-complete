
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution43 {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        int min = Integer.MAX_VALUE;
        int[] cha = new int[arr.length];

        for (int idx = 0; idx < arr.length; idx++) {
            cha[idx] = arr[idx + 1] - arr[idx];
            if(cha[idx] < min){
                min = cha[idx];
            }
        }

        List<List<Integer>> ans = new ArrayList<>();

        for (int idx = 0; idx < arr.length; idx++) {
            if(cha[idx] == min ){
                List<Integer> tmp = List.of(arr[idx],arr[idx+1]);
                ans.add(tmp);  
            }   
        }
        
        return ans;
    }
}