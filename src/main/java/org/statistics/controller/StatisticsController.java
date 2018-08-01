package org.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.statistics.model.StatisticsSummary;
import org.statistics.service.StatsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatsService statsService;

    @GetMapping
    public StatisticsSummary getStatistics(){
        return statsService.getStats();
    }
}
