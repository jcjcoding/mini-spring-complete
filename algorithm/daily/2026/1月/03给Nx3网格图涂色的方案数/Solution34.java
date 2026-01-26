public class Solution34 {
    public int numOfWays(int n) {
        int[] states = {121, 123, 131, 132, 212, 213, 231, 232, 312, 313, 321, 323};
        long[][] dp = new long[n+1][12];
        for (int i = 0;i < 12; i++){ 
            dp[1][i] = 1;
        }
        long ans = 0;
        for(int cen = 2;cen<= n;cen++){
            for(int s1 = 0;s1 < 12;s1 ++){
                dp[cen][s1] = 0;
                for(int s2= 0;s2 < 12;s2 ++){
                    int state1 = states[s1];
                    int state2 = states[s2];
                    if(isStateLegal(state1, state2)){
                        dp[cen][s1] += dp[cen-1][s2];
                    }
                }
                
            }
        }
        for (int i = 0;i < 12; i++){ 
            ans += dp[n][i];
            System.out.println(dp[n][i]);
        }
        ans %= (10^9 + 7);
        return (int)ans;
    }

    public boolean isStateLegal (int state1, int state2){
        while(state1 > 0&& state2 > 0){
            if(state1 % 10 == state2 % 10) return false;
            state1 /= 10;
            state2 /= 10;
        }
        return true;
    }

    public int countLegal (int state){
        int[] states = {010, 012, 020, 021, 101, 102, 120, 121, 201, 202, 210, 212};
        int ans = 0;
        for(int s : states){
            if(isStateLegal(state, s)) ans++;
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution34 solution34 = new Solution34();
        int n = 1;
        int m = solution34.numOfWays(n);
        System.out.println("");
    }
}
