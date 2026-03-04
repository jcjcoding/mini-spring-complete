public class StackOOM {
    // 记录递归次数，方便观察
    private static int count = 0;

    public static void main(String[] args) {
        try {
            recursiveMethod(1);
        } catch (StackOverflowError e) {
            System.out.println("递归次数：" + count);
            e.printStackTrace();
        }
    }

    // 递归方法：无终止条件，每次调用压入新栈帧
    private static void recursiveMethod(int i) {
        count++;
        // 递归调用自身，参数递增（无终止条件）
        recursiveMethod(i + 1);
    }
}