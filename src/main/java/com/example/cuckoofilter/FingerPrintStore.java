package com.example.cuckoofilter;

public interface FingerPrintStore<T> {

    boolean add (T value);
    boolean remove(T value);
    boolean exists(T value);

}
