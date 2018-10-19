package org.bear.sso.controller;

import org.bear.exception.CustomException;
import org.bear.reuslt.Result;
import org.bear.reuslt.ResultUtils;
import org.bear.sso.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@RequestMapping("/sms")
@Controller
public class SmsController {


    private Logger LOGGER=LoggerFactory.getLogger(SmsController.class);

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/login/{phone}",method = RequestMethod.GET)
    public Result sendLoginSms(@PathVariable("phone") String phone) throws CustomException {
        LOGGER.info("发送短信,电话号码为{}",phone);
        smsService.sendLoginSms(phone);
        return ResultUtils.buildSuccess();
    }



}
