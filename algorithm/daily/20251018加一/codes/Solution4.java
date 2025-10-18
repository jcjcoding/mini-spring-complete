public class Solution4 {
    public int[] plusOne(int[] digits) {
        int index = digits.length-1;
        if(digits[index] != 9){
            digits[index] ++;
            return digits;
        }else{
            int newIndex = index;
            while(newIndex > 0 && digits[newIndex] == 9){
                newIndex--;
            }
            int[] res;
            if(newIndex > 0 || (newIndex == 0 && digits[newIndex] !=9 )){
                res = new int[digits.length];
                System.arraycopy(digits, 0, res, 0, index);
                for(int i = index;i > newIndex;i--){
                    res[i] = 0;
                }
                res[newIndex] ++;
                return res;
            }else{
                res = new int[digits.length+1];
                res[0] = 1;
                return res;
            }
        }
    }

    public static void main(String[] args) {
        int[] testInts1 = {0};
        int[] testInts2 = {3,4,5,2,1};
        int[] testInts3 = {9};
        int[] testInts4 = {1,4,9};
        int[] testInts5 = {4,9,9};
        int[] testInts6 = {9,9};
        int[][] allArrays ={testInts1,testInts2,testInts3,testInts4,testInts5,testInts6};
        Solution4 solution4 = new Solution4();
        for(int[] test:allArrays){
            int[] res = solution4.plusOne(test);
            for(int i:res){
                System.out.print(i+" ");
            }
            System.out.println('\n');
        }
    }
}
