class Solution3 {
    public String longestCommonPrefix(String[] strs) {
        int index = 0;
        String res = "";
        char tmp = strs[0].charAt(0);
        while(true){
            boolean isTrue = true;
            for (String str:strs){
                if (str.charAt(index) != tmp) {
                    isTrue = false;
                }
            }
            if(isTrue){
                res += tmp;
                index ++;
                tmp = strs[0].charAt(index);
            }else{
                break;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        String[] strsTest = {"flow","flosdad","flwadaw"};
        Solution3 solution3 = new Solution3();
        String res = solution3.longestCommonPrefix(strsTest);
        System.out.println(res);
    }
}