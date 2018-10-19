package org.bear.admin.service.impl;

import org.bear.admin.service.ItemDescService;
import org.bear.admin.service.ItemService;
import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
import org.bear.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {

    private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImpl.class);


    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ItemDescService itemDescService;

    public void addItem(Item item, String desc) {

        if(item==null||StringUtils.isEmpty(desc)){
            return;
        }

        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        this.save(item);

        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getCreated());
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);
    }



    @Override
    public void updateItem(Item item, String desc) {



        if(item==null||StringUtils.isEmpty(desc)){
            return;
        }
        //设置修改时间
        item.setUpdated(new Date());
        this.update(item);

        //修改商品描述
        ItemDesc itemDesc=new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescService.update(itemDesc);

        //通知前台系统干掉缓存

        LOGGER.debug("通知前台系统干掉用缓存,商品id为{}",item.getId());
        //HttpClientUtil.doGet("http://www.xxkj.cn/item/cache/delete/"+item.getId());

        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
              return session.createTextMessage(item.getId()+"");
            }
        });

    }



}
