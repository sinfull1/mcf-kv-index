package com.example.cuckoofilter;


import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.UUID;
import java.util.zip.CRC32;
@Component
public class CuckooFilter {
    private static final int BUCKET_SIZE = 2; // Each bucket can hold 4 fingerprints
    private static final int FINGERPRINT_SIZE = 1; // Size of each fingerprint (in bytes)
    private static final int NUM_BUCKETS = 2; // Number of buckets in the filter
    HashFunction functions = Hashing.murmur3_32_fixed();
    private Trie[][] buckets = new Trie[NUM_BUCKETS][BUCKET_SIZE];
    private Random rand = new Random();
   // ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(1);

    // Constructor to initialize the Cuckoo Filter
    public CuckooFilter() {
        // Initialize buckets
        for(int i=0; i<NUM_BUCKETS; i++) {
            for (int j=0; j<BUCKET_SIZE; j++) {
                buckets[i][j] = new Trie();
            }
        }

        rand = new Random();
        //executorService.schedule(this::printBucket,1000, TimeUnit.SECONDS);
    }
    public void printBucket() {
        for(int i=0; i<NUM_BUCKETS; i++) {
            for (int j=0; j<BUCKET_SIZE; j++) {
                buckets[i][j].printAllWords();
                System.out.print("/");
            }
            System.out.println(",");
        }


    }

    // Insert an item into the Cuckoo filter
    public boolean insert(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);
        System.out.println(fingerprint);
        System.out.print(i1+"|");
        System.out.print(i2);
        // Try inserting into either of the two buckets
        if (insertIntoBucket(i1, fingerprint) || insertIntoBucket(i2, fingerprint)) {
            return true;
        }
         return false; // Filter is full, and the item could not be inserted
    }

    // Lookup an item in the Cuckoo filter
    public boolean lookup(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);

        // Check both candidate buckets
        return findInBucket(i1, fingerprint) || findInBucket(i2, fingerprint);
    }

    // Delete an item from the Cuckoo filter
    public boolean delete(String item) {
        String fingerprint = getFingerprint(item);
        int i1 = getIndex(item);
        int i2 = getAltIndex(i1, fingerprint);

        // Try to delete from either bucket
        if (deleteFromBucket(i1, fingerprint) || deleteFromBucket(i2, fingerprint)) {
            return true;
        }

        return false; // Item was not found in either bucket
    }

    // Generate a fingerprint using a CRC32 hash
    private String getFingerprint(String item) {
        return functions.hashBytes(item.getBytes(Charset.defaultCharset())).toString();

    }

    // Calculate the first bucket index
    private int getIndex(String item) {
        CRC32 crc32 = new CRC32();
        crc32.update(item.getBytes(StandardCharsets.UTF_8));
        System.out.println("crc"+ crc32.getValue() );
        return (int) (crc32.getValue() % NUM_BUCKETS);
    }

    // Calculate the alternate index for a given fingerprint
    private int getAltIndex(int index, String fingerprint) {
        CRC32 crc32 = new CRC32();
        crc32.update(fingerprint.getBytes());
        return (index ^ (int) (crc32.getValue() % NUM_BUCKETS)) % NUM_BUCKETS;
    }

    // Insert a fingerprint into a specific bucket
    private boolean insertIntoBucket(int index, String fingerprint) {
        for (int i = 0; i < BUCKET_SIZE; i++) {
            if (/*buckets[index][i].size()*/ 1 == 1) {
                buckets[index][i].insert(fingerprint);
                return true;
            }
        }
        return false;
    }

    // Search for a fingerprint in a specific bucket
    private boolean findInBucket(int index, String fingerprint) {
        for (int i = 0; i < BUCKET_SIZE; i++) {
            if (buckets[index][i].search(fingerprint)) {
                return true;
            }
        }
        return false;
    }

    // Delete a fingerprint from a specific bucket
    private boolean deleteFromBucket(int index, String fingerprint) {
        for (int i = 0; i < BUCKET_SIZE; i++) {
            //fingerprint is not unique enough
            if (buckets[index][i].search(fingerprint)) {
                buckets[index][i].delete(fingerprint); // Clear the fingerprint
                return true;
            }
        }
        return false;
    }

    // Main function to demonstrate the Cuckoo Filter

}

