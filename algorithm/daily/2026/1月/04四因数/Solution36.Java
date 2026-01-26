class Solution36 {
    public int sumFourDivisors(int[] nums) {
        int ans = 0;
        for(int i:nums){
            ans += getFactor(i);
        }
        return ans;
    }
    public int getFactor(int num){
        int ans = 0;
        int count = 0;

        if(num == 0){
            return 0;
        }

        if(num == 1){
            return 0;
        }

        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                ans += i;
                ans += m/i;
                count ++;     
            }
            if(count >= 3){
                return 0;
            }
        }

        return ans;
    }
}