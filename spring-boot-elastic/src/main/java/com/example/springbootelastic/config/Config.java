package com.example.springbootelastic.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class Config {

    @Value("${indexName}")
    private String indexName;

    //es操作客户端
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
    //批量操作的对象
//    private static BulkProcessor bulkProcessor;

    @Value("${host.name}")
    private String hostName;

    @Value("${port}")
    private int port;

    @Value("${user.name}")
    private String username;

    @Value("${elastic.password}")
    private String password;

    @Value("${elastic.scheme}")
    private String scheme;


    @Bean
    public RestHighLevelClient restHighLevelClient() {
//        List<HttpHost> hostList=new ArrayList<>();
//        hostList.add(new HttpHost(hostName,port));
//        List<HttpHost> httpHosts=new ArrayList<>();
        //集群部署，测试完成，可用
        HttpHost host=new HttpHost(hostName,port);
        HttpHost httpHost=new HttpHost(hostName,port);
//        hostList.toArray(new HttpHost[0])
        RestClientBuilder builder=RestClient.builder(host,httpHost);
//                new HttpHost(hostName, port, scheme)
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(1000);
            requestConfigBuilder.setSocketTimeout(1000);
            requestConfigBuilder.setConnectionRequestTimeout(1000);
            return requestConfigBuilder;
        });

        //填充用户名密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //setCredentials可以同时添加多个验证账号和密码，对应RestClient.builder中的集群数据。
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(30);
            httpClientBuilder.setMaxConnPerRoute(30);
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });

        return new RestHighLevelClient(builder);
    }
//     {
//        bulkProcessor=createBulkProcessor();
//    }

//    public BulkProcessor createBulkProcessor() {
//        String name="zlh";
//        //监听操作结果
//        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
//            @Override
//            public void beforeBulk(long executionId, BulkRequest request) {
//                log.info("1. 【beforeBulk】批次[{}] 携带 {} 请求数量", executionId, request.numberOfActions());
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,
//                                  BulkResponse response) {
//                if (!response.hasFailures()) {
//                    log.info("2. 【afterBulk-成功】批量 [{}] 完成在 {} ms", executionId, response.getTook().getMillis());
//                } else {
//                    BulkItemResponse[] items = response.getItems();
//                    for (BulkItemResponse item : items) {
//                        if (item.isFailed()) {
//                            log.info("2. 【afterBulk-失败】批量 [{}] 出现异常的原因 : {}", executionId, item.getFailureMessage());
//                            break;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void afterBulk(long executionId, BulkRequest request,
//                                  Throwable failure) {
//
//                List<DocWriteRequest<?>> requests = request.requests();
//                List<String> esIds = requests.stream().map(DocWriteRequest::id).collect(Collectors.toList());
//                log.error("3. 【afterBulk-failure失败】es执行bluk失败,失败的esId为：{}", esIds, failure);
//            }
//        };
//
//        //批量操作数据
////        BulkProcessor.Builder builder =
//            return BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
//            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
//        }), listener,name)
//        //到达10000条时刷新
//        .setBulkActions(10000)
//        //内存到达8M时刷新
//        .setBulkSize(new ByteSizeValue(8L, ByteSizeUnit.MB))
//        //设置的刷新间隔10s
//        .setFlushInterval(TimeValue.timeValueSeconds(10))
//        //设置允许执行的并发请求数。
//        .setConcurrentRequests(8)
//        //设置重试策略
//        .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1), 3))
//        .build();
////        return builder.build();
//    }

}
