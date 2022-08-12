package com.example.springeasyexcelinput.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springeasyexcelinput.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("UserMapper")
public class UserserviceImpl extends AnalysisEventListener<UserDto>{

//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private Uservice uservice;

    private static final int BATCH_COUNT = 2000;

    public UserserviceImpl(){

    }

    public UserserviceImpl(Uservice uservice){
        this.uservice=uservice;

    }

    @Override
    public void invoke(UserDto userDto, AnalysisContext analysisContext) {
        List<UserDto> userList=new ArrayList<>();
//        List<UserDto> userAllList=userMapper.selectList(null);
        userList.add(userDto);
//        userAllList.addAll(userList);
//       List<UserDto> userDtos= userAllList.stream().distinct().collect(Collectors.toList());
//        if (userList.size() <= BATCH_COUNT) {
        userList.forEach(userDto1 -> {
            uservice.insertDto(userDto1);
            });

//        }
//        userAllList=userMapper.selectList(null);
//        List<UserDto> userDtos=new ArrayList<>();
//        userDtos=userAllList.stream().distinct().collect(Collectors.toList());
//        userDtos.forEach(userDto1 -> {
//            userMapper.insert(userDto1);
//        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        List<UserDto> userList=new ArrayList<>();
        userList.forEach(userDto1 -> {
            uservice.insertDto(userDto1);
        });
    }

//    @Override
//    public void importExcel(MultipartFile file) throws IOException {
//        try {
//            EasyExcel.read(file.getInputStream(), UserDto.class,new UserserviceImpl(userMapper))
//                    .sheet().doRead();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
