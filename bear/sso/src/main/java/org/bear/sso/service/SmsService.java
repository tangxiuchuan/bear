package org.bear.sso.service;

import org.bear.exception.CustomException;

public interface SmsService {

    public void sendLoginSms(String phone) throws CustomException;

    public void sendRegisterSms(String phone);

}
