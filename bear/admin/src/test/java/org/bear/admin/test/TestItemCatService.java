package org.bear.admin.test;

import com.github.pagehelper.PageInfo;
import org.bear.admin.service.ItemCatService;
import org.bear.bean.ItemCat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:spring/applicationContext.xml"})
public class TestItemCatService {
    
    @Autowired
    private ItemCatService itemCatService;
    
    
    @Test
    public void queryById(){

        ItemCat itemCat = itemCatService.queryById(1L);

    }


    @Test
    public void queryPage(){

        PageInfo<ItemCat> pageInfo = itemCatService.queryPageListByWhere(null, 0, 10);

    }

    @Test
    public void delete(){

        List ids=new ArrayList();
        ids.add(2000L);
        ids.add(2001L);
        itemCatService.deleteByWhere("id",ids);



    }





    
}
