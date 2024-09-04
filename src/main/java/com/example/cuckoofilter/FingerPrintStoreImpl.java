package com.example.cuckoofilter;

import com.google.common.hash.HashFunction;

import java.util.HashMap;

public class FingerPrintStoreImpl implements FingerPrintStore<String> {

    private final HashFunction functions;

    private final short numBuckets;

    private final short bucketSize;

    private final short fingerPrintSize;

    private final HashMap<String, Boolean>[][] buckets;

    public FingerPrintStoreImpl(HashFunction functions, short numBuckets, short bucketSize, short fingerPrintSize) {
        this.numBuckets = numBuckets;
        this.bucketSize = bucketSize;
        this.fingerPrintSize = fingerPrintSize;
        buckets = new HashMap[numBuckets][bucketSize];
        for (short i = 0; i < numBuckets; i++) {
            for (short j = 0; j < bucketSize; j++) {
                buckets[i][j] = new HashMap<>();

            }
        }
        this.functions = functions;
    }


    public IndexKey getIndexKey(String item) {
        short index = (short) (Math.abs(item.hashCode()) % numBuckets);
        return IndexKey.builder().index(index)
                .index1((short) ((short) (index ^ (Math.abs(getFingerPrint(item).hashCode()) % numBuckets)) % numBuckets)).build();
    }

    public String getFingerPrint(String input) {
        return functions.hashBytes(input.getBytes()).toString().substring(0, fingerPrintSize);
    }

    @Override
    public boolean add(String value) {
        IndexKey key = getIndexKey(value);
        if (!buckets[key.getIndex()][key.getIndex1()].containsKey(getFingerPrint(value))) {
            buckets[key.getIndex()][key.getIndex1()].put(getFingerPrint(value), true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(String value) {
        IndexKey indexKey = getIndexKey(value);
        if (buckets[indexKey.getIndex()][indexKey.getIndex1()] == null) {
            return false;
        } else {
            buckets[indexKey.getIndex()][indexKey.getIndex1()].remove(getFingerPrint(value));
            return true;
        }
    }

    @Override
    public boolean exists(String value) {
        IndexKey indexKey = getIndexKey(value);
        if (buckets[indexKey.getIndex()][indexKey.getIndex1()] == null) {
            return false;
        } else {
            return buckets[indexKey.getIndex()][indexKey.getIndex1()].containsKey(getFingerPrint(value));

        }
    }
}
