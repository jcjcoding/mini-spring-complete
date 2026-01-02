class Solution1 {
    public void reverseString(char[] s) {
        int indexL = 0;
        int indexR = s.length-1;
        while (indexL < indexR) {
            char temp = ' ';
            temp = s[indexL];
            s[indexL] = s[indexR];
            s[indexR] = temp;
            indexL++;
            indexR--;
        }
    }
    public static void main(String[] args) {
        Solution1 solution = new Solution1();
        char[] test1 = {'3','f','4','g','f'};
        solution.reverseString(test1);
        for(char temp:test1){
            System.out.print(temp);
        }
    }
}