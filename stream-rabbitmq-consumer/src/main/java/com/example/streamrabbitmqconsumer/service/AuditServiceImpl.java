package com.example.streamrabbitmqconsumer.service;

import com.alibaba.fastjson.JSON;
import com.example.streamrabbitmqconsumer.bean.DemoDto;
import com.example.streamrabbitmqconsumer.config.TestConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuditServiceImpl implements AuditService {

    @Value("${indexName}")
    private String indexName;

    //es操作客户端
    @Autowired
    private RestHighLevelClient restHigh;
//    //批量操作的对象
//    private static BulkProcessor bulkProcessor;

    @Autowired
    private TestConfig testConfig;



    @Autowired
    private BulkProcessor bulkProcessor;

    @Autowired
    private AuditService auditService;


    @Override
    public void insertLog(List<DemoDto> auditBoList) {
        List<IndexRequest> indexRequests = new ArrayList<>();
        //批量插入日志
        auditBoList.forEach(e -> {
            IndexRequest request = new IndexRequest(indexName);
            request.source(JSON.toJSONString(e), XContentType.JSON)
                   .opType(DocWriteRequest.OpType.CREATE);
            indexRequests.add(request);
        });
//        indexRequests.forEach(testConfig.createBulkProcessor()::add);
        indexRequests.forEach(bulkProcessor::add);
    }

    @Override
    public void inLog(DemoDto demoDto) {
        //单条插入
        IndexRequest indexRequest=new IndexRequest(indexName);
        indexRequest.source(JSON.toJSONString(demoDto),XContentType.JSON)
                .opType(DocWriteRequest.OpType.CREATE)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                .create(true)
                .id(demoDto.getId()+"");
        try {
            restHigh.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLog(String id) {
        DeleteRequest deleteRequest=new DeleteRequest(indexName,id);
        try {
            restHigh.delete(deleteRequest,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLogById(List<String> idList) {
        //批量删除es日志
        List<DeleteRequest> indexRequests = new ArrayList<>();
        try {
            idList.forEach(id->{
                DeleteRequest deleteRequest=new DeleteRequest(indexName,id);
                indexRequests.add(deleteRequest);
            });
//            indexRequests.forEach(bulkProcessor::add);
            indexRequests.forEach(index->{
                bulkProcessor.add(index);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public List<Object> test(List<Map<String, Object>> test, int count) {
//        return auditService.test(test, count) ;
//    }

//    @Override
//    public void updateLog(List<DemoDto> demoDtos,String id) {
//        List<UpdateQuery> updateQueries=new ArrayList<>();
//        Map<String, Object> map = new HashMap<>();
//        map.put("id","12345");
//        UpdateRequest updateRequest = new UpdateRequest();
//        updateRequest.doc(map);
//        UpdateQuery updateQuery = new UpdateQueryBuilder()
//                .withId("552629576220545024")
//                .withIndexName("esdto")
//                .withType("esdto")
//                .withUpdateRequest(updateRequest)
//                .build();
//        updateQueries.add(updateQuery);
//        List<UpdateRequest> updateRequests=new ArrayList<>();
//        demoDtos.forEach(e->{
//        for(DemoDto demoDto:demoDtos) {
//
//            UpdateRequest updateRequest = new UpdateRequest(indexName,id);
//            Map<String, Object> map = new HashMap<>();
//            map.put("id", demoDto.getId());
//            map.put("name", demoDto.getName());
//            map.put("name1", demoDto.getName1());
//            Script script1 = new Script(Script.DEFAULT_SCRIPT_TYPE, Script.DEFAULT_SCRIPT_LANG,id, map);
//            updateRequests.add(updateRequest.script(script1));
//        }
//        });
//        updateRequests.forEach(testConfig.createBulkProcessor()::add);
//    }

//    public void Config() {
//        List<HttpHost> httpHosts = new ArrayList<>();
//        //填充数据
//        httpHosts.add(new HttpHost(hostName, port));
//        httpHosts.add(new HttpHost("172.26.17.11", 9201));
//        httpHosts.add(new HttpHost("172.26.17.11", 9202));
        //填充host节点
//        RestHighLevelClient builder = new RestHighLevelClient(RestClient.builder(httpHosts.toArray(new HttpHost[0]));
//                RestClient.builder(httpHosts.toArray(new HttpHost[0]));
//        RestClientBuilder builder= RestClient.builder(httpHosts.toArray(new HttpHost[0]));
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
//    }
//
//    static {
//        bulkProcessor=createBulkProcessor();
//    }
//
//    private static BulkProcessor createBulkProcessor() {
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
}
