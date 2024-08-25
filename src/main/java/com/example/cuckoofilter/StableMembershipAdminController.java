package com.example.cuckoofilter;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class StableMembershipAdminController {


    final DeleteInvariantFprCuckooFilter deleteInvariantFprCuckooFilter;

    public StableMembershipAdminController() {
        this.deleteInvariantFprCuckooFilter = new DeleteInvariantFprCuckooFilter((short) 100, (short) 3, (short) 100, Hashing.murmur3_32_fixed());
    }


    @GetMapping("/put/{id}")
    public boolean put(@PathVariable("id") String id) {
        return deleteInvariantFprCuckooFilter.insert(id);
    }

    @GetMapping("/check/{id}")
    public boolean check(@PathVariable("id") String id) {
        return deleteInvariantFprCuckooFilter.lookup(id);
    }

    @GetMapping("/print/{id}")
    public void print() {
        deleteInvariantFprCuckooFilter.printBucket();
    }

    @GetMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") String id) {
        return deleteInvariantFprCuckooFilter.delete(id);
    }

    @GetMapping("/print/{index}/{index1}")
    public void print(@PathVariable("index") int index, @PathVariable("index1") int index1) {
        //cuckooFilter.print(index, index1);
    }
}
