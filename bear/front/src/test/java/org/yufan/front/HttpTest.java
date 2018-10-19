package org.yufan.front;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpTest {

   private Logger LOGGER=LoggerFactory.getLogger(HttpTest.class);



   //模拟不带参数的get请求
    @Test
    public void testGet() throws IOException {
        //1.创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //2.模拟get请求   相当于在浏览器输入地址
        HttpGet   httpGet=new HttpGet("http://manage.xxkj.cn/rest/item/list");
        //3.发送get请求  获得服务器的响应
        CloseableHttpResponse  response= httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功获取响应体
            HttpEntity entity = response.getEntity();

            String s=EntityUtils.toString(entity);

            LOGGER.debug("-----------------{}",s);
        }
        response.close();
        httpClient.close();
    }


    //模拟带参数的get请求
    @Test
    public void testGet2() throws IOException, URISyntaxException {
        //1.创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URI uri= new URIBuilder("http://manage.xxkj.cn/rest/item/list").
                setParameter("page", "1").setParameter("rows", "1").build();
        //2.模拟get请求   相当于在浏览器输入地址
        HttpGet   httpGet=new HttpGet(uri);
        //3.发送get请求  获得服务器的响应
        CloseableHttpResponse  response= httpClient.execute(httpGet);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功获取响应体
            HttpEntity entity = response.getEntity();
            String s=EntityUtils.toString(entity);

            LOGGER.debug("-----------------{}",s);
        }
        response.close();
        httpClient.close();
    }


    @Test
    public void testPost() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost=new HttpPost("http://manage.xxkj.cn/rest/item/list");
        //设置请求参数
      List<NameValuePair> list=new ArrayList<NameValuePair>();
      list.add(new BasicNameValuePair("page","1"));
      list.add(new BasicNameValuePair("rows","1"));
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);

      //把模拟的表单设置到post请求
      httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if(response.getStatusLine().getStatusCode()==200){
            //请求成功获取响应体
            HttpEntity e = response.getEntity();
            String s=EntityUtils.toString(e);
            LOGGER.debug("-----------------{}",s);
        }
        response.close();
        httpClient.close();
    }




}
