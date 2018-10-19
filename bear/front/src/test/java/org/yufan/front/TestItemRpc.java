package org.yufan.front;

import org.bear.admin.service.rpc.ItemRpcService;
import org.bear.bean.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class TestItemRpc {


    private Logger LOGGER=LoggerFactory.getLogger(TestItemRpc.class);

    @Autowired
    private ItemRpcService itemRpcService;




    @Test
    public void fun1(){

        Item item = itemRpcService.queryItemById(41L);



    }


}
