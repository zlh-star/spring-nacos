package com.example.provider2.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class HbaseConfig {

    @Bean(name = "connection")
    public Connection connection() throws IOException {
        org.apache.hadoop.conf.Configuration configuration= HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","127.0.0.1:2181");
        return ConnectionFactory.createConnection(configuration);
    }
}
