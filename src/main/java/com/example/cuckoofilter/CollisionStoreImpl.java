package com.example.cuckoofilter;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollisionStoreImpl implements CollisionStore<IndexKey, TreeSet<Integer>, Integer, String> {

    private final TreeMap<IndexKey, TreeSet<Integer>> collisions = new TreeMap<>();

    private final HashFunction doubleHashingFunction = Hashing.adler32();
    public void addCollection(IndexKey key, TreeSet<Integer> collection) {
        collisions.putIfAbsent(key, collection);
    }

    public int getDoubleHash(String item)  {
        return doubleHashingFunction.hashBytes(item.getBytes()).asInt();
    }



    public boolean contains(IndexKey key, String input) {
        int value = getDoubleHash(input);
        if (collisions.get(key) != null) {
            return false;
        } else {
            return collisions.get(key).contains(value);
        }
    }


    public void addItemInCollection(IndexKey key, String input) {
        int value = getDoubleHash(input);
        if (collisions.get(key) != null) {
            Set<Integer> set = collisions.get(key);
            set.add(value);
        } else {
            TreeSet<Integer> treeSet = new TreeSet<>();
            treeSet.add(value);
            this.addCollection(key, treeSet);
        }
    }

    public void removeItemFromCollection(IndexKey key, String input) {
        int value = getDoubleHash(input);
        if (collisions.get(key) == null) {
            System.out.println("Item not in collection");
         //   return false;
        } else {
            TreeSet<Integer> treeSet = collisions.get(key);
            treeSet.remove(value);
            if (!treeSet.isEmpty()) {
                this.addCollection(key, treeSet);
            } else {
                removeCollection(key);
            }
        }
    }



    public void removeCollection(IndexKey key) {
        if (collisions.get(key) == null) {
            System.out.println("Item not in collection");
        } else {
            collisions.remove(key);
        }
    }

    public int countInCollection(IndexKey key) {
        if (collisions.get(key) == null) {
            return 0;
        } else {
            return collisions.get(key).size();
        }
    }

    @Override
    public String toString() {
        return "CollisionStoreImpl{" +
                "collisions=" + collisions +
                '}';
    }
}
