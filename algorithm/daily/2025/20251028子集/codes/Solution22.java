import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        back(nums, 0, new ArrayList<>(), result);
        return result;
    }

    public void back(int[] nums,int index,List<Integer> path,List<List<Integer>> result){
        result.add(new ArrayList<>(path));
        for(int i=index;i<nums.length;i++){
            path.add(nums[i]);
            back(nums,i+1,path,result);
            path.remove(path.size()-1);
        }
    }
}