package com.example.backenddemo.controller;

import com.example.backenddemo.service.TransactionTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionTestController {
    private final TransactionTestService transactionTestService;
    public TransactionTestController(TransactionTestService transactionTestService){
        this.transactionTestService=transactionTestService;
    }

    @GetMapping("/test/transaction")
    public String testTransaction(){
        transactionTestService.testTransaction();
        return "执行成功";
    }
}
