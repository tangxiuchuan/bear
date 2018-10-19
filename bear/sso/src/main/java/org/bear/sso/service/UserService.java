package org.bear.sso.service;

import org.bear.exception.CustomException;
import org.bear.sso.bean.User;

public interface UserService {

    /**
     * 注册
     * @param user
     */
    public void register(User user) throws CustomException;


    /**
     * 用户密码登录
     * @param username
     * @param password
     * @return
     */
    public String login(String username,String password) throws CustomException;

    /**
     *
     * @param phone
     * @param code
     * @return
     */
    public String loginByPhone(String phone,String code) throws CustomException;

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    public User queryUserByToken(String token);


}
