public class Solution37 {
    public String largestEven(String s) {
        if(s.indexOf("2") == -1) return "";
        int end = s.length() - 1;
        while (end >= 0 && s.charAt(end) == '1') {
            end--;
        }
        return s.substring(0, end + 1);
    }
}
