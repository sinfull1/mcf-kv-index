package com.example.allocations.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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

    @PostMapping("/allocation")
    public Mono<AllocationTree> postAllocation() {
        AllocationTree allocationTree = new AllocationTree();
        List<TimeInterval> allocationSlotList = new ArrayList<>();
        LocalDateTime current = LocalDateTime.now();
        long seconds = current.toEpochSecond(ZoneOffset.UTC);
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            TimeInterval interval = new TimeInterval(LocalDateTime.ofEpochSecond(seconds + random.nextLong(0, 500), 0, ZoneOffset.UTC),
                    LocalDateTime.ofEpochSecond(seconds + random.nextLong(500, 1000), 0, ZoneOffset.UTC));
            List<Underlier> underlierList = new ArrayList<>();
            for (int j=0;j<4;j++){
                Underlier underlier = new Underlier();
                underlier.setAllocationType(AllocationType.values()[random.nextInt(4)]);
                underlier.setId(UUID.randomUUID().toString());
                underlierList.add(underlier);
            }
            interval.setUnderlierList(underlierList);
            allocationSlotList.add(interval);
        }
        allocationTree.setSlots(allocationSlotList);
        return allocationRepository.save(allocationTree);
    }

}
