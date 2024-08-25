package com.example.cuckoofilter;


import com.google.common.hash.HashFunction;

import java.nio.charset.Charset;
import java.util.TreeMap;
import java.util.UUID;

public class DeleteInvariantFprCuckooFilter {
    private final int bucketSize;
    private final int fingerPrintSize;
    private final int numBuckets;
    private final HashFunction functions;
    private final TreeMap<String, Boolean>[][] buckets;
    private final CollisionStoreImpl collisionStore = new CollisionStoreImpl();

    public DeleteInvariantFprCuckooFilter(int bucketSize, int fingerPrintSize, int numBuckets, HashFunction functions) {
        this.bucketSize = bucketSize;
        this.fingerPrintSize = fingerPrintSize;
        this.numBuckets = numBuckets;
        this.functions = functions;
        buckets = new TreeMap[this.numBuckets][this.bucketSize];
        for (int i = 0; i < this.numBuckets; i++) {
            for (int j = 0; j < this.bucketSize; j++) {
                buckets[i][j] = new TreeMap<String, Boolean>();
            }
        }
    }

    public void printBucket() {
        for (int i = 0; i < numBuckets; i++) {
            for (int j = 0; j < bucketSize; j++) {
                System.out.print(buckets[i][j].toString());
                System.out.print("/");
            }
            System.out.println(",");
        }
        int counter = 0;
        System.out.println("Coll:" + counter);
        System.out.println(collisionStore.toString());
    }

    public void populate() {
        for (int i = 1; i < 1000000; i++) {
            this.insert(UUID.randomUUID().toString());
        }
    }

    // Insert an item into the Cuckoo filter
    public boolean insert(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);
        return insertIntoBucket(i1, i2, fingerprint, item);
    }

    // Lookup an item in the Cuckoo filter
    public boolean lookup(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);
        return findInBucket(i1, i2, fingerprint);
    }

    public boolean delete(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);
        return deleteFromBucket(i1, i2, fingerprint, item);
    }

    private String getFingerprint(String item) {
        return functions.hashBytes(item.getBytes(Charset.defaultCharset())).toString().substring(0, fingerPrintSize);
    }

    private int getIndex(String item) {
        return Math.abs(item.hashCode()) % numBuckets;
    }

    private int getAltIndex(int index, String fingerprint) {
        return (index ^ (Math.abs(fingerprint.hashCode()) % numBuckets)) % numBuckets;
    }

    private boolean insertIntoBucket(int index, int index1, String fingerprint, String item) {
        IndexKey indexKey = IndexKey.builder().key(fingerprint).index((short) index).index((short) index1).build();
        if (buckets[index][index1].get(fingerprint) != null) {
            collisionStore.addItemInCollection(indexKey, functions.hashBytes(item.getBytes()).toString());
        }
        buckets[index][index1].put(fingerprint, true);
        return true;
    }

    private boolean findInBucket(int index, int index1, String fingerprint) {
        if (buckets[index][index1].get(fingerprint) == null) {
            return false;
        } else {
            return buckets[index][index1].get(fingerprint);
        }
    }

    private boolean deleteFromBucket(int index, int index1, String fingerprint, String item) {
        if (buckets[index][index1].get(fingerprint) != null) {
            IndexKey indexKey = IndexKey.builder().key(fingerprint).index((short) index).index((short) index1).build();
            collisionStore.removeItemFromCollection(indexKey, functions.hashBytes(item.getBytes()).toString());
            if (collisionStore.countInCollection(indexKey) == 0) {
                buckets[index][index1].remove(fingerprint);
            }
            return true;
        }
        return false;
    }

}


