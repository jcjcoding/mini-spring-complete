public class Solution20 {
    public int fib(int n) {
        if(n<=1){
            return n;
        }else{
            return fib(n-1)+fib(n-2);
        }
    }
    public static void main(String[] args) {
        Solution20 solution20 = new Solution20();
        int res = solution20.fib(10);
        System.out.println(res);
    }
}
