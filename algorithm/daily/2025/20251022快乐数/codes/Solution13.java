
import java.util.HashMap;
import java.util.Map;

public class Solution13 {
    public boolean isHappy(int n) {
        Map<Integer,Integer> hashMap = new HashMap<>();
        int num = n;
        while (true) { 
            if(num == 1){
                return true;
            }
            if(hashMap.getOrDefault(num, -1) == 1){
                return false;
            }else{
                hashMap.put(num, 1);
            }
            int count = 0;
            while(num > 0){
                int tmp = num % 10;
                num /= 10;
                count += tmp*tmp;
            } 
            num = count;
        }
        
    }
    public static void main(String[] args) {
        int num1Test = 19;
        int num2Test = 2;
        int num3Test = 19;
        int num4Test = 789;
        int[] numsTest = {num1Test,num2Test,num3Test,num4Test};
        Solution13 solution13 = new Solution13();
        for (int i = 0; i < 4; i++) {
            if(solution13.isHappy(numsTest[i])){
                System.out.println(i);
            }
        }
    }
}
