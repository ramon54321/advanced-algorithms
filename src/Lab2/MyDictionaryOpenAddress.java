package Lab2;

public class MyDictionaryOpenAddress<K,V> {

    private int _bucketCount;

    private BucketOpen<K,V>[] _buckets;

    public MyDictionaryOpenAddress(int _bucketCount, int _bucketSize) {
        this._bucketCount = nextPrime(_bucketCount);

        // -- Init buckets
        _buckets = new BucketOpen[this._bucketCount];
        for (int i = 0; i < this._bucketCount; i++) {
            _buckets[i] = new BucketOpen<K,V>(_bucketSize);
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

    private int getNextIndex(int index) {
        if(index < _bucketCount-1)
            return index+1;
        return 0;
    }

    private int hashKey(K key) {
        return Math.abs(key.hashCode() % _bucketCount);
    }

    public V get(K key) {
        int tries = 0;
        int index = hashKey(key);
        while (true) {
            if(tries > _bucketCount)
                return null;

            V getResult = _buckets[index].tryGet(key);
            if(getResult != null)
                return getResult;
            index = getNextIndex(index); // -- Linear probing
            tries++;
        }
    }

    public int put(K key, V item) {
        int tries = 0;
        int index = hashKey(key);
        while (true) {
            if(tries > _bucketCount)
                return -1;

            boolean didAdd = _buckets[index].tryAdd(new BucketEntry(key, item));
            if(didAdd)
                return index;
            index = getNextIndex(index); // -- Linear probing
            tries++;
        }
    }

    public int del(K key) {
        int tries = 0;
        int index = hashKey(key);
        while (true) {
            if(tries > _bucketCount)
                return -1;

            boolean didDel = _buckets[index].tryDel(key);
            if(didDel)
                return index;
            index = getNextIndex(index); // -- Linear probing
            tries++;
        }
    }

    public void print() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < _bucketCount; i++) {
            stringBuilder.append(" [" + i + "]\t");
            for (int j = 0; j < _buckets[i].get_count(); j++) {
                stringBuilder.append(" - ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);
    }
}
