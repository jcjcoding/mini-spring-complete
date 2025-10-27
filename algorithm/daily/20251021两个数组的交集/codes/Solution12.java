
import java.util.HashMap;
import java.util.Map;

public class Solution12 {
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer,Integer> hashMap = new HashMap<>();
        int count = 0;
        for(int i:nums1){
            hashMap.put(i, 0);
        }
        for (int i : nums2) {
            if(hashMap.getOrDefault(i, -1) == 0 ){
                hashMap.put(i,1);
                count++;
            };
        }
        int[] Res =new int[count];
        int index = 0;
        for(Map.Entry<Integer,Integer> entryMap:hashMap.entrySet()){
            if(entryMap.getValue()==1){
                Res[index] = entryMap.getKey();
                index++;
            }
        }
        return Res;
    }  
    public static void main(String[] args) {
        int[] nums1Test = {1,3,4,2,4};
        int[] nums2Test = {4,2,4,1,23};
        Solution12 solution12 = new Solution12();
        int[] res = solution12.intersection(nums1Test, nums2Test);
        for(int i: res){
            System.out.print(i+" ");
        }
    }          
}
