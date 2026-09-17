package com.example.backenddemo.controller;

import com.example.backenddemo.service.OptimisticLockTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OptimisticLockTestController {
    private final OptimisticLockTestService optimisticLockTestService;
    public OptimisticLockTestController(OptimisticLockTestService optimisticLockTestService){
        this.optimisticLockTestService=optimisticLockTestService;
    }

    @GetMapping("/test/optimistic-lock")
    public String testOptimisticLock(){
        optimisticLockTestService.testOptimisticLock();
        return "更新成功";
    }
}
