

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution24 {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeDivide(lists, 0, lists.length - 1);
    }
    private ListNode mergeDivide(ListNode[] lists, int left, int right) {
        if (left == right) return lists[left]; // 只剩一个链表，直接返回
        int mid = left + (right - left) / 2;
        ListNode l1 = mergeDivide(lists, left, mid); // 合并左半
        ListNode l2 = mergeDivide(lists, mid + 1, right); // 合并右半
        return mergeTwoLists(l1, l2); // 合并左右结果
    }
    public ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if(l1 == null){
            return l2;
        }
        if(l2 == null){
            return l1;
        }
        ListNode res = new ListNode();
        ListNode head = res;
        while(l1 != null && l2 != null){
            if(l1.val < l2.val){
                res.next = l1;
                l1 = l1.next;
            }else{
                res.next = l2;
                l2 = l2.next;
            }
            res = res.next;
        }
        if(l1 != null){
            res.next = l1;
        }
        if(l2 != null){
            res.next = l2;
        }
        return head.next;
    }
}

