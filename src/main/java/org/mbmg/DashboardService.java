package org.mbmg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TODO: add methods to support the UI queries for the data from repository transforming Records to DataSeries (dCharts
 * widget's native model type)
 */
@Service
public class DashboardService {

    @Value("${message.text}")
    String injectedMessage;
    private int count = 0;
    @Autowired
    private DashboardRepository dashboardRepository;

    public String sayHello() {
        return injectedMessage + (" + count++ + ");

    }
}