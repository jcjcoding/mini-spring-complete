import java.util.HashMap;
import java.util.Map;

class LRUCache {
    class Node{
        int key;
        int value;
        long timestamp;
        
        Node prev;
        Node next;

        public Node(){
            //哨兵节点使用
        }

        public Node(int key,int value){
            this.key = key;
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }

    
    private final int capacity;
    private final Map<Integer,Node> cache;
    private final Node head;
    private final Node tail;

    public LRUCache(int capacity) {
        if(capacity <= 0){
            throw new IllegalArgumentException("最大容量必须为正数");
        }else{
            this.capacity = capacity;
        }
        this.cache = new HashMap<>(capacity);
        this.head = new Node();
        this.tail = new Node();
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        if(cache.containsKey(key)){
            Node nowNode = cache.get(key);
            moveToHead(nowNode);
            int value = nowNode.value;
            return value;
        }else{
            return -1;
        }
    }
    
    public void put(int key, int value) {
        int pairCount = cache.size();

        if (cache.containsKey(key)) {
            Node newNode = cache.get(key);
            newNode.value = value;    
            moveToHead(newNode);

        } else if (pairCount < capacity){
            Node newNode = new Node(key,value);
            moveToHead(newNode);
            cache.put(key,newNode);
        } else {
            int keyToDelete = removeTail();
            cache.remove(keyToDelete);

            Node newNode = new Node(key,value);
            moveToHead(newNode);
            cache.put(key,newNode);
        }
    }

    public void moveToHead(Node node){
        if (node.prev != null && node.next != null) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
        node.timestamp = System.currentTimeMillis();
    }

    public int removeTail(){
        Node lastNode = tail.prev;
        int key = lastNode.key;
        if(tail.prev == head){
            return key;
        }
        lastNode.prev.next = tail;
        tail.prev = lastNode.prev;
        return key;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */