package com.example.elastic.test;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Api(value = "测试")
@RestController
public class AuditController {

    @Autowired
    private  AuditService auditService;

    @Value("${indexName}")
    private String indexName;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @ApiOperation(value = "合并链表",notes = "合并链表")
    @RequestMapping(value = "/lianbiao",method = RequestMethod.POST)
    public Object lianbiao (){
        ListNode l1 = new ListNode(11);
        ListNode listNode2 = new ListNode(22);
        ListNode listNode3 = new ListNode(33);
        ListNode listNode4 = new ListNode(44);
        ListNode listNode5 = new ListNode(55);

        l1.next = listNode2;
        listNode2.next = listNode3;
        listNode3.next = listNode4;
        listNode4.next = listNode5;

        ListNode l2 = new ListNode(10);
        ListNode listNode21 = new ListNode(23);
        ListNode listNode31 = new ListNode(32);
        ListNode listNode41 = new ListNode(45);
        ListNode listNode51 = new ListNode(51);

        l2.next = listNode21;
        listNode21.next = listNode31;
        listNode31.next = listNode41;
        listNode41.next = listNode51;
//        ListNode l1=new ListNode();
//        l1.first(1);
//        l1.first(5);
//        ListNode l2=new ListNode();
//        l2.first(3);
//        l2.first(4);
        ListNode prehead=new ListNode(-1);
        ListNode prev=prehead;
//        ListNode l1=new ListNode(123456789);
//        ListNode l2=new ListNode(98765432);
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }
        prev.next = l1 == null ? l2 : l1;
        return prehead.next;
    }


    @ApiOperation(value = "两数之和",notes = "两数之和")
    @RequestMapping(value = "/suanfa",method = RequestMethod.POST)
    public int[] suanfa(){
        int[] nums={2,7,11,15};
        int target=18;
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> map1 = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            map.put(i,nums[i]);
        }
        map.forEach((k,v)->{
            map1.put(v,k);
        });

        for (int num : nums) {
            int complement = target - num;
            if (map.containsValue(complement)) {
                return new int[]{map1.get(complement), complement};
            }
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    public int[] twoSum_3(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int complement=0;
        for (int i = 0; i < nums.length; i++) {
            complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");
    }

    @ApiModelProperty(value = "测试")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public void insert(){
        List<DemoDto> list=new ArrayList<>();
        DemoDto demoDto=new DemoDto();
        demoDto.setId("10");
        demoDto.setName("zhao0");
        demoDto.setName1("zlh0");
        demoDto.setPageNo(1000);
        demoDto.setDate(new Date());
        list.add(demoDto);
        auditService.insertLog(list);
    }

    @ApiModelProperty(value = "测试单条删除")
    @RequestMapping(value = "/deleteOneLog",method = RequestMethod.POST)
    public void deleteOneLog(@RequestParam("id") String id){
        try {
            auditService.deleteLog(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiModelProperty(value = "测试批量删除")
    @RequestMapping(value = "/deleteListLog",method = RequestMethod.POST)
    public Object deleteListLog(@RequestParam(value = "Idlist",required = false) List<String> Idlist){
//        List<String> list=new ArrayList<>();
        try {
            auditService.deleteLogById(Idlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "成功";
    }

//    @ApiModelProperty(value = "测试批量更新")
//    @RequestMapping(value = "/updateListLog",method = RequestMethod.POST)
//    public Object updateListLog(@RequestBody List<DemoDto> dtoList,@RequestParam("id")String id) {
//        try {
//            if (!StringUtils.isEmpty(dtoList)) {
//                auditService.updateLog(dtoList,id);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//        dtoList.forEach(e->{
//            auditService.updateLog();
//        });
//        List<SearchResponse> responseList=new ArrayList<>();
//        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
//        SearchRequest searchRequest=new SearchRequest();
//        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
//        if(dtoList!=null&&dtoList.size()>0){
//            dtoList.forEach(demoDto->{
//                boolQueryBuilder.must(QueryBuilders.termQuery("id.keyword",demoDto.getId()));
//                sourceBuilder.query(boolQueryBuilder);
//                searchRequest.source(sourceBuilder);
//                try {
//                    SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//                    responseList.add(searchResponse);
//                    System.out.println(responseList);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//            auditService.updateLog(responseList);
//        }




    @ApiModelProperty(value = "测试删除")
    @RequestMapping(value = "/deleteLog",method = RequestMethod.POST)
    public Object deleteLog(@RequestParam(value = "Idlist",required = false) List<String> Idlist){
        try {
            Idlist.forEach(id->{
                DeleteRequest deleteRequest=new DeleteRequest(indexName,id);
                try {
                    restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return 1;
//        DemoDto demoDto=new DemoDto();
//        demoDto.setId("aY-y-4EBjSZXDrn8_tjn");
//        list.add(demoDto);
//        auditService.deleteLog(demoDto.getId());
return 0;
    }
}
