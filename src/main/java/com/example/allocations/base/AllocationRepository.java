package com.example.allocations.base;


import com.couchbase.client.java.query.QueryScanConsistency;
import org.springframework.data.couchbase.repository.*;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository("allocationRepository")
@Scope("prod")
@Collection("allocations")
@ScanConsistency(query = QueryScanConsistency.REQUEST_PLUS)
public interface AllocationRepository extends ReactiveCouchbaseRepository<AllocationTree, String>, DynamicProxyable<AllocationRepository> {

    @Query(" select META(d).id AS __id, META(d).cas AS __cas, d.* from allocation.`prod`.allocations d ")
    Flux<AllocationTree> getAllocations();

    //CREATE INDEX adv_DISTINCT_slots_start_end ON `default`:`allocation`.`prod`.`allocations`(DISTINCT ARRAY FLATTEN_KEYS(`slot`.`end`,`slot`.`start`) FOR `slot` IN `slots` END)

    @Query(" select META(d).id AS __id, META(d).cas AS __cas, d.* from allocation.`prod`.allocations d " +
            " WHERE ANY slot IN slots SATISFIES slot.start >= $1 AND slot.end <= $2 END;")
    Flux<AllocationTree> getAllocationByTimeRange(long start, long end);

}