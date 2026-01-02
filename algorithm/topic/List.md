List
ArrayList	动态数组	查询快（索引访问）、增删慢（需移动元素）、非线程安全、效率高	读多写少的场景（最常用）

LinkedList	双向链表	查询慢（需遍历）、增删快（仅修改节点引用）、非线程安全	写多读少（频繁增删）的场景

Vector	动态数组	线程安全（方法加 synchronized）、效率低、已被 ArrayList 替代	多线程环境（建议用 CopyOnWriteArrayList 替代）

List<String> list = new ArrayList<>();
    // 1. 增：添加元素
        list.add("Apple");          // 尾部添加
        list.add(0, "Banana");      // 指定索引添加（索引越界会抛 IndexOutOfBoundsException）
        list.addAll(List.of("Cherry", "Durian")); // 批量添加

    // 2. 查：访问元素
        String first = list.get(0); // 获取指定索引元素
        int size = list.size();     // 获取列表长度
        boolean contains = list.contains("Cherry"); // 判断是否包含元素
        int index = list.indexOf("Durian"); // 获取元素首次出现的索引（不存在返回 -1）
        int lastIndex = list.lastIndexOf("Apple"); // 获取元素最后出现的索引

    // 3. 改：修改元素
        list.set(1, "Blueberry"); // 替换指定索引的元素

    // 4. 删：删除元素
        list.remove(0); // 删除指定索引的元素
        list.remove("Cherry"); // 删除首次出现的指定元素
        list.removeAll(List.of("Durian")); // 批量删除
        list.clear(); // 清空列表

List<String> fruits = List.of("Apple", "Banana", "Cherry");

    // 方式 1：普通 for 循环（支持索引操作，适合需要下标场景）
        for (int i = 0; i < fruits.size(); i++) {
            System.out.println(fruits.get(i));
        }

    // 方式 2：增强 for 循环（foreach，简洁，无需索引）
        for (String fruit : fruits) {
            System.out.println(fruit);
        }

    // 方式 3：迭代器（支持遍历中删除元素，避免 ConcurrentModificationException）
        Iterator<String> iterator = fruits.iterator();
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            if (fruit.equals("Banana")) {
                iterator.remove(); // 仅迭代器的 remove 方法安全
            }
            System.out.println(fruit);
        }

    // 方式 4：JDK 8+ Stream 流式遍历（支持函数式编程，简洁高效）
        fruits.stream().forEach(fruit -> System.out.println(fruit));
        // 更简洁的方法引用

        fruits.forEach(System.out::println);
        List<Integer> nums = new ArrayList<>(List.of(3, 1, 2));
        Collections.sort(nums); // 升序排序（自然排序）
        System.out.println(nums); // [1, 2, 3]

    // JDK 8+ 支持 List.sort() 方法
        nums.sort(null); // null 表示使用自然排序

        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", 20));
        students.add(new Student("Bob", 18));
        students.add(new Student("Charlie", 19));

    // 按年龄升序排序（Comparator 匿名内部类）
        students.sort((s1, s2) -> s1.getAge() - s2.getAge());

    // JDK 8+ 方法引用简化
        students.sort(Comparator.comparingInt(Student::getAge));

    // 按年龄降序
        students.sort(Comparator.comparingInt(Student::getAge).reversed());

        System.out.println(students);