package com.asideal.lflk.test.generator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class FoundationAla {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("E://2020-12-22.txt");
        BufferedReader bfr= Files.newBufferedReader(path);
        String result = bfr.readLine();
        JSONObject object = JSONObject.parseObject(result);
        JSONArray array = JSONArray.parseArray(object.getString("datas"));
        List<String> list = array.stream().map(Object::toString).collect(Collectors.toList());
        list.forEach(System.out::println);
    }
}
