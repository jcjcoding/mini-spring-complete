package com.student.utils;


public class HashMap<K,V> {
    static class Entry<K,V>{
        K key;
        V value;
        Entry<K,V> next;

        public Entry(K Key,V Value,Entry<K,V> next){
            this.key = Key;
            this.value = Value;
            this.next = next;
        }
    }

    private Entry<K,V>[] table;
    private int size;
    public int capacity;
    private  static final float LOAD_FACTOR = 0.75f;

    public HashMap(){
        this.capacity = 16;//初始化16个
        table = new Entry[capacity];
        this.size = 0;
    }

    private int hash(K key){
        if(key == null){
            return 0;
        }
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private int indexFor(int hash){
        return hash % (capacity-1);
    }

    private void resize() {
        int newCapacity = capacity * 2; // 容量翻倍
        Entry<K, V>[] newTable = new Entry[newCapacity]; // 新数组

        // 遍历旧数组的每个抽屉
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> entry = table[i]; // 拿到抽屉里的第一个节点
            while (entry != null) {
                // 先记下当前节点的下一个节点（防止移走后找不到）
                Entry<K, V> next = entry.next;

                // 重新计算当前节点在新数组中的索引
                int newIndex = hash(entry.key) & (newCapacity - 1);

                // 把当前节点挂到新数组的对应抽屉（头插法）
                entry.next = newTable[newIndex]; // 新节点钩子勾住新抽屉的第一个节点
                newTable[newIndex] = entry; // 新抽屉的第一个节点换成当前节点

                // 处理下一个节点
                entry = next;
            }
        }

        // 旧柜子换成新柜子，容量更新
        table = newTable;
        capacity = newCapacity;
    }

    public int size() {
        return size;
    }

    public V put(int hash,K key,V value){
        int index = indexFor(hash);
        Entry<K,V> entry = table[index];
        while(entry != null){
            if((entry.key == key) || (key != null && key.equals(entry.key))){
                V oldValue = entry.value;
                entry.value = value;
                return oldValue; // 结束方法
            }
            entry = entry.next;
        }
        // 创建新节点：key、value，钩子勾住原来的第一个节点（table[index]）
        Entry<K, V> newEntry = new Entry<>(key, value, table[index]);
        // 把新节点放到抽屉的第一个位置（原来的节点被新节点的钩子勾住了）
        table[index] = newEntry;
        // 数据数量+1
        size++;
        // 如果当前数量 > 容量×负载因子，触发扩容
        if (size > capacity * LOAD_FACTOR) {
            resize(); // 扩容方法，后面讲
        }
        return null;
    }

    public V get(K key) {
        int hash = hash(key); // 算哈希值
        int index = indexFor(hash); // 算索引

        // 遍历抽屉里的链表
        Entry<K, V> entry = table[index];
        while (entry != null) {
            // 找到相同的key，返回value
            if ((entry.key == key) || (key != null && key.equals(entry.key))) {
                return entry.value;
            }
            entry = entry.next;
        }

        // 遍历完都没找到，返回null
        return null;
    }

    // 添加containsKey方法：判断是否包含指定key
    public boolean containsKey(K key) {
        int hash = hash(key); // 计算key的哈希值
        int index = indexFor(hash); // 计算对应的索引位置

        // 遍历该索引位置的链表
        Entry<K, V> entry = table[index];
        while (entry != null) {
            // 匹配逻辑和get/put保持一致：处理null key，且用equals比较
            if ((entry.key == key) || (key != null && key.equals(entry.key))) {
                return true; // 找到匹配的key，返回true
            }
            entry = entry.next; // 继续遍历下一个节点
        }

        // 遍历完链表都没找到，返回false
        return false;
    }


    
}
