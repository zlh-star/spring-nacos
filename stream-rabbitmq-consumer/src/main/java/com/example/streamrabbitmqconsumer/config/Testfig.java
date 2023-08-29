package com.example.streamrabbitmqconsumer.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class Testfig {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Bean
    public BulkProcessor bulkProcessor(){
        String name="";

        BulkProcessor.Listener listener=new BulkProcessor.Listener() {
            @Override
            public void beforeBulk(long l, BulkRequest bulkRequest) {
                log.info("1. 【beforeBulk】批次[{}] 携带 {} 请求数量", l, bulkRequest.numberOfActions());
            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, BulkResponse bulkResponse) {
                if (!bulkResponse.hasFailures()) {
                    log.info("2. 【afterBulk-成功】批量 [{}] 完成在 {} ms", l, bulkResponse.getTook().getMillis());
                } else {
                    BulkItemResponse[] items = bulkResponse.getItems();
                    for (BulkItemResponse item : items) {
                        if (item.isFailed()) {
                            log.info("2. 【afterBulk-失败】批量 [{}] 出现异常的原因 : {}", l, item.getFailureMessage());
                            break;
                        }
                    }
                }

            }

            @Override
            public void afterBulk(long l, BulkRequest bulkRequest, Throwable throwable) {
                List<DocWriteRequest<?>> requests = bulkRequest.requests();
                List<String> esIds = requests.stream().map(DocWriteRequest::id).collect(Collectors.toList());
                log.error("3. 【afterBulk-failure失败】es执行bluk失败,失败的esId为：{}", esIds, throwable);
            }
        };

        return BulkProcessor.builder(((bulkRequest, bulkResponseActionListener) -> {
            restHighLevelClient.bulkAsync(bulkRequest, RequestOptions.DEFAULT, bulkResponseActionListener);
        }), listener, name)
                //达到一万条进行刷新
                .setBulkActions(10000)
                //达到8M进行刷新
                .setBulkSize(new ByteSizeValue(8L, ByteSizeUnit.MB))
                //刷新时间间隔10s
                .setFlushInterval(TimeValue.timeValueSeconds(10))
                //允许的并发请求数
                .setConcurrentRequests(8)
                //设置重试策略
                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1), 3))
                .build();
    }
}
