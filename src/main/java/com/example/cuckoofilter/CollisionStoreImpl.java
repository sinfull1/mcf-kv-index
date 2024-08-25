package com.example.cuckoofilter;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class CollisionStoreImpl implements CollisionStore<IndexKey, TreeSet<String>, String> {

    private final TreeMap<IndexKey, TreeSet<String>> collisions = new TreeMap<>();

    public void addCollection(IndexKey key, TreeSet<String> collection) {
        collisions.putIfAbsent(key, collection);
    }

    public boolean contains(IndexKey key, String value) {
        if (collisions.get(key) != null) {
            return false;
        } else {
            return collisions.get(key).contains(value);
        }
    }


    public void addItemInCollection(IndexKey key, String value) {
        if (collisions.get(key) != null) {
            Set<String> set = collisions.get(key);
            set.add(value);
        } else {
            TreeSet<String> treeSet = new TreeSet<>();
            treeSet.add(value);
            this.addCollection(key, treeSet);
        }
    }

    public void removeItemFromCollection(IndexKey key, String value) {
        if (collisions.get(key) == null) {
            System.out.println("Item not in collection");
         //   return false;
        } else {
            TreeSet<String> treeSet = collisions.get(key);
            treeSet.remove(value);
            if (!treeSet.isEmpty()) {
                this.addCollection(key, treeSet);
            } else {
                removeCollection(key);
            }
       //     return true;
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
