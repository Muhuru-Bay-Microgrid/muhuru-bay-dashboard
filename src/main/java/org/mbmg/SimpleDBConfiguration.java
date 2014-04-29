package org.mbmg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.simpledb.core.SimpleDb;
import org.springframework.data.simpledb.core.SimpleDbTemplate;
import org.springframework.data.simpledb.core.domain.DomainManagementPolicy;

import javax.validation.constraints.NotNull;

/**
 * Created by rpomeroy on 4/26/14.
 * https://github.com/3pillarlabs/spring-data-simpledb
 */
@Configuration
@PropertySource("file:///${HOME}/AWSCredentials.properties")
@ConfigurationProperties
public class SimpleDBConfiguration {

    @NotNull
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${domainPrefix:MuhuruBay}")
    private String domainPrefix;
    @Value("${dbUnavailableRetries:10}")
    private int dbUnavailableRetries;

    @Bean
    public SimpleDbTemplate simpleDBTemplate1() {
        SimpleDb simpleDb = new SimpleDb(accessKey, secretKey);
        simpleDb.setConsistentRead(true);
        simpleDb.setDomainPrefix(domainPrefix);
        simpleDb.setDomainManagementPolicy(DomainManagementPolicy.DROP_CREATE);
        simpleDb.setUnavailableServiceRetries(dbUnavailableRetries);
        simpleDb.afterPropertiesSet();
        return new SimpleDbTemplate(simpleDb);

    }
}
