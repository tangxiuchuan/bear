package org.bear.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zxd on 2018/1/29.
 */
public class ResponseUtils {

    /**
     * 响应json串给调用方
     * @param response
     * @param json
     */
    public static void responseJson(HttpServletResponse response, String json) {
        response.setContentType("application/json;charset=utf-8");
        //response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
