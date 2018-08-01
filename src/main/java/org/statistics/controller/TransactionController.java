package org.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.statistics.model.Transaction;
import org.statistics.service.TxnService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TxnService txnService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTxn(@RequestBody Transaction transaction) {
        txnService.addTxn(transaction);
    }
}
