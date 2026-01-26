import java.util.ArrayDeque;
import java.util.Deque;

public class Solution33 {
    public int evalRPN(String[] tokens) {
        Deque<Integer> deque = new ArrayDeque<>();

        int ans = 0;
        if(tokens.length == 1){
            return Integer.parseInt(tokens[0]);
        }
        for (String token : tokens) {
            if(token.equals("+") || token.equals("-") 
            || token.equals("*") || token.equals("/")){
                int num1 = deque.pop();
                int num2 = deque.pop();
                ans = calculate(token, num2, num1);
                deque.push(ans);
            }else{
                deque.push(Integer.parseInt(token));
            }
        }
        return ans;
    }

    public int calculate(String op,int a,int b){
        switch (op) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new AssertionError();
        }
    }
}
