package com.example.springboottest.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboottest.dao.StudentDao;
import com.example.springboottest.dao.UserDao;
import com.example.springboottest.dao.UserServiceDao;
import com.example.springboottest.model.Student;
import com.example.springboottest.model.StudentCondition;
import com.example.springboottest.model.UserCondition;
import com.example.springboottest.model.UserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "用户演示类")
@RestController
public class AccountController {

//    @Autowired
//    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private UserServiceDao userServiceDao;

    public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @ApiModelProperty(value = "获取所有的用户信息")
    @ApiOperation(value = "获取所有账号")
    @GetMapping("/getAllAccount")
    public Object getAllAccount(){
        Long count = null;
        List<UserModel> list=new ArrayList<>();
        list= userDao.getAllAccount();
        if(list!=null&&list.size()>0){
            count=list.stream().filter(p->p.getPassword()!="123456").count();
            return count;
        }
        System.out.println(count);
        return list;
    }

    @ApiModelProperty(value = "查询账号",notes = "根据账号id查询账号信息")
    @ApiOperation(value = "查询账号",notes = "根据账号id查询账号信息")
    @GetMapping("/selectAccount")
    public Object selesctAccount(@RequestParam("accountId") String accountId){
        if(StringUtils.isEmpty(accountId)){
            return Collections.EMPTY_LIST;
        }
        List<UserModel> accountList=userDao.selectAccount(accountId);
        return accountList;
    }

    @ApiModelProperty(value = "插入账号",notes = "对账号信息进行完整插入")
    @ApiOperation(value = "插入账号",notes = "对账号信息进行完整插入")
    @PostMapping("/insertAccount")
    public int insertAccount(@RequestBody UserModel userModel){
        List<UserModel> userModelList=new ArrayList<>();
        UserModel userModel1=new UserModel();
        userModel1.setAccountId(userModel.getAccountId());
        userModel1.setAccountName(userModel.getAccountName());
        userModel1.setPassword(userModel.getPassword());
        userModel1.setCreateData(new Date());
        userModelList.add(userModel1);
        List<UserModel> userAllAccountlist=userDao.getAllAccount();
        if(userAllAccountlist!=null && userAllAccountlist.size()>0) {
                //去重操作
            List<UserModel> notexitlist = userModelList.stream().filter(name ->
                    (!userAllAccountlist.contains(name))).collect(Collectors.toList());
            if (notexitlist.size() == 0) {
                return 0;
            } else {
                int userModelList1 = userDao.insertAccount(notexitlist);
                if (StringUtils.isEmpty(userModelList1)) {
                    return 0;
                }
                return userModelList1;
            }
        }
        return 0;
    }

    @ApiModelProperty(value = "插入账户数据",notes = "使用Mono")
    @ApiOperation(value = "插入账户数据",notes = "使用Mono")
    @PostMapping("/insertAccounts")
    public Mono<UserModel> insertAccounts(@RequestBody UserModel userModel) throws ParseException {

        List<UserModel> userModels=new ArrayList<>();
        UserModel userModel1=new UserModel();
        userModel1.setAccountId(userModel.getAccountId());
        userModel1.setAccountName(userModel.getAccountName());
        userModel1.setPassword(userModel.getPassword());
        String accountCreateTime=(new SimpleDateFormat("yy-MM-dd HH-mm-ss")).format(new Date());
        Date date=sdf.parse(accountCreateTime);
        userModel1.setCreateData(date);
        userModels.add(userModel1);
        try {
            if(userModels.size() > 0){
                return userServiceDao.save(userModel1)
                        .onErrorResume(e ->userServiceDao.findByAccountName(userModel.getAccountId())
                                .flatMap(originalUser ->{
                                    userModel.setAccountId(originalUser.getAccountId());
                                    return userServiceDao.save(userModel1);
                                }));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiModelProperty(value = "更新账号",notes = "更新账户信息")
    @ApiOperation(value = "更新账号",notes = "更新账户信息")
    @PostMapping("/updateAccount")
    public int updateAccount(@RequestBody UserModel userModel){
        List<UserModel> userModelList=new ArrayList<>();
        try {
            if(!StringUtils.isEmpty(userModel.getAccountId())) {
                List<UserModel> modelList = userDao.selectAccount(userModel.getAccountId());
                if (modelList != null && modelList.size() > 0) {
                    UserModel userModel1 = new UserModel();
                    userModel1.setAccountId(userModel.getAccountId());
                    userModel1.setAccountName(userModel.getAccountName());
                    userModel1.setPassword(userModel.getPassword());
                    userModel1.setCreateData(new Date());
                    userModelList.add(userModel1);
                    int userModelList1 = userDao.updateAccount(userModelList);
                    return userModelList1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //测试使用
    @ApiModelProperty(value = "测试",notes = "更新账户信息")
    @ApiOperation(value = "测试",notes = "更新账户信息")
    @PostMapping("/update")
    public int update(UserModel userModel){
        int userlist=userDao.update(userModel);
        return userlist;
    }

    @ApiModelProperty(value = "测试",notes = "分页")
    @ApiOperation(value = "测试",notes = "分页")
    @PostMapping("/selectAllAccount")
    public List<UserModel> selectAllAccount(@RequestBody UserCondition userCondition){
         List<UserModel> userModels=userDao.getAllAccount();
        int count=0;
        int pageno=Integer.parseInt(userCondition.getPageNo());
        int pagesize=Integer.parseInt(userCondition.getPageSize());
        if(userModels!=null&&userModels.size()>0){
            count=userModels.size();
            int pageIndex=pageno*pagesize;
            int toIndex=(pageno+1)*pagesize;
            if(toIndex>count){
                toIndex=count;
            }
            return userModels.subList(pageIndex,toIndex);
        }
        return userModels;
    }

    @ApiModelProperty(value = "删除账号信息",notes = "根据账号ID删除账号信息")
    @ApiOperation(value = "删除账号信息",notes = "根据账号ID删除账号信息")
    @DeleteMapping("/deleteAccount")
    public int deleteAccount(@RequestParam("accountId") String accountId){
        try {
            List<String> list=new ArrayList<>(Collections.singletonList(accountId));
            List<UserModel> list1=new ArrayList<>();
            if(list.size() > 0){
                list.forEach(id->{
                    List<UserModel> userModelList=userDao.selectAccount(id);
                    list1.addAll(userModelList);
                    if(list1.size() > 0){
                        int userModels=userDao.deleteAccount(accountId,list1);
//                        return userModels;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @ApiModelProperty(value = "分页",notes = "分页插件")
    @ApiOperation(value = "分页",notes = "mybatisPlus分页插件")
    @GetMapping("/findPage")
    public Object findPage(UserCondition userCondition) {
        Page<UserModel> page = new Page<>(userCondition.getPageBegin(),userCondition.getPageEnd());
        IPage<UserModel> modelPage=userDao.selectPage(page, null);
        return modelPage;
    }

    @ApiModelProperty(value = "分",notes = "分页插件")
    @ApiOperation(value = "分",notes = "mybatisPlus分页插件")
    @GetMapping("/find")
    public Object find(StudentCondition condition){
        Page<Student> studentPage = new Page<Student>(condition.getPageNo(), condition.getPageSize());
        IPage<Student> page=studentDao.selectPage(studentPage,null);
        return page;
    }

    @ApiModelProperty(value = "分页数",notes = "分页插件")
    @ApiOperation(value = "分",notes = "mybatisPlus")
    @GetMapping("/findye")
    public Object listToPage(int pageNum, int pageSize){
        List<UserModel> pageList = new ArrayList<>();
        List<UserModel> list=userDao.getAllAccount();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        IPage page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }
}
