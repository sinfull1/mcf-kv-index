package com.example.cuckoofilter;

import java.util.Collection;
import java.util.Set;

public interface CollisionStore<K, T extends Collection<E>, E> {
    public void addCollection(K key, T collection);

    public void addItemInCollection(K key, E value);

    public void removeItemFromCollection(K key, E value);

    public void removeCollection(K key);

    public int countInCollection(K key);
}
