import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Solution44 {
    private static final int INF = Integer.MAX_VALUE;

    public int minCost(int n, int[][] edges) {
        // 1. 构建邻接表（对应Python的g=[[] for _ in range(n)]）
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        // 填充邻接表：原边(u→v, w)，反向边(v→u, 2*w)
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            graph.get(u).add(new int[]{v, w});       // 原边
            graph.get(v).add(new int[]{u, 2 * w});   // 反向边（成本翻倍）
        }

        // 2. 初始化距离数组（对应Python的dis=[inf]*n）
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0; // 起点0的距离为0

        // 3. 初始化优先队列（最小堆，对应Python的heapq）
        // 队列元素：int[]{当前成本, 节点}，按成本升序排列
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, 0});

        // 4. 核心Dijkstra逻辑（对应Python的while h循环）
        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int distU = curr[0]; // 对应Python的dis_u
            int u = curr[1];     // 对应Python的u

            // 剪枝：当前弹出的成本不是最优的，跳过
            if (distU > dist[u]) {
                continue;
            }

            // 到达终点，直接返回（优先队列保证是最小成本）
            if (u == n - 1) {
                return distU;
            }

            // 遍历邻接边，松弛操作
            for (int[] edge : graph.get(u)) {
                int v = edge[0];   // 邻接节点
                int w = edge[1];   // 边的权重
                int newDistV = distU + w; // 新的候选成本

                // 若新成本更小，更新并加入优先队列
                if (newDistV < dist[v]) {
                    dist[v] = newDistV;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        // 无法到达终点，返回-1
        return -1;
    }
    
}
