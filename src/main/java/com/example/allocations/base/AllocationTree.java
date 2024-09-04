package com.example.allocations.base;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.List;

@Document
@NoArgsConstructor
@Getter
@Setter
public class AllocationTree {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    String id;
    List<TimeInterval> slots;
}
