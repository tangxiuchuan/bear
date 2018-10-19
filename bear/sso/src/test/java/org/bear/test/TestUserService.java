package org.bear.test;

import org.bear.exception.CustomException;
import org.bear.sso.bean.User;
import org.bear.sso.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestUserService {

    @Autowired
    private UserService userService;


    @Test
    public void testRegister(){

        User user=new User();
        user.setUsername("xj1");
        user.setPassword("123456");
        user.setPhone("180");
        user.setEmail("110@qq.com");

        try {
            userService.register(user);
        } catch (CustomException e) {
            e.printStackTrace();
            System.out.print(e.getMessage()+"------------------------");
        }


    }

}
