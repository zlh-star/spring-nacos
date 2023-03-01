//package com.example.elastic.test;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.HttpHost;
//import org.apache.http.auth.AuthScope;
//import org.apache.http.auth.UsernamePasswordCredentials;
//import org.apache.http.client.CredentialsProvider;
//import org.apache.http.impl.client.BasicCredentialsProvider;
//import org.elasticsearch.action.DocWriteRequest;
//import org.elasticsearch.action.bulk.*;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.ByteSizeUnit;
//import org.elasticsearch.common.unit.ByteSizeValue;
//import org.elasticsearch.common.unit.TimeValue;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Configuration
//@Slf4j
//public class Config {
//
//    @Value("${indexName}")
//    private String indexName;
//
//    //es操作客户端
//    private static RestHighLevelClient restHighLevelClient;
//    //批量操作的对象
//    private static BulkProcessor bulkProcessor;
//
//    @Value("${host.name}")
//    private String hostName;
//
//    @Value("${port}")
//    private int port;
//
//    @Value("${user.name}")
//    private String username;
//
//    @Value("${elastic.password}")
//    private String password;
//
//    @Value("${elastic.scheme}")
//    private String scheme;
//
//
//    public void restHighLevelClient(){
//        RestClientBuilder builder=RestClient.builder(
//                new HttpHost(hostName, port, scheme));
//
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(1000);
//            requestConfigBuilder.setSocketTimeout(1000);
//            requestConfigBuilder.setConnectionRequestTimeout(1000);
//            return requestConfigBuilder;
//        });
//
//        //填充用户名密码
//        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
//
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            httpClientBuilder.setMaxConnTotal(30);
//            httpClientBuilder.setMaxConnPerRoute(30);
//            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
//            return httpClientBuilder;
//        });
//
//        restHighLevelClient = new RestHighLevelClient(builder);
//
//
//    }
////    static {
////        bulkProcessor=createBulkProcessor();
////    }
//
//    public  BulkProcessor createBulkProcessor() {
//
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
//           }
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
//        BulkProcessor.Builder builder = BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
//            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
//        }), listener);
//        //到达10000条时刷新
//        builder.setBulkActions(10000);
//        //内存到达8M时刷新
//        builder.setBulkSize(new ByteSizeValue(8L, ByteSizeUnit.MB));
//        //设置的刷新间隔10s
//        builder.setFlushInterval(TimeValue.timeValueSeconds(10));
//        //设置允许执行的并发请求数。
//        builder.setConcurrentRequests(8);
//        //设置重试策略
//        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1), 3));
//        return builder.build();
//    }
//
//}
