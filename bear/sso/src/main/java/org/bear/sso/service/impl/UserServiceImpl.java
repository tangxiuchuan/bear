package org.bear.sso.service.impl;

import org.bear.common.JedisService;
import org.bear.exception.CustomException;
import org.bear.sso.bean.User;
import org.bear.sso.repository.UserRepository;
import org.bear.sso.service.UserService;
import org.bear.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;


@Transactional(propagation = Propagation.REQUIRED,readOnly = false)
@Service
public class UserServiceImpl implements UserService {

    private static final String SALT="xjzll";

    private static final String USER_LOGIN="user_login:";

    private Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JedisService jedisService;

    @Override
    public void register(User user) throws CustomException {
        if(user==null){
            LOGGER.error("用户信息不能为空");
            return ;
        }
        //1.参数校验
       checkout(user);
        //2..用户名和电话号码不能重复
        if(userRepository.queryUserByUsernameEquals(user.getUsername())!=null){
            LOGGER.info("{}:用户名已经注册!",user.getUsername());
            throw  new CustomException(user.getUsername()+":用户名已经注册!");
        }
        if(userRepository.queryUserByPhone(user.getPhone())!=null){
            LOGGER.info("{}:电话号码已经注册!",user.getPhone());
            throw  new CustomException(user.getPhone()+":电话号码已经注册!");
        }
        //3.密码加密
        user.setPassword(DigestUtils.md5DigestAsHex((SALT+user.getPassword()).getBytes()));
        //4.设置时间
        user.setUpdated(new Date());
        user.setCreated(user.getUpdated());
        //5.保存到数据库
        userRepository.save(user);
    }

    @Override
    public String login(String username, String password) throws CustomException {
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            LOGGER.info("参数为空!");
            throw  new CustomException("参数为空!");
        }
        //密码加密
        String md5Password=DigestUtils.md5DigestAsHex((SALT+password).getBytes());
        User user=userRepository.queryUserByUsernameAndPassword(username,md5Password);
        if(user==null){
            //用户名密码错误
            LOGGER.info("{}:用户，用户名密码错误",username);
            throw  new CustomException("用户名密码错误!");
        }
        //处理登录
        String token=getToken();
        //把用户信息设置到redis服务器
        jedisService.set(USER_LOGIN+token,JsonUtils.objectToJson(user));
        LOGGER.info("用户{},token为{}",username,USER_LOGIN+token);
        //设置失效时间
        jedisService.expire(USER_LOGIN+token,60*60*2);
        return token;
    }


    @Override
    public User queryUserByToken(String token) {
        if(StringUtils.isEmpty(token)){
            LOGGER.info("token不能为空");
            return null;
        }
        LOGGER.info("查询用户信息的token为:{}",USER_LOGIN+token);
        String result=jedisService.get(USER_LOGIN+token);
        if(StringUtils.isEmpty(result)){
            LOGGER.info("登录失效!{}",USER_LOGIN+token);
            return null;
        }
        User user=JsonUtils.jsonToPojo(result,User.class);
        LOGGER.info("查询用户信息成功!,用户名为{}",user.getUsername());
        //更新过期时间
        jedisService.expire(USER_LOGIN+token,60*60*2);
        return user;
    }

    @Override
    public String loginByPhone(String phone, String code) throws CustomException {
        if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)){
            LOGGER.info("参数不能为空!");
            throw  new CustomException("参数不能为空!");
        }
        User user=userRepository.queryUserByPhone(phone);
        if(user==null){
            LOGGER.info("手机号码未注册!{}",phone);
            throw  new CustomException("手机号码未注册!");
        }

        //1.从服务器取出服务器存储的code
        String serverCode=jedisService.get(SmsServiceImpl.SMS_LOGIN+phone);
        if(StringUtils.isEmpty(serverCode)){
            LOGGER.info("验证码失效!,{}",phone);
            throw  new CustomException("验证码失效!");
        }
        //2.校验验证码是否一致
        if(!code.equals(serverCode)){
            LOGGER.info("验证码输入错误,手机号码{}",phone);
            throw  new CustomException("验证码输入错误!");
        }

        //3.处理登录
        //处理登录
        String token=getToken();
        //把用户信息设置到redis服务器
        jedisService.set(USER_LOGIN+token,JsonUtils.objectToJson(user));
        LOGGER.info("手机号码{},token为{}",phone,USER_LOGIN+token);
        //设置失效时间
        jedisService.expire(USER_LOGIN+token,60*60*2);
        return token;
    }


    /**
     * 生成token
     * @return
     */
    private String getToken() {
        long times=new Date().getTime();
        return DigestUtils.md5DigestAsHex((times+SALT).getBytes());
    }

    private void checkout(User user) throws CustomException {
        if(StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())//
                ||StringUtils.isEmpty(user.getPhone())||StringUtils.isEmpty(user.getEmail())){
               LOGGER.error("注册参数不能为空!");
               throw  new CustomException("注册参数不能为空!");
        }
    }
}
