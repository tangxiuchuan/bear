package org.bear.sso.controller;

import org.bear.exception.CustomException;
import org.bear.reuslt.Result;
import org.bear.reuslt.ResultUtils;
import org.bear.sso.bean.User;
import org.bear.sso.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@ResponseBody
@Controller
@RequestMapping("/sso")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger LOGGER=LoggerFactory.getLogger(UserController.class);


    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public Result  register(@Valid  User user, BindingResult bindingResult) throws CustomException {
        if(bindingResult.hasErrors()){
            //校验不通过
            List<ObjectError> errors = bindingResult.getAllErrors();
            List<String> list=new ArrayList<>();
            for (ObjectError error:errors) {
                //获取每一个错误信息
                String msg=error.getDefaultMessage();
                list.add(msg);
            }
            String message=org.apache.commons.lang3.StringUtils.join(list,",");
            LOGGER.error("校验失败，{}",message);
            return ResultUtils.buildFail(message,110);
        }
        //校验通过
        userService.register(user);
        LOGGER.info("{}:注册成功",user.getUsername());
        return ResultUtils.buildSuccess();
    }



    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public Result login(String username,String password) throws CustomException {
        if(StringUtils.isEmpty(username)){
            LOGGER.debug("用户名不能为空");
            return ResultUtils.buildFail("用户名不能为空",111);
        }
        if(StringUtils.isEmpty(password)){
            LOGGER.debug("密码不能为空");
            return ResultUtils.buildFail("密码不能为空",112);
        }
        LOGGER.debug("开始登录....");
        String token= userService.login(username,password);
        if(StringUtils.isEmpty(token)){
            LOGGER.error("用户{},登录失败!",username);
            return ResultUtils.buildFail("登陆失败",113);
        }
        return ResultUtils.buildSuccess(token);
    }

    @RequestMapping(value = "/user/login/phone",method = RequestMethod.POST)
    public Result loginByPhone(String phone,String code) throws CustomException {
        if(StringUtils.isEmpty(phone)){
            LOGGER.debug("手机号码不能为空");
            return ResultUtils.buildFail("手机号码不能为空",111);
        }
        if(StringUtils.isEmpty(code)){
            LOGGER.debug("验证码不能为空");
            return ResultUtils.buildFail("验证码不能为空",112);
        }
        LOGGER.debug("开始登录....");
        String token= userService.loginByPhone(phone,code);
        if(StringUtils.isEmpty(token)){
            LOGGER.error("手机号码{},登录失败!",phone);
            return ResultUtils.buildFail("登陆失败",113);
        }
        return ResultUtils.buildSuccess(token);
    }




    @RequestMapping(value = "/user/{token}",method = RequestMethod.GET)
    public Result queryUserByToken(@PathVariable("token") String token) throws CustomException {
        LOGGER.info("查询的token为{}",token);
        User user=userService.queryUserByToken(token);
        if(user==null){
            LOGGER.info("登录失效");
            return ResultUtils.buildFail("登录时效",114);
        }
        return ResultUtils.buildSuccess(user);
    }








}
