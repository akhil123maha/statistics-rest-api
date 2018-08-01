package org.statistics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.statistics.model.Statistics;
import org.statistics.model.StatisticsSummary;
import org.statistics.model.Transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.statistics.common.TimeUtil.isValidTransactionTimestamp;

@Service
public class StatsServiceImpl implements StatsService {

    private static final Logger logger = LoggerFactory.getLogger(StatsServiceImpl.class);

    private static final Map<Integer, Statistics> statsForLastMin = new ConcurrentHashMap<>(60);


    @Override
    public void computeStatistics(Transaction transaction) {
        logger.info("Computing statistics based on new received transaction => {}", transaction);

        if (isValidTransactionTimestamp(transaction.getTimestamp())) {
            int second = LocalDateTime.ofInstant(Instant.ofEpochMilli(transaction.getTimestamp()), ZoneId.systemDefault()).getSecond();
            statsForLastMin.compute(second, (k, v) -> {
                if (v == null || !isValidTransactionTimestamp(transaction.getTimestamp())) {
                    v = new Statistics();
                    v.setTimestamp(transaction.getTimestamp());
                    v.setSum(transaction.getAmount());
                    v.setMax(transaction.getAmount());
                    v.setMin(transaction.getAmount());
                    v.setCount(1l);
                    return v;
                }

                v.setCount(v.getCount() + 1);
                v.setSum(v.getSum() + transaction.getAmount());
                if (Double.compare(transaction.getAmount(), v.getMax()) > 0) v.setMax(transaction.getAmount());
                if (Double.compare(transaction.getAmount(), v.getMin()) < 0) v.setMin(transaction.getAmount());
                return v;
            });
        }
    }

    @Override
    public StatisticsSummary getStats() {
        StatisticsSummary summary = statsForLastMin.values().stream()
                .filter(s -> isValidTransactionTimestamp(s.getTimestamp()))
                .map(StatisticsSummary::new)
                .reduce(new StatisticsSummary(), (s1, s2) -> {
                    s1.setSum(s1.getSum() + s2.getSum());
                    s1.setCount(s1.getCount() + s2.getCount());
                    s1.setMax(Double.compare(s1.getMax(), s2.getMax()) > 0 ? s1.getMax() : s2.getMax());
                    s1.setMin(Double.compare(s1.getMin(), s2.getMin()) < 0 ? s1.getMin() : s2.getMin());
                    return s1;
                });

        summary.setMin(Double.compare(summary.getMin(), Double.MAX_VALUE) == 0 ? 0.0 : summary.getMin());
        summary.setMax(Double.compare(summary.getMax(), Double.MIN_VALUE) == 0 ? 0.0 : summary.getMax());
        summary.setAvg(summary.getCount() > 0l ? summary.getSum() / summary.getCount() : 0.0);

        logger.info("Statistics summary for last minute => {}", summary);
        return summary;
    }
}
