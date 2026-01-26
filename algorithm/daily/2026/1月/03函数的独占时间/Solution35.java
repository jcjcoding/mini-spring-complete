import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

//这里想用当然了，用c++的map理解java的map了，
//而且开始想把start和end状态入栈但是后面发现没必要，所以其实栈里面存数组就行了
public class Solution35 {
    public int[] exclusiveTime(int n, List<String> logs) {
        int[] ans = new int[n];
        Deque<Map<Integer, Integer>> stack = new ArrayDeque<>();

        for (String log : logs) {
            String[] str = log.split(":");
            int functionId = Integer.parseInt(str[0]);
            boolean isStart = "start".equals(str[1]);
            int timestamp = Integer.parseInt(str[2]);

            if (isStart) {
                Map<Integer, Integer> funcInfo = new HashMap<>();
                funcInfo.put(0, functionId);  
                funcInfo.put(1, timestamp);   
                funcInfo.put(2, 0);           
                stack.push(funcInfo);
            } else {
                Map<Integer, Integer> funcInfo = stack.pop();
                int startId = funcInfo.get(0);
                int startTime = funcInfo.get(1);
                int deductTime = funcInfo.get(2);


                int totalTime = timestamp - startTime + 1;
                int exclusiveTime = totalTime - deductTime;
                ans[startId] += exclusiveTime;

    
                if (!stack.isEmpty()) {
                    Map<Integer, Integer> topFunc = stack.peek();
                    topFunc.put(2, topFunc.get(2) + totalTime);
                }
            }
        }
        return ans;
    }
}