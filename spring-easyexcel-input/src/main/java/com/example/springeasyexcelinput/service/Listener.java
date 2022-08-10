package com.example.springeasyexcelinput.service;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.example.springeasyexcelinput.dto.UserDto;

public class Listener implements ReadListener<UserDto> {
    @Override
    public void invoke(UserDto userDto, AnalysisContext analysisContext) {

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
