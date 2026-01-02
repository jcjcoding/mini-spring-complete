# Hash
## HashMap	
- 键值对存储、允许键 / 值为 null、非线程安全	
- 记录元素 - 状态映射（如计数、是否出现）

## HashSet	
- 基于 HashMap 实现（值为固定空对象）、无重复元素	
- 仅记录 “元素是否存在”（去重、判存）

## ConcurrentHashMap	
- 线程安全、分段锁 / CAS 优化、效率高于 Hashtable	
- 多线程环境下的哈希表操作

## Hashtable	
- 线程安全（方法加锁）、不允许 null、已过时	
- 仅兼容老代码，不推荐使用

```Java
    // 推荐：提前指定容量（预估元素数量）+ 负载因子1.0（避免扩容）
    // 容量公式：预估元素数 / 负载因子（如预估100个元素：100/1.0=100）
    Map<Integer, Integer> countMap = new HashMap<>(100, 1.0f);

    // 空初始化（默认容量16，负载因子0.75，扩容阈值=16*0.75=12）
    Map<String, Boolean> existMap = new HashMap<>();

    Map<String, Integer> map = new HashMap<>(10, 1.0f);

    // 1. 增/改：put（键存在则覆盖值，返回旧值；不存在则返回null）
    map.put("Apple", 1); // 新增
    map.put("Banana", 2);
    map.put("Apple", 3); // 覆盖，返回1

    // 2. 查：get / getOrDefault（避免空指针）
    int appleCount = map.get("Apple"); // 3
    // 推荐：键不存在时返回默认值（无需判空）
    int orangeCount = map.getOrDefault("Orange", 0); // 0

    // 3. 判存：containsKey（判断键是否存在）
    boolean hasBanana = map.containsKey("Banana"); // true
    boolean hasOrange = map.containsKey("Orange"); // false

    // 4. 删：remove（删除键，返回对应值；键不存在返回null）
    Integer removed = map.remove("Banana"); // 2

    // 5. 清空/长度
    map.clear(); // 清空所有元素
    int size = map.size(); // 0

    Map<String, Integer> map = new HashMap<>();
    map.put("A", 1);
    map.put("B", 2);
    map.put("C", 3);

    // 方式1：遍历键集（最常用，按需取值）
    for (String key : map.keySet()) {
        int value = map.get(key);
        System.out.println(key + "=" + value);
    }

    // 方式2：遍历键值对（效率更高，无需二次get）
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
        String key = entry.getKey();
        int value = entry.getValue();
        System.out.println(key + "=" + value);
    }

    // 方式3：JDK8+ 流式遍历（简洁，支持过滤/排序）
    map.forEach((key, value) -> {
        if (value > 1) {
            System.out.println(key + "=" + value);
        }
    });

    // 方式4：迭代器（支持遍历中删除，避免并发修改异常）
    Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
    while (iterator.hasNext()) {
        Map.Entry<String, Integer> entry = iterator.next();
        if (entry.getValue() == 2) {
            iterator.remove(); // 安全删除
        }
    }
    // 初始化（同样推荐指定容量）
    Set<Integer> existSet = new HashSet<>(10, 1.0f);

    // 增：add（元素不存在返回true，存在返回false）
    existSet.add(1); // true
    existSet.add(2);
    existSet.add(1); // false（重复，不添加）

    // 查：contains（判断元素是否存在）
    boolean has1 = existSet.contains(1); // true

    // 删：remove（元素存在返回true，不存在返回false）
    existSet.remove(2); // true

    // 遍历（同HashMap，仅遍历元素）
    for (int num : existSet) {
        System.out.println(num);
    }

    // 清空/长度
    existSet.clear();
    int size = existSet.size(); // 0