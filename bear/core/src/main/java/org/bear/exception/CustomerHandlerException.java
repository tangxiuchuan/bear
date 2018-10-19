package org.bear.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bear.reuslt.Result;
import org.bear.reuslt.ResultUtils;
import org.bear.utils.JsonUtils;
import org.bear.utils.ResponseUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class CustomerHandlerException implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        String msg;
        //如果是自定义异常取出异常信息
        if(exception instanceof CustomException){
            msg=exception.getMessage();
        }else{
            exception.printStackTrace();;
            msg="对不起，系统开小差了!";
        }
        ModelAndView modelAndView=new ModelAndView();
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Class beanClazz=handlerMethod.getBeanType();

        //1.先判断controller方法上面有没有responseBody注解,如果有则所有的方法都返回json数据
        ResponseBody responseBody = (ResponseBody) beanClazz.getAnnotation(ResponseBody.class);
        if(responseBody==null){

            //2.如果没有，继续判断方法，如果方法上面有responseBody注解或者方法的返回值类型是responseEntity返回json数据
            //获取处理器的方法
            Method method = handlerMethod.getMethod();
            responseBody= AnnotationUtils.findAnnotation(method,ResponseBody.class);
            if(responseBody==null && !method.getReturnType().getName().equals(ResponseEntity.class.getName())){
                //3.上面都不满足转发到jsp
                modelAndView.setViewName("error");
                modelAndView.addObject("msg",msg);
                return modelAndView;
            }

        }
        //需要返回json数据
        Result result=ResultUtils.buildFail(msg,500);
        ResponseUtils.responseJson(response,JsonUtils.objectToJson(result));
        return modelAndView;
    }
}
