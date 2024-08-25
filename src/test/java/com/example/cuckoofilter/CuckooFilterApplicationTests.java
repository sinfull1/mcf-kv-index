package com.example.cuckoofilter;

import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;


class CuckooFilterApplicationTests {

    DeleteInvariantFprCuckooFilter deleteInvariantFprCuckooFilter = new DeleteInvariantFprCuckooFilter(2,3,2, Hashing.murmur3_32_fixed());

    String[] results = new String[] {"sid", "stephen", "boris", "phillipe"};

    @Test
    public void test() {
        for( String s: results) {
            deleteInvariantFprCuckooFilter.insert(s);
        }
        for( String s: results) {
            assert  deleteInvariantFprCuckooFilter.lookup(s);
        }
        for( String s: results) {
            assert  deleteInvariantFprCuckooFilter.delete(s);
        }
        for( String s: results) {
            assert  !deleteInvariantFprCuckooFilter.lookup(s);
        }

    }

}
