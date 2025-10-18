class Solution3 {
    public String longestCommonPrefix(String[] strs) {
        if (strs[0] == "" || strs.length == 0) {
            return "";
        }
        String firstStr = strs[0];
        // 检查第一个字符串是否为空
        if (firstStr.isEmpty()) {
            return "";
        }
        int index = 0;
        String result = "";
        while(index < firstStr.length()){
            char currentChar = firstStr.charAt(index);
            boolean isTrue = true;
            for (String str:strs){
                if (index >= str.length() || str.charAt(index) != currentChar) {
                    isTrue = false;
                    break;
                }
            }
            if(isTrue){
                result += currentChar;
                index ++;
            }else{
                break;
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String[] strsTest = {"flow","flosdad","flwadaw"};
        Solution3 solution3 = new Solution3();
        String res = solution3.longestCommonPrefix(strsTest);
        System.out.println(res);
    }
}