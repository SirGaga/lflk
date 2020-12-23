package com.asideal.lflk.test.generator;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class FoundationAla {
    public static void main(String[] args) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now();
        String currentDate = formatter.format(date);
        Path path = Paths.get("E://"+ currentDate +".txt");
        BufferedReader bfr= Files.newBufferedReader(path);
        String result = bfr.readLine();
        JSONObject object = JSONObject.parseObject(result);
        JSONArray array = JSONArray.parseArray(object.getString("datas"));
        List<String> list = array.stream().map(Object::toString).collect(Collectors.toList());
        LocalDate tjrq;
        if (date.getDayOfWeek().getValue() == 1) {
            tjrq = date.minus(3, ChronoUnit.DAYS);
        } else {
            tjrq = date.minus(1, ChronoUnit.DAYS);
        }
        String tjrqString = formatter.format(tjrq);
        list.forEach(e -> {
            String[] strings = e.split(",");
            if (ObjectUtil.isNotNull(strings[3]) && tjrqString.equals(strings[3])) {
                System.out.println(e);
            }
        });
    }
}
