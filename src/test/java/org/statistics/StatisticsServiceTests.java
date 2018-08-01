package org.statistics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.statistics.model.StatisticsSummary;
import org.statistics.model.Transaction;
import org.statistics.service.StatsService;

import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTests {

    @Autowired
    private StatsService statsService;

    @Test
    public void whenOutdatedTimestamp_doNothing(){
        StatisticsSummary summaryBefore = statsService.getStats();
        statsService.computeStatistics(new Transaction(10.5, System.currentTimeMillis() - 60001));
        assertEquals(summaryBefore, statsService.getStats());
    }

    @Test
    public void whenValidTimestamp_computeStatistics(){
        statsService.computeStatistics(new Transaction(5.5, System.currentTimeMillis() - 10000));
        statsService.computeStatistics(new Transaction(15.5, System.currentTimeMillis() - 9000));
        statsService.computeStatistics(new Transaction(25.2, System.currentTimeMillis() - 8000));
        statsService.computeStatistics(new Transaction(65.5, System.currentTimeMillis() - 7000));
        statsService.computeStatistics(new Transaction(5.7, System.currentTimeMillis() - 6000));
        statsService.computeStatistics(new Transaction(5.8, System.currentTimeMillis() - 5000));
        statsService.computeStatistics(new Transaction(3.5, System.currentTimeMillis() - 4000));
        statsService.computeStatistics(new Transaction(2.8, System.currentTimeMillis() - 3000));
        statsService.computeStatistics(new Transaction(9.5, System.currentTimeMillis() - 2000));
        statsService.computeStatistics(new Transaction(12.3, System.currentTimeMillis() - 1000));

        StatisticsSummary summary = statsService.getStats();
        assertEquals(summary.getCount(), 10l);
        assertEquals(summary.getSum(), 151.3, 0.0);
        assertEquals(summary.getMax(), 65.5, 0.0);
        assertEquals(summary.getMin(), 2.8, 0.0);
        assertEquals(summary.getAvg(), 151.3 / 10, 0.0);
    }

    @Test
    public void whenValidTimestamp_computeStatistics_concurrencyTest(){


        IntStream.range(1000, 10000).parallel().forEach(i -> compute(i));

        StatisticsSummary summary = statsService.getStats();
        assertEquals(summary.getCount(), 9010L);
        assertEquals(summary.getSum(), 49651.3, 0.0);
        assertEquals(summary.getMax(), 65.5, 0.0);
        assertEquals(summary.getMin(), 2.8, 0.0);
        assertEquals(summary.getAvg(), 5.510688124306327, 0.0);
    }

    private void compute(int i) {
        statsService.computeStatistics(new Transaction(5.5, System.currentTimeMillis() - i));
    }

}
