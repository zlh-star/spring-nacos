package com.example.springpictures.mapper;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("counts")
public class Count {
    private String cout;

    private String fileName;

//    public String getCout() {
//        return cout;
//    }
//
//    public void setCout(String cout) {
//        this.cout = cout;
//    }
}
