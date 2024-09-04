package com.example.allocations.base;

public interface AllocationStore<K, T> {
    /**
     * Build the Segment Tree based on the input array.
     *
     * @param arr the input array to build the Segment Tree from.
     */
    void build(T[] arr);

    /**
     * Query the Segment Tree for a specific range.
     *
     * @param l the starting index of the range (inclusive).
     * @param r the ending index of the range (inclusive).
     * @return the result of the query for the specified range.
     */
    T query(K l, K r);

    /**
     * Update an element in the Segment Tree.
     *
     * @param idx the index of the element to update.
     * @param value the new value of the element.
     */
    void update(K idx, T value);

    /**
     * Combine the results from two segments.
     * This operation will define the behavior of the Segment Tree (e.g., sum, minimum, maximum).
     *
     * @param left the result from the left segment.
     * @param right the result from the right segment.
     * @return the combined result.
     */
    T combine(T left, T right);

}

