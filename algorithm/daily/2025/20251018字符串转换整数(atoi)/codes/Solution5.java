public class Solution5 {
    public int myAtoi(String s) {
        long res = 0;
        boolean isZFfind = false;
        boolean isZF = true;
        boolean isNumBegin = false;
        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            if(!isNumBegin &&ch == ' '){
                continue;
            }

            if(!isNumBegin && !isZFfind &&(ch == '+' || ch == '-')){
                if(ch == '-'){
                    isZF = false;        
                }
                isNumBegin = true;
                isZFfind = true;
                continue;
            }

            if(ch < '0' || ch >  '9'){
                break;
            }

            if(ch>= '0' && ch <= '9'){
                isNumBegin = true;
                res*=10;
                res+=ch-'0';
            }
            long resTest = res;  
            if(!isZF){
                resTest*=-1;
            }
            if(resTest > 2147483647){
            return 2147483647;
            }
            
            if(resTest < -2147483648){
                return -2147483648;
            }
        }
        if(!isZF){
            res*=-1;
        }
        if(res > 2147483647){
            return 2147483647;
        }
        if(res < -2147483648){
            return -2147483648;
        }
        return (int)res;
    }

    public static void main(String[] args) {
        String testStr1 = "";
        String testStr2 = "   ";
        String testStr3 = "   4";
        String testStr4 = "   -4";
        String testStr5 = "   +4";
        String testStr6 = "   0+6";
        String testStr7 = "   0+h5607";
        String testStr8 = "   05h456";
        String testStr9 = "   0000-35h45";
        String testStr10 ="   2147483648";
        String[] allTest={testStr1,testStr2,testStr3,testStr4,testStr5,testStr6,testStr7,testStr8,testStr9,testStr10};
        Solution5 solution5 = new Solution5();
        for(String test:allTest){
            int res = solution5.myAtoi(test);
            System.out.println(res);
        }
    }
}
