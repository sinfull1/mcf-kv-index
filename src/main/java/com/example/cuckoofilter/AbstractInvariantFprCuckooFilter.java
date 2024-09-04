package com.example.cuckoofilter;

import java.util.Collection;

public class AbstractInvariantFprCuckooFilter<K, T extends Collection<E>, E, G> {

    CollisionStore<K, T, E, G> collisionStore;
    FingerPrintStore<G> fingerPrintStore;

}
