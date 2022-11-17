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

  @Bean(name = "oAuthFactory")
  public MongoDbFactory factory() {
      List<MongoCredential> credentialsList=new ArrayList<>();
      credentialsList.add(MongoCredential.createCredential(username,database,password.toCharArray()));
      MongoCredential mongoCredential=MongoCredential.createCredential(username,database,password.toCharArray());
     //springboot需要与spring-boot-starter-data-mongodb版本一致，否则报错
      MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
      MongoClientOptions mongoClientOptions = builder.build();
      MongoClient mongoClient=new MongoClient(new ServerAddress(host,port),mongoCredential,mongoClientOptions);
      return new SimpleMongoDbFactory(mongoClient,dbname);
}

    @Bean(name = "oAuthTemplate")
    public MongoTemplate template() throws Exception{
        return new MongoTemplate(factory());
    }



}
