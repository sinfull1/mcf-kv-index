package com.example.allocations.base;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class AllocationController {


    final AllocationRepository allocationRepository;

    public AllocationController(AllocationRepository allocationRepository) {
        this.allocationRepository = allocationRepository;
    }

    @GetMapping("/allocation")
    public Flux<AllocationTree> getAllocation() {
        return allocationRepository.getAllocations();
    }


    @PutMapping("/allocation/{id}")
    public Mono<AllocationTree> postAllocation(@PathVariable("id") String requestId) {

        AllocationTree allocationTreeOld = allocationRepository.findById(requestId).block();
        AllocationTree allocationTreeNew = new AllocationTree();
        allocationTreeNew.setId(allocationTreeOld.getId());
        List<TimeInterval> allocationSlotList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            TimeInterval interval =
                    generateRandomTimeInterval();
            List<Underlier> underlierList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Underlier underlier = new Underlier();
                underlier.setAllocationType(AllocationType.values()[random.nextInt(4)]);
                underlier.setId(UUID.randomUUID().toString());
                underlierList.add(underlier);
            }
            interval.setUnderlierList(underlierList);
            allocationSlotList.add(interval);
        }
        IntervalTree<TimeInterval> intervalIntervalTree = new IntervalTree<>(allocationSlotList);
        for (TimeInterval interval : allocationTreeOld.getSlots()) {
            intervalIntervalTree.insert(interval);
        }
        allocationTreeNew.setSlots(intervalIntervalTree.query(LocalDateTime.MIN, LocalDateTime.MAX));
        return allocationRepository.save(allocationTreeNew);
    }

    @PostMapping("/allocation")
    public Mono<AllocationTree> postAllocation() {
        AllocationTree allocationTree = new AllocationTree();
        List<TimeInterval> allocationSlotList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            TimeInterval interval =
                    generateRandomTimeInterval();
            List<Underlier> underlierList = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                Underlier underlier = new Underlier();
                underlier.setAllocationType(AllocationType.values()[random.nextInt(4)]);
                underlier.setId(UUID.randomUUID().toString());
                underlierList.add(underlier);
            }
            interval.setUnderlierList(underlierList);
            allocationSlotList.add(interval);
        }
        IntervalTree<TimeInterval> intervalIntervalTree = new IntervalTree<>(allocationSlotList);
        allocationTree.setSlots(intervalIntervalTree.query(LocalDateTime.MIN, LocalDateTime.MAX));
        return allocationRepository.save(allocationTree);
    }

    public static TimeInterval generateRandomTimeInterval() {
        // Define the range: from 3 years ago to now
        LocalDateTime threeYearsAgo = LocalDateTime.now().minusYears(3);
        LocalDateTime now = LocalDateTime.now();

        // Generate a random number of seconds between threeYearsAgo and now
        long minSecond = threeYearsAgo.toEpochSecond(java.time.ZoneOffset.UTC);
        long maxSecond = now.toEpochSecond(java.time.ZoneOffset.UTC);
        long randomSecond = ThreadLocalRandom.current().nextLong(minSecond, maxSecond);

        // Convert the random second back to LocalDateTime
        LocalDateTime randomStart = LocalDateTime.ofEpochSecond(randomSecond, 0, java.time.ZoneOffset.UTC);

        // Add 30 minutes to the start time to get the end time
        LocalDateTime randomEnd = randomStart.plus(30, ChronoUnit.MINUTES);

        // Return the TimeInterval
        return new TimeInterval(randomStart, randomEnd);
    }
}
