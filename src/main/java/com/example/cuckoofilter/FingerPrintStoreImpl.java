package com.example.cuckoofilter;

import com.google.common.hash.HashFunction;

import java.util.HashMap;

public class FingerPrintStoreImpl implements  FingerPrintStore<String> {

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
        for( short i=0; i<numBuckets;i++) {
            for(short j=0; j<bucketSize; j++) {
                buckets[i][j] = new HashMap<>();

            }
        }
        this.functions = functions;
    }


    public String getFingerPrint(String input) {
        return functions.hashBytes(input.getBytes()).toString().substring(0, fingerPrintSize);
    }

    @Override
    public boolean add(String value) {
        IndexKey key = getIndexKey(value);

        return false;
    }

    @Override
    public boolean remove(String value) {
        return false;
    }

    @Override
    public boolean exists(String value) {
        return false;
    }
}
