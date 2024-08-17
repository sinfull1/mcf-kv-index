package com.example.cuckoofilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class CuckooController {

    @Autowired
    CuckooFilter cuckooFilter;

    @GetMapping("/put/{id}")
    public boolean put(@PathVariable("id") String id) {
       return cuckooFilter.insert(id);
    }
    @GetMapping("/check/{id}")
    public boolean check(@PathVariable("id") String id) {
        return cuckooFilter.lookup(id);
    }
    @GetMapping("/print/{id}")
    public void print() {
        cuckooFilter.printBucket();
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") String id) {
         cuckooFilter.delete(id);
    }

}
