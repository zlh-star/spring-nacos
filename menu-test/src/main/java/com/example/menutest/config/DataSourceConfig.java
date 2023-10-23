package com.example.menutest.config;

//import com.sgcc.basictech.util.logging.InternalLogger;
//import com.sgcc.basictech.util.logging.Slf4jLoggerFactory;
//import com.sgcc.common.data.infrastructure.utils.DataEncrypUtils;
//import com.taobao.txc.datasource.cobar.TxcDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableAutoConfiguration(
        exclude = {DataSourceAutoConfiguration.class}
)
public class DataSourceConfig {
//    InternalLogger log = Slf4jLoggerFactory.getLogger(this.getClass());
    @Value("${spring.datasource.password}")
    private String pswd;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.driver-class-name}")
    private String driverName;
//    @Value("${spring.datasource.logFlag:false}")
//    private boolean flag;
    @Value("${spring.jdbc.query-timeout:60}")
    private Integer queryTimeOut;
    @Value("${spring.datasource.hikari.max-lifetime:1800000}")
    private Integer maxLifeTime;
    @Value("${spring.datasource.hikari.connection-timeout:30000}")
    private Integer connectionTimeout;
    @Value("${spring.datasource.hikari.idle-timeout:600000}")
    private Integer idleTimeout;
    @Value("${spring.datasource.hikari.maximum-pool-size:100}")
    private Integer maximumPoolSize;
    @Value("${spring.datasource.hikari.validation-timeout:5000}")
    private Integer validationTimeOut;
    @Value("${spring.datasource.hikari.minimum-idle:10}")
    private Integer minimumIdle;
    @Value("${spring.jdbc.custom.maxrows:2000}")
    private Integer queryMaxRows;

    public DataSourceConfig() {
    }

    @Bean(name = {"txcDataSource"})
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        try {
            dataSource.setLoginTimeout(5);
        } catch (SQLException var4) {
            log.error("设置loginTimeOut失败！");
        }

        dataSource.setJdbcUrl(url);
//        if (this.flag) {
//            String deEncrypUser = DataEncrypUtils.deEncryp(this.user);
//            dataSource.setUsername(deEncrypUser);
//            String deEncrypPwd = DataEncrypUtils.deEncryp(this.pswd);
//            dataSource.setPassword(deEncrypPwd);
//        } else {
            dataSource.setUsername(user);
            dataSource.setPassword(pswd);
            dataSource.setDriverClassName(driverName);
//        }

        dataSource.setIdleTimeout((long)idleTimeout);
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setConnectionTimeout((long)connectionTimeout);
        dataSource.setValidationTimeout((long)validationTimeOut);
        dataSource.setMaxLifetime((long)maxLifeTime);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        return dataSource;
    }

//    @Bean(
//            name = {"dataSourceProxy"}
//    )
//    @Primary
//    public TxcDataSource dataSourceProxy() {
//        return new TxcDataSource(this.dataSource());
//    }

    @Bean(name = {"queryJdbcTemplate"})
    @RefreshScope
    public JdbcTemplate queryJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource());
        jdbcTemplate.setFetchSize(2000);
        jdbcTemplate.setMaxRows(queryMaxRows);
        jdbcTemplate.setQueryTimeout(queryTimeOut);
        return jdbcTemplate;
    }

    @Bean(name = {"jdbcTemplate"})
    @RefreshScope
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource());
        jdbcTemplate.setMaxRows(100000);
//        jdbcTemplate.setQueryTimeout(this.queryTimeOut);
        return new JdbcTemplate(this.dataSource());
    }

    @Bean
    @RefreshScope
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(this.dataSource());
    }
}

