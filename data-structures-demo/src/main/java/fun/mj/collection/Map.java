package fun.mj.collection;

public interface Map<K, V> {
    int size();
    boolean isEmpty();
    void clear();
    V put(K key, V value); //添加元素
    V get(K key);
    V remove(K key);
    boolean containsKey(K key); //查找key是否存在
    boolean containsValue(V value); //查找value是否存在
    void traversal(Visitor<K, V> visitor); //元素遍历
	
    public static abstract class Visitor<K, V> {
        public boolean stop;

        /**
         *
         * @param key
         * @param value
         * @return true 停止遍历
         */
        public abstract boolean visit(K key, V value);
    }
}
