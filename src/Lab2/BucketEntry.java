package Lab2;

public class BucketEntry<K,V> {
    public K key;
    public V value;

    public BucketEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
