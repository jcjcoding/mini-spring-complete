import java.util.*;

class Solution38 {
    List<List<String>> result = new ArrayList<>();
    // 预处理哈希表：key=字符，value=该字符对应位置匹配的单词（含索引，避免重复选）
    private Map<Character, List<WordInfo>> char0Map; // key=word[0]，value=word[0]等于该字符的单词
    private Map<Character, List<WordInfo>> char3Map; // key=word[3]，value=word[3]等于该字符的单词
    private List<WordInfo> allWordInfos; // 所有有效单词的信息（缓存char0/char3，避免重复计算）

    // 封装单词信息：缓存charAt(0)和charAt(3)，避免高频调用
    static class WordInfo {
        String word;
        char c0; // word.charAt(0)
        char c3; // word.charAt(3)
        int index; // 原数组索引，用于判重

        WordInfo(String word, int index) {
            this.word = word;
            this.c0 = word.charAt(0);
            this.c3 = word.charAt(3);
            this.index = index;
        }
    }

    public List<List<String>> wordSquares(String[] words) {
        // 1. 极致预处理：过滤+缓存+哈希分组（仅执行一次）
        preprocess(words);
        if (allWordInfos.isEmpty()) {
            return result;
        }

        // 2. 回溯：每层仅遍历精准匹配的单词，无无效遍历
        backtrack(new ArrayList<>(), new HashSet<>(), 0);

        // 3. 排序（保留，但优化比较逻辑）
        result.sort((q1, q2) -> {
            for (int i = 0; i < 4; i++) {
                int cmp = q1.get(i).compareTo(q2.get(i));
                if (cmp != 0) return cmp;
            }
            return 0;
        });
        return result;
    }

    // 预处理：过滤有效单词+缓存char0/char3+哈希分组
    private void preprocess(String[] words) {
        char0Map = new HashMap<>();
        char3Map = new HashMap<>();
        allWordInfos = new ArrayList<>();

        if (words == null) return;

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // 仅保留长度为4的单词（核心过滤）
            if (word == null || word.length() != 4) continue;

            WordInfo wi = new WordInfo(word, i);
            allWordInfos.add(wi);

            // 按word[0]分组
            char0Map.computeIfAbsent(wi.c0, k -> new ArrayList<>()).add(wi);
            // 按word[3]分组
            char3Map.computeIfAbsent(wi.c3, k -> new ArrayList<>()).add(wi);
        }
    }

    // 回溯核心：按层精准匹配，无无效遍历
    private void backtrack(List<String> path, Set<Integer> usedIndex, int level) {
        // 终止条件：四元组已凑齐，直接加入结果（无需再校验，因为每层都精准匹配规则）
        if (level == 4) {
            result.add(new ArrayList<>(path));
            return;
        }

        // 每层仅遍历“符合当前层规则+未被使用”的单词
        List<WordInfo> candidates = getCandidates(path, level);
        for (WordInfo wi : candidates) {
            if (usedIndex.contains(wi.index)) continue; // 跳过已使用的单词

            // 选择：无无效操作
            path.add(wi.word);
            usedIndex.add(wi.index);

            // 递归：仅精准匹配的分支会进入下一层
            backtrack(path, usedIndex, level + 1);

            // 回溯：极简操作
            path.remove(level);
            usedIndex.remove(wi.index);
        }
    }

    // 核心：按当前层（0-3）和已选路径，精准获取候选单词（无无效候选）
    private List<WordInfo> getCandidates(List<String> path, int level) {
        switch (level) {
            case 0: // 选第0个单词（top）：无前置规则，返回所有有效单词
                return allWordInfos;
            case 1: // 选第1个单词（left）：必须left[0] = top[0]
                char topC0 = path.get(0).charAt(0);
                return char0Map.getOrDefault(topC0, Collections.emptyList());
            case 2: // 选第2个单词（right）：必须right[0] = top[3]
                char topC3 = path.get(0).charAt(3);
                return char0Map.getOrDefault(topC3, Collections.emptyList());
            case 3: // 选第3个单词（bottom）：必须bottom[0]=left[3] 且 bottom[3]=right[3]
                char leftC3 = path.get(1).charAt(3);
                char rightC3 = path.get(2).charAt(3);
                // 先按bottom[0]=left[3]过滤，再筛选bottom[3]=right[3]（双重精准过滤）
                List<WordInfo> tmp = char0Map.getOrDefault(leftC3, Collections.emptyList());
                List<WordInfo> res = new ArrayList<>();
                for (WordInfo wi : tmp) {
                    if (wi.c3 == rightC3) {
                        res.add(wi);
                    }
                }
                return res;
            default:
                return Collections.emptyList();
        }
    }
}