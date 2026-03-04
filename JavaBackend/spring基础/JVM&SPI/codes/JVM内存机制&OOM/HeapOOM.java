import java.util.ArrayList;
import java.util.List;

public class HeapOOM {
    // 静态集合：持有对象引用，防止GC回收
    private static List<Object> objectList = new ArrayList<>();

    public static void main(String[] args) {
        // 循环创建对象，直到堆溢出
        while (true) {
            // 每次创建一个1KB的对象（ byte[1024] 约1KB）
            objectList.add(new byte[1024]);
            // 可选：每加1000个对象打印一次，观察内存变化
            if (objectList.size() % 1000 == 0) {
                System.out.println("已创建对象数量：" + objectList.size());
            }
        }
    }
}