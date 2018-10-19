package org.bear.test;

import org.bear.utils.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestSso {


    //功能测试
    @Test
    public void testRegister(){

        //准备测试数据
        Map<String,String> param=new HashMap<String,String>();

        param.put("username","xioangjian");
        param.put("password","123456");
        param.put("phone","18980861075");
        param.put("email","xxjk@qq.com");


        String result = HttpClientUtil.doPost("http://sso.xxkj.cn/sso/user/register", param);

        System.out.print(result+"---------------------");

    }

}
