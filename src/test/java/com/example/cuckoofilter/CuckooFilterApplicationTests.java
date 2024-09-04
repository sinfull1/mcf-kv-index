package com.example.cuckoofilter;

import com.example.allocations.base.IntervalTree;

import com.example.allocations.base.TimeInterval;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

import javax.swing.text.Segment;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


class CuckooFilterApplicationTests {

    DeleteInvariantFprCuckooFilter deleteInvariantFprCuckooFilter = new DeleteInvariantFprCuckooFilter(2, 3, 2, Hashing.murmur3_32_fixed());

    String[] results = new String[]{"sid", "stephen", "boris", "phillipe", "sid", "Sid"};

    @Test
    public void test() {
        for (String s : results) {
            deleteInvariantFprCuckooFilter.insert(s);
        }
        for (String s : results) {
            assert deleteInvariantFprCuckooFilter.lookup(s);
        }
        for (String s : results) {
            deleteInvariantFprCuckooFilter.delete(s);
        }
        for (String s : results) {
            assert !deleteInvariantFprCuckooFilter.lookup(s);
        }

    }

    @Test
    public void segmentTree() {

        List<TimeInterval> interval = Arrays.asList(
                new TimeInterval(LocalDateTime.of(2023, 9, 1, 9, 0), LocalDateTime.of(2023, 9, 1, 10, 0)),
                new TimeInterval(LocalDateTime.of(2023, 9, 1, 9, 30), LocalDateTime.of(2023, 9, 1, 11, 30)),
                new TimeInterval(LocalDateTime.of(2023, 9, 1, 12, 0), LocalDateTime.of(2023, 9, 1, 13, 0))
        );
        IntervalTree<TimeInterval> segmentTree = new IntervalTree<>(interval);
        System.out.println(segmentTree.query(LocalDateTime.MIN, LocalDateTime.MAX));
    }

}
