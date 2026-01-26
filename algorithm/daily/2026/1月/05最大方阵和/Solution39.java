public class Solution39 {
    public long maxMatrixSum(int[][] matrix) {
        long ans = 0;
        int count = 0;
        int to = 1000000000;
        for (int idx = 0; idx < matrix.length; idx++) {
            for(int i = 0; i < matrix[0].length; i++){
                if(matrix[idx][i] <= 0){
                    count++;
                    matrix[idx][i] *= -1; 
                }
                if(matrix[idx][i] < to){
                    to = matrix[idx][i];
                }
        
                ans += matrix[idx][i];
            }
        }
        if(count %2 !=0){
            ans -= 2*to;
        }
        return  ans;
    }
}
