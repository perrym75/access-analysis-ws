package com.infosec.accanalysis.api.rest.controller;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/1.0/rule-process")
public class RuleProcessingController {

    private final AtomicLong counter = new AtomicLong();

    @PutMapping
    public int processRules() {
        return 0;
    }

    @GetMapping("/{id}")
    public long getProcessProgress(@PathVariable(value="id") int id) {
        return counter.incrementAndGet();
    }
}
