package com.example.cuckoofilter;

import java.util.Collection;
import java.util.Set;

public interface CollisionStore<K, T extends Collection<E>, E, G> {
    public void addCollection(K key, T collection);

    public void addItemInCollection(K key, G value);

    public void removeItemFromCollection(K key, G value);

    public void removeCollection(K key);

    public int countInCollection(K key);
}
