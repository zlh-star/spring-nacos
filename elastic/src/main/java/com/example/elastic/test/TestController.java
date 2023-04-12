package com.example.elastic.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.MultiCollectorManager;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ml.job.results.Result;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.data.elasticsearch.core.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@Api(value = "查询日志测试")
@Slf4j
public class TestController {


    @Value("${indexName}")
    private String indexName;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

//    @Autowired
//    private  AuditService auditService;


//    public void deleteByQuery(TransportClient client) {
//        BulkByScrollResponse response = DeleteByQueryAction.INSTANCE
//                .newRequestBuilder(client)
//                .filter(QueryBuilders.matchQuery("gender", "male"))
//                .source("persons")
//                .get();
//        long deleted = response.getDeleted();
//    }


//    @ApiOperation(value = "根据菜单父ID获取直接下级", httpMethod = "POST")
//    @RequestMapping(value = "/getMenuChildrenTree/{id}", method = RequestMethod.POST)
//    public Object findRegionTreeByPid(@PathVariable("id") String id){
//        Map<String, List<MenuBo>> map = new HashMap<>();
//        try {
//            List<MenuBo> list = menuService.getNextChildMenu(id);
//            map.put(id, list);
//            //调用递归，查询所有下级
//            findRegionDigui(list, map);;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        List<List<MenuBo>> menuBolist = map.values().stream().collect(Collectors.toList());
//        return menuBolist;
//    }
//
//    public void findRegionDigui(List<MenuBo> regions, Map<String, List<MenuBo>> map) {
//        List<MenuBo> reList = new ArrayList<>();
//        for (MenuBo r : regions) {
//            //通过ID查询下一级
//            reList = menuService.getNextChildMenu(r.getMenuId());
//            if (reList.size() > 0) {//代表有下级
//                map.put(r.getMenuId(), reList);
//                findRegionDigui(reList, map); //循环调用自己
//            }
//        }
//    }

    @RequestMapping(value = "/deleteList",method = RequestMethod.POST)
    public Object deleteList(@RequestParam(value = "ids",required = false) List<String> ids) throws IOException {

        DeleteByQueryRequest deleteByQueryRequest=new DeleteByQueryRequest(indexName);
        if(ids!=null&&ids.size()>0) {
           ids.forEach(id->{
               BulkByScrollResponse deleteResponse = null;
               BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery();
               boolQueryBuilder.must(QueryBuilders.termQuery("id.keyword", id));
               deleteByQueryRequest.setQuery(boolQueryBuilder);
               try {
                   deleteResponse = restHighLevelClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           });
           return true;
        }
        return false;
    }

    //无需_id,使用关键字段进行单个删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object delete(@RequestBody DemoDto demoDto) throws IOException {

        DeleteByQueryRequest deleteByQueryRequest=new DeleteByQueryRequest(indexName);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery();
        if(demoDto.getId()!=null&& !"".equals(demoDto.getId())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("id.keyword", demoDto.getId()));
        }
//        searchSourceBuilder.query(boolQueryBuilder);
        deleteByQueryRequest.setQuery(boolQueryBuilder);
        try {
            BulkByScrollResponse deleteResponse=restHighLevelClient.deleteByQuery(deleteByQueryRequest , RequestOptions.DEFAULT);
            if(deleteResponse.getDeleted()>=1){
                log.info("删除成功");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @ApiModelProperty(value = "查询es中所有日志")
    @RequestMapping(value = "/selectAllAudit", method = RequestMethod.POST)
    public Object selectAllAudit() throws IOException {
//        Result result=new Result()
//        DemoDto demoDto=new DemoDto();
        List<SearchResponse> list=new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(indexName);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        list.add(searchResponse);
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
       SearchHit[] searchHits=searchResponse.getHits().getHits();
       for (SearchHit searchHit:searchHits){
           Map<String,Object> objectMap=searchHit.getSourceAsMap();
           mapList.add(objectMap);
       }
        JSONArray jsonArray=new JSONArray();
        jsonArray.addAll(mapList);
        List<DemoDto> lists=JSONObject.parseArray(jsonArray.toJSONString(),DemoDto.class).stream()
                .sorted(Comparator.comparingInt(DemoDto::getPageNo).reversed()).collect(Collectors.toList());
        return lists;

    }

    @ApiModelProperty(value = "查询es中的日志并分页")
    @RequestMapping(value = "/selectAudit", method = RequestMethod.POST)
    public Object selectAudit(@RequestBody DemoDto demoDto) throws IOException {
        int a=demoDto.getPageNo();
        int b=demoDto.getPageSize();
        List<SearchResponse> list=new ArrayList<>();
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(indexName);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery();
        if(demoDto.getId()!=null&& !"".equals(demoDto.getId())){
            boolQueryBuilder.must(QueryBuilders.termQuery("id.keyword",demoDto.getId()));

        }if(demoDto.getName()!=null&& !"".equals(demoDto.getName())){
            boolQueryBuilder.must(QueryBuilders.termQuery("name.keyword",demoDto.getName()));
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date.keyword")
                    .gte(demoDto.getLoginTime())
                    .lte(demoDto.getLogoffTime()));

        }if(demoDto.getLoginTime()!=null&& !"".equals(demoDto.getLoginTime()) &&
                demoDto.getLogoffTime()!=null&& !"".equals(demoDto.getLogoffTime())){
//            boolQueryBuilder.must(QueryBuilders.termQuery("id",demoDto.getId()))
//                    .must(QueryBuilders.termQuery("name",demoDto.getName()));
            //按照时间段来查询数据(es)
            boolQueryBuilder.must(QueryBuilders.rangeQuery("date.keyword").gte(demoDto.getLoginTime()).lte(demoDto.getLogoffTime()));
            //根据精确条件来查询时间数据(模糊查询)
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("loginTime.keyword","*"+demoDto.getLoginTime()+"*"))
                    .must(QueryBuilders.wildcardQuery("logoffTime.keyword","*"+demoDto.getLogoffTime()+"*"));
        }

//        boolQueryBuilder.minimumShouldMatch(1)
//        searchSourceBuilder.query(QueryBuilders.termQuery("name",demoDto.getName()));
        searchSourceBuilder.query(boolQueryBuilder);
        //按照日期降序排列
//        searchSourceBuilder.sort("date.keyword", SortOrder.DESC);
        searchSourceBuilder.from((a-1)*b);
        searchSourceBuilder.size(b);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        long totalCount=searchResponse.getHits().getTotalHits().value;
        int total=Integer.parseInt(String.valueOf(totalCount));
        SearchHit[] searchHits=searchResponse.getHits().getHits();

        for(SearchHit hit:searchHits){
            Map<String,Object> objectMap=hit.getSourceAsMap();
            List<String> stringList=new ArrayList<>();
            String Ids=hit.getId();
            stringList.add(Ids);
//            Map<String,Object> stringMap=stringList.stream().collect(Collectors.toMap(Function.identity(),s->new ArrayList<>()));
            objectMap.put("ids",stringList);
            mapList.add(objectMap);
        }
        //可将数据降序排列
//        JSONArray jsonArray=new JSONArray();
//        jsonArray.addAll(mapList);
//        List<DemoDto> list1=JSONObject.parseArray(jsonArray.toJSONString(),DemoDto.class).stream()
//                .sorted(Comparator.comparing(DemoDto::getDate)).collect(Collectors.toList());
                //jsonArray.toList(DemoDto.class)
                //.stream()
                //.sorted(Comparator.comparing(DemoDto::getLoginTime).reversed()).collect(Collectors.toList());

//        list.add(searchResponse);
        return Results.wrapResult(mapList,total);
//        List<SearchResponse> pageList = new ArrayList<>();
//        List<UserModel> list=userDao.getAllAccount();
//        int curIdx = demoDto.getPageNo() > 1 ? (demoDto.getPageNo() - 1) * demoDto.getPageSize() : 0;
//        for (int i = 0; i <demoDto.getPageSize() && curIdx + i < list.size(); i++) {
//            pageList.add(list.get(curIdx + i));
//        }
//        IPage page = new Page<>(demoDto.getPageNo(), demoDto.getPageSize());
//        page.setRecords(pageList);
//        page.setTotal(list.size());
//        return page;
    }

    @ApiModelProperty(value = "查询es中所有日志并分页")
    @RequestMapping(value = "/selecAlltAudit", method = RequestMethod.POST)
    public Object selecAlltAudit(@RequestBody DemoDto demoDto) throws IOException {
        int a=demoDto.getPageNo();
        int b=demoDto.getPageSize();
        List<SearchResponse> list=new ArrayList<>();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest(indexName);
        BoolQueryBuilder boolQueryBuilder= QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery());
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from((a-1)*b);
        searchSourceBuilder.size(b);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        list.add(searchResponse);
        return list;

    }


//    @ApiModelProperty(value = "查询es中的日志并分页")
//    @RequestMapping(value = "/matchAll",method = RequestMethod.POST)
//    public List<DemoDto> matchAll (@RequestBody PageBo pageBo){
//        SearchQuery searchQuery=new NativeSearchQuery(QueryBuilders.matchAllQuery());
//        List<DemoDto> list=elasticsearchTemplate.queryForList(searchQuery,DemoDto.class);
////        SearchRequest searchRequest=new SearchRequest();
////        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
////        List<DemoDto> list=new ArrayList<>();
////        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
////        list.addAll((Collection<? extends DemoDto>) matchAllQueryBuilder);
////        searchRequest.source(searchSourceBuilder);
////        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
////        SearchHits hits = search.getHits();
//        int count=0;
//        int pageno=Integer.parseInt(pageBo.getPageNo());
//        int pagesize=Integer.parseInt(pageBo.getPageSize());
//        if(list!=null&&list.size()>0){
//            count=list.size();
//            int pageIndex=pageno*pagesize;
//            int toIndex=(pageno+1)*pagesize;
//            if(toIndex>count){
//                toIndex=count;
//            }
//            List<DemoDto> pageList=list.subList(pageIndex,toIndex);
//            return pageList;
//        }
//        return list;
//    }

//    @ApiModelProperty(value = "多条件查询es中的日志并分页")
//    @RequestMapping(value = "/selectLog",method = RequestMethod.POST)
//    public List<DemoDto> selectLog(@RequestBody DemoDto demoDto){
//        NativeSearchQueryBuilder query = new NativeSearchQueryBuilder();
//        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.matchQuery("id",demoDto.getId()));
//        boolQueryBuilder.must(QueryBuilders.matchQuery("name",demoDto.getName() ));
//
//        query.withQuery(boolQueryBuilder);
//        String[] include={"id","name"};
//        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(include, null);
//        SearchQuery searchQuery=new NativeSearchQueryBuilder()
//                .withSourceFilter(fetchSourceFilter)
//                .build();
//        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
//        List<QueryBuilder> queryBuilders=new ArrayList<>();
////        queryBuilders.add(QueryBuilders.matchPhraseQuery(demoDto.getName(),""));
//        queryBuilders.add(QueryBuilders
//                .rangeQuery(demoDto.getName())
//                .gte(demoDto.getId())
//                .lte(demoDto.getDate()));
//          boolQueryBuilder.must().addAll(queryBuilders);
//          SearchHits<DemoDto> list=elasticsearchRestTemplate.search(query.build(),DemoDto.class);
//          List<DemoDto> list1=new ArrayList<>();
//          for (SearchHit<DemoDto> searchHits : list){
//              list1.add(searchHits.getContent());
//        }
//        int count=0;
//        int pageno=0;
////                Integer.parseInt(pageBo.getPageNo());
//        int pagesize=2;
////                Integer.parseInt(pageBo.getPageSize());
//        if(list1!=null&&list1.size()>0){
//            count=list1.size();
//            int pageIndex=pageno*pagesize;
//            int toIndex=(pageno+1)*pagesize;
//            if(toIndex>count){
//                toIndex=count;
//            }
//            List<DemoDto> pageList=list1.subList(pageIndex,toIndex);
//            return pageList;
//        }
//        return list1;
//    }
}

