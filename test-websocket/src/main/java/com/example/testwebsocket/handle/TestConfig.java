package com.example.testwebsocket.handle;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public  class TestConfig {


    @Value("${host.name}")
    private String hostName;

    @Value("${port}")
    private int port;

    @Value("${user.name}")
    private String userName;

    @Value("${elastic.password}")
    private String password;

    //es操作客户端
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
    //批量操作的对象
//    @Autowired
//    private BulkProcessor bulkProcessor;

    @Bean
    public RestHighLevelClient restHigh(){
//        List<HttpHost> httpHostList=new ArrayList<>();
//       httpHostList.add(new HttpHost(hostName,port));
        //集群部署，测试完成可正常使用
       HttpHost httpHost=new HttpHost(hostName,port);
        HttpHost httpHost1=new HttpHost(hostName,port);
//        httpHostList.toArray(new HttpHost[0])
//        String.valueOf(httpHostList.add(new HttpHost(hostName,port)))
        RestClientBuilder builder= RestClient.builder(httpHost,httpHost1);

        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(3000);
            requestConfigBuilder.setSocketTimeout(3000);
            requestConfigBuilder.setConnectionRequestTimeout(3000);
            return requestConfigBuilder;
        });

        //填充用户名密码
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //setCredentials可以同时添加多个验证账号和密码，对应RestClient.builder中的集群数据。
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName,password));
        credentialsProvider.setCredentials(AuthScope.ANY,new UsernamePasswordCredentials(userName,password));

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
//        String name = "zlh";
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
////        BulkProcessor.Builder builder =
//               return BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
//            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
//        }), listener, name)
//                //到达10000条时刷新
//                .setBulkActions(10000)
//                //内存到达8M时刷新
//                .setBulkSize(new ByteSizeValue(8L, ByteSizeUnit.MB))
//                //设置的刷新间隔10s
//                .setFlushInterval(TimeValue.timeValueSeconds(10))
//                //设置允许执行的并发请求数。
//                .setConcurrentRequests(8)
//                //设置重试策略
//                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1), 3))
//                .build();
//
////        return builder.build();
//    }
}
