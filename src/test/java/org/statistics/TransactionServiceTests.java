package org.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.statistics.exception.OutdatedTransactionException;
import org.statistics.exception.ValidationException;
import org.statistics.model.Transaction;
import org.statistics.service.StatsServiceImpl;
import org.statistics.service.TxnService;
import org.statistics.service.TxnServiceImpl;

import static org.mockito.Mockito.*;

/**
 * @author zaur.guliyev
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTests {

    @Autowired
    private TxnService txnService;


    @Mock
    private StatsServiceImpl statisticsServiceMock;

    @InjectMocks
    private TxnServiceImpl transactionServiceMock;


    @Test(expected = ValidationException.class)
    public void whenEmptyRequestBody_exceptionThrown() {
        txnService.addTxn(null);
    }

    @Test(expected = OutdatedTransactionException.class)
    public void whenOutdatedTxn_exceptionThrown() {
        txnService.addTxn(new Transaction(100.0, System.currentTimeMillis()-70000));
    }

    @Test(expected = ValidationException.class)
    public void whenMissingTimestampField_exceptionThrown() {
        txnService.addTxn(new Transaction(12.5, null));
    }

    @Test(expected = ValidationException.class)
    public void whenMissingAmountField_exceptionThrown() {
        txnService.addTxn(new Transaction(null, System.currentTimeMillis()));
    }

    @Test
    public void whenValidTransaction_flowSucceeds(){
        doNothing().when(statisticsServiceMock).computeStatistics(any(Transaction.class));
        transactionServiceMock.addTxn(new Transaction(12.5, System.currentTimeMillis()));

        verify(statisticsServiceMock, times(1)).computeStatistics(any(Transaction.class));
    }
}
