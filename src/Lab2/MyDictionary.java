package Lab2;

import java.util.ArrayList;

public class MyDictionary<K,V> {

    private int _bucketCount;

    private Bucket[] _buckets;

    public MyDictionary(int _bucketCount) {
        this._bucketCount = nextPrime(_bucketCount);

        // -- Init buckets
        _buckets = new Bucket[this._bucketCount];
        for (int i = 0; i < this._bucketCount; i++) {
            _buckets[i] = new Bucket<K,V>();
        }
    }

    private static int nextPrime(int n) {
        int prime = 0;
        int i;
        int nextPrime;

        for(i=2; i<n/2; i++) {
            if(n%i == 0) {
                prime = 1;
                break;
            }
        }

        if(prime == 1) {
            nextPrime = n;
            prime = 1;
            while(prime != 0) {
                nextPrime++;
                prime = 0;
                for(i=2; i<nextPrime/2; i++) {
                    if(nextPrime%i == 0) {
                        prime = 1;
                        break;
                    }
                }
            }
            return (nextPrime);
        } else {
            return (n);
        }
    }

    private int hashKey(K key) {
        return Math.abs(key.hashCode() % _bucketCount);
    }

    private BucketEntry getBucketEntry(K key) {
        int index = hashKey(key);
        ArrayList<BucketEntry> entries = _buckets[index].contents;
        for (BucketEntry bucketEntry : entries) {
            if (bucketEntry.key == key)
                return bucketEntry;
        }
        return null;
    }

    public Object get(K key) {
        BucketEntry bucketEntry = getBucketEntry(key);
        if(bucketEntry != null)
            return bucketEntry.value;
        return null;
    }

    public int put(K key, V item) {
        int index = hashKey(key);
        _buckets[index].contents.add(new BucketEntry(key, item));
        return index;
    }

    public int del(K key) {
        BucketEntry bucketEntry = getBucketEntry(key);
        if(bucketEntry != null) {
            _buckets[hashKey(key)].contents.remove(bucketEntry);
            // -- TODO: Return length of new array
        }
        return 0;
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < _bucketCount; i++) {
            stringBuilder.append(" [" + i + "]\t");
            for (int j = 0; j < _buckets[i].contents.size(); j++) {
                stringBuilder.append(" - ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }
}
