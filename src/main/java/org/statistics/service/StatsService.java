package org.statistics.service;

import org.statistics.model.StatisticsSummary;
import org.statistics.model.Transaction;

public interface StatsService {

    void computeStatistics(Transaction transaction);

    StatisticsSummary getStats();

}
