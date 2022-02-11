package bizfeng.leetcode.cache;

import bizfeng.leetcode.cache.Cache;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;


/**
 * 基于LRU缓存策略的实现
 * 固定缓存容量，达到最大容量后，剔除最久没被使用的元素
 */

public class LruCache<K, V> implements Cache<K, V> {

    private int cap = 0;
    //cache定义
    private HashMap<K, Node<K, V>> map;
    //双向链表
    private LinkNode<K, V> link;

    public LruCache(int cap) {
        this.cap = cap;
        this.map = new HashMap<>(cap);
        this.link = new LinkNode<>();
    }

    //api
    @Override
    public boolean put(K k, V v) {
        Node<K, V> node = new Node<>(k, v);
        this.putNode(node);
        return true;
    }

    @Override
    public V get(K k) {
        Node<K, V> node = this.getNode(k);
        return node == null ? null : node.getValue();
    }

    @Override
    public boolean del(K k) {
        this.delNode(k);
        return true;
    }

    @Override
    public int size() {
        return this.link.getSize();
    }

    //获取
    private Node<K, V> getNode(K k) {
        if (!map.containsKey(k)) {
            return null;
        }
        //获取到node
        Node<K, V> node = map.get(k);
        //将node放到链表最前面 提升为最近使用的
        link.join(node);
        return node;
    }

    //存放
    private void putNode(Node<K, V> node) {
        K key = node.getKey();
        //如果key从未存储
        if (!map.containsKey(key)) {
            //判断link是否已经满了
            while (link.getSize() >= cap) {
                //删除队尾元素
                Node<K, V> lastNode = link.getLastNode();
                //从map里面删除
                map.remove(lastNode.getKey());
                //从link里面删除
                link.delTailNode();
            }
            //将元素放到头部 队列长度加一
            link.joinNew(node);
        } else {
            //将元素放到头部 队列长度不变
            link.join(node);
        }
        //存入map中
        map.put(key, node);
    }

    //删除node
    private void delNode(K key) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            link.del(node, true);
            map.remove(key);
        }
    }

    @Getter
    @Setter
    //元素定义 (双向链表结构)
    static class Node<K, V> {
        private K key;
        private V value;
        //前驱节点
        Node<K, V> preNode;
        //后驱节点
        Node<K, V> nextNode;

        public Node() {
        }

        public Node(K k, V v) {
            this.key = k;
            this.value = v;
        }

        private void delNode() {
            if (this.preNode != null) {
                //将前驱节点的后驱节点指向后一个节点
                this.preNode.nextNode = this.nextNode;
            }
            if (this.nextNode != null) {
                //后驱节点的前驱节点指向前一个节点
                //临时节点
                this.nextNode.preNode = this.preNode;
            }
        }

        //清空节点的所有引用数据 help GC
        public void clear() {
            this.key = null;
            this.value = null;
            this.preNode = null;
            this.nextNode = null;
        }
    }


    //链表定义
    static class LinkNode<K, V> {
        int size = 0;
        //头部节点
        private Node<K, V> head = new Node<>();
        //尾部节点
        private Node<K, V> tail = new Node<>();

        public LinkNode() {
            init();
        }

        private void init() {
            //首尾相连
            head.nextNode = tail;
            tail.preNode = head;
        }


        //将新的节点加入队首
        public void joinNew(Node<K, V> node) {
            //插队
            node.nextNode = head.nextNode;
            node.preNode = head;
            head.nextNode.preNode = node;
            head.nextNode = node;
            ++size;
        }

        //将旧的节点插入到队首
        public void join(Node<K, V> node) {
            //删除节点原本的关联
            node.delNode();
            //插队
            node.nextNode = head.nextNode;
            node.preNode = head;
            head.nextNode.preNode = node;
            head.nextNode = node;
        }

        //追加到尾部
        public void append(Node<K, V> node) {
            tail.preNode.nextNode = node;
            node.preNode = tail.preNode;
            tail.preNode = node;
            node.nextNode = tail;
            ++size;
        }

        public void del(Node<K, V> node) {
            node.delNode();
            --size;
        }

        public void del(Node<K, V> node, boolean clear) {
            node.delNode();
            --size;
            if (clear) {
                node.clear();
            }
        }

        //删除队尾节点
        public void delTailNode() {
            //临时存储一下最后一个节点
            Node<K, V> tmp = tail.preNode;
            //关系转移
            del(tmp, true);
        }

        //获取最后一个节点
        public Node<K, V> getLastNode() {
            return tail.preNode;
        }

        //获取第一个节点
        public Node<K, V> getFirstNode() {
            return head.nextNode;
        }

        public int getSize() {
            return size;
        }
    }


    public String print() {
        StringBuilder builder = new StringBuilder();
        Node<K, V> node = this.link.head;
        while (node != null && node.getNextNode() != null) {
            builder.append("[k=").append(node.getKey())
                    .append(",v=").append(node.getValue())
                    .append("]");
            node = node.nextNode;
        }
        return builder.toString();
    }
}
