import java.util.HashMap;
import java.util.Map;
/*复杂度更低的解法但是我们要学哈希表就不用这个了
 * public boolean isAnagram(String s, String t) {
        // 优化1：长度不同直接返回false
        if (s.length() != t.length()) {
            return false;
        }
        
        // 优化2：用数组统计26个小写字母的频率（a-z对应索引0-25）
        int[] count = new int[26];
        
        // 遍历第一个字符串，累加字符频率
        for (char c : s.toCharArray()) {
            count[c - 'a']++; // 'a'的ASCII是97，c-'a'直接得到0-25的索引
        }
        
        // 遍历第二个字符串，递减字符频率
        for (char c : t.toCharArray()) {
            count[c - 'a']--;
            // 提前剪枝：若某字符频率为负，说明t中该字符更多，直接返回false
            if (count[c - 'a'] < 0) {
                return false;
            }
        }
        
        // 检查所有字符频率是否为0（若有非0，说明字符数量不匹配）
        for (int num : count) {
            if (num != 0) {
                return false;
            }
        }
        
        return true;
    }
 */
public class Solution11 {
    public boolean isAnagram(String s, String t) {
        Map<Character,Integer> hashMapA = new HashMap<>();
        Map<Character,Integer> hashMapB = new HashMap<>();
        for(Character ch:s.toCharArray()){
            int count = 1;
            count += (int)hashMapA.getOrDefault(ch, 0);
            hashMapA.put(ch, count);
        }
        for(Character ch:t.toCharArray()){
            int count = 1;
            count += (int)hashMapB.getOrDefault(ch, 0);
            hashMapB.put(ch, count);
        }
        /* 
        for (Map.Entry<Character, Integer> entry : hashMapA.entrySet()) {
            // 直接通过entry获取键和值，无需重复调用get(key)，效率更高
            Character key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("A键：" + key + "，值：" + value);
        }
        for (Map.Entry<Character, Integer> entry : hashMapB.entrySet()) {
            // 直接通过entry获取键和值，无需重复调用get(key)，效率更高
            Character key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println("B键：" + key + "，值：" + value);
        }
            */
        return hashMapA.equals(hashMapB);    
    }
    public static void main(String[] args) {
        String str1Test = "aaan";
        String str2Test = "mena";
        String str3Test = "nnnaa";
        Solution11 solution11 = new Solution11();
        if(solution11.isAnagram(str1Test, str3Test)){
            System.out.println("cg");
        }
    }
}
