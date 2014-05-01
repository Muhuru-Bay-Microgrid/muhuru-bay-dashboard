package org.mbmg.mbmg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * I class that loads our data from the filesystem
 * <p>
 * Created by rpomeroy on 4/26/14.
 */
@Component
public class Loader {

    @Autowired
    private DashboardRepository dashboardRepository;

    public void load(String filePath) throws Exception {
        dashboardRepository.save(Parser.parseFile(filePath));
    }
}
