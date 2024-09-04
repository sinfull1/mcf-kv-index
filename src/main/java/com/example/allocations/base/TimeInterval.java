package com.example.allocations.base;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class TimeInterval {


    private LocalDateTime start;
    private LocalDateTime end;
    private LocalDateTime created;

    private List<Underlier> underlierList;

    public TimeInterval(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
        this.created = LocalDateTime.now();
    }



    @Override
    public String toString() {
        return "TimeInterval{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}
