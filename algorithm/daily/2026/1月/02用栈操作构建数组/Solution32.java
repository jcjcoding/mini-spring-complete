import java.util.ArrayList;
import java.util.List;

class Solution32 {
    public List<String> buildArray(int[] target, int n) {
        String push = "Push";
        String pop = "Pop";
        List<String> ans = new ArrayList<>();
        int idx = 0;
        int end = target.length; 
        for(int i = 1; i <= n; i++){
            if(idx >= end) return ans;
            int need = target[idx];
            if(need == i){
                ans.add(push);
                idx++;
            }else{
                ans.add(push);
                ans.add(pop);
            }
        }
        return ans;
    }
}