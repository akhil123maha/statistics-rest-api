package org.statistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.statistics.common.ApiException;
import org.statistics.exception.OutdatedTransactionException;
import org.statistics.exception.ValidationException;
import org.statistics.model.Transaction;

import static org.statistics.common.TimeUtil.isValidTransactionTimestamp;


@Service
public class TxnServiceImpl implements TxnService {

    private static final Logger logger = LoggerFactory.getLogger(TxnServiceImpl.class);

    @Autowired
    private StatsService statsService;

    @Override
    public void addTxn(Transaction transaction) {
        if(transaction == null) throw new ValidationException(ApiException.VALIDATION_EMPTY_REQUEST_BODY);
        if(transaction.getTimestamp() == null) throw new ValidationException(ApiException.VALIDATION_MISSING_TIMESTAMP);
        if(transaction.getAmount() == null) throw new ValidationException(ApiException.VALIDATION_MISSING_AMOUNT);
        if(!isValidTransactionTimestamp(transaction.getTimestamp())) throw new OutdatedTransactionException(ApiException.OUTDATED_TRANSACTION);

        logger.info("Received new transaction => {}", transaction);
        statsService.computeStatistics(transaction);
    }
}
