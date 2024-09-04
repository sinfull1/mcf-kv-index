package com.example.allocations.base;


import com.couchbase.client.java.query.QueryScanConsistency;
import org.springframework.data.couchbase.repository.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository("allocationRepository")
@Scope("prod")
@Collection("allocations")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
public interface AllocationRepository extends ReactiveCouchbaseRepository<AllocationTree, String>, DynamicProxyable<AllocationRepository> {

    @Query(" select META(d).id AS __id, META(d).cas AS __cas, d.* from allocation.`prod`.allocations d ")
    Flux<AllocationTree> getAllocations();

}