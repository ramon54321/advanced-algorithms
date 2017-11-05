package Lab2;

import java.util.ArrayList;

public class BucketOpen<K,V> {

    private BucketEntry<K,V>[] _contents;

    private int _bucketSize;
    private int _count = 0;

    public int get_count() {
        return _count;
    }

    public BucketOpen(int _bucketSize) {
        this._bucketSize = _bucketSize;
        _contents = new BucketEntry[this._bucketSize];
    }

    boolean tryAdd(BucketEntry<K,V> bucketEntry) {
        if(_count < _bucketSize) {
            // Add
            _contents[_count] = bucketEntry;
            _count++;
            return true;
        } else {
            // No Space to add
            return false;
        }
    }

    V tryGet(K key) {
        for (int i = 0; i < _count; i++) {
            if(_contents[i].key == key)
                return _contents[i].value;
        }
        return null;
    }

    boolean tryDel(K key) {
        for (int i = 0; i < _count; i++) {
            if(_contents[i].key == key){
                _count--;
                for (int j = i; j < _count; j++) {
                    _contents[j] = _contents[j+1];
                }
                return true;
            }
        }
        return false;
    }

    boolean isFull() {
        return _count == _bucketSize;
    }
}
