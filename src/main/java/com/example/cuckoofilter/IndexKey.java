package com.example.cuckoofilter;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndexKey implements Comparable<IndexKey> {

    private short index;
    private short index1;
    private String key;

    @Override
    public int compareTo(IndexKey o) {
        if (o.getKey().equals(this.key)) {
            return Math.abs(o.getIndex() - this.getIndex()) + Math.abs(o.getIndex1() - this.getIndex1());
        }
        return o.getKey().compareTo(this.key);
    }
}
