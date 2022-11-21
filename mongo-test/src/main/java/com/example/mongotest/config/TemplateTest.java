package com.example.mongotest.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TemplateTest {

    @Value("${mongodb.port}")
    private int port;

    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.database}")
    private String database;

    @Value("${mongodb.dbname}")
    private String dbname;

    @Value("${mongodb.username}")
    private String username;
    @Value("${mongodb.password}")
    private String password;

    @Value("${spring.data.mongodb.option.max-connection-per-host}")
    private int maxConnectionPerHost;

    @Value("${spring.data.mongodb.option.min-connection-per-host}")
    private int minConnectionPerHost;

    @Value("${spring.data.mongodb.option.server-selection-timeout}")
    private int serverSelectionsTimeout;

    @Value("${spring.data.mongodb.option.threads-allowed-to-block-for-connection-multiplier}")
    private int threadsAllowedToBlockForConnection;

    @Value("${spring.data.mongodb.option.max-wait-time}")
    private int maxWaitTimes;

    @Value("${spring.data.mongodb.option.max-connection-idle-time}")
    private int maxConnectionIdleTimes;

    @Value("${spring.data.mongodb.option.max-connection-life-time}")
    private int maxConnectionLifeTimes;

    @Value("${spring.data.mongodb.option.connect-timeout}")
    private int connectTimeouts;

    @Value("${spring.data.mongodb.option.socket-timeout}")
    private int socketTimeouts;

    @Value("${spring.data.mongodb.option.socket-keep-alive}")
    private boolean socketKeepAlives;

    @Value("${spring.data.mongodb.option.ssl-enabled}")
    private boolean sslEnable;

    @Value("${spring.data.mongodb.option.ssl-invalid-host-name-allowed}")
    private boolean sslInvalidHostNameAllowe;

    @Value("${spring.data.mongodb.option.always-use-m-beans}")
    private boolean alwaysUseMBean;

    @Value("${spring.data.mongodb.option.heartbeat-socket-timeout}")
    private int heartbeatSocketTimeouts;

    @Value("${spring.data.mongodb.option.heartbeat-connect-timeout}")
    private int heartbeatConnectTimeouts;

    @Value("${spring.data.mongodb.option.min-heartbeat-frequency}")
    private int minHeartbeatFrequences;

    @Value("${spring.data.mongodb.option.heartbeat-frequency}")
    private int heartbeatFrequences;

    @Value("${spring.data.mongodb.option.local-threshold}")
    private int localThresholdes;

  @Bean(name = "oAuthFactory")
  public MongoDbFactory factory() {
      List<MongoCredential> credentialsList=new ArrayList<>();
      credentialsList.add(MongoCredential.createCredential(username,database,password.toCharArray()));
      MongoCredential mongoCredential=MongoCredential.createCredential(username,database,password.toCharArray());
     //springboot需要与spring-boot-starter-data-mongodb版本一致，否则报错
      MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
              //host允许链接的最大链接数
      builder.connectionsPerHost(maxConnectionPerHost)
              .minConnectionsPerHost(minConnectionPerHost)
              .serverSelectionTimeout(serverSelectionsTimeout)
              //线程队列数
              .threadsAllowedToBlockForConnectionMultiplier(threadsAllowedToBlockForConnection)
              .maxWaitTime(maxWaitTimes)
              .maxConnectionIdleTime(maxConnectionIdleTimes)
              .maxConnectionLifeTime(maxConnectionLifeTimes)
              .connectTimeout(connectTimeouts)
              .socketTimeout(socketTimeouts)
              .socketKeepAlive(socketKeepAlives)
              .sslEnabled(sslEnable)
              .sslInvalidHostNameAllowed(sslInvalidHostNameAllowe)
              .alwaysUseMBeans(alwaysUseMBean)
              .heartbeatSocketTimeout(heartbeatSocketTimeouts)
              .heartbeatConnectTimeout(heartbeatConnectTimeouts)
              .minHeartbeatFrequency(minHeartbeatFrequences)
              .heartbeatFrequency(heartbeatFrequences)
              .localThreshold(localThresholdes);
      MongoClientOptions mongoClientOptions = builder.build();
      MongoClient mongoClient=new MongoClient(new ServerAddress(host,port),mongoCredential,mongoClientOptions);
      return new SimpleMongoDbFactory(mongoClient,dbname);
}

    @Bean(name = "oAuthTemplate")
    public MongoTemplate template() throws Exception{
        return new MongoTemplate(factory());
    }



}
