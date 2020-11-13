package com.asideal.lflk.utils.response;

import com.alibaba.fastjson.JSON;
import com.asideal.lflk.response.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    public static void responseResult(HttpServletResponse response, Result result) throws IOException {
        response.setCharacterEncoding("utf-8");    //设置 HttpServletResponse使用utf-8编码
        response.setHeader("Content-Type", "text/html;charset=utf-8");  //设置响应头的编码
        response.getWriter().write(JSON.toJSONString(result));
    }
}
