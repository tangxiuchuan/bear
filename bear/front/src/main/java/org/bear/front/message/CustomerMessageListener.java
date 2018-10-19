package org.bear.front.message;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.bear.front.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * 编写监听的代码
 */
public class CustomerMessageListener implements MessageListener {

    private Logger LOGGER=LoggerFactory.getLogger(CustomerMessageListener.class);

    @Autowired
    private ItemService itemService;

    @Override
    public void onMessage(Message message) {

        try {
       ActiveMQTextMessage activeMQTextMessage= (ActiveMQTextMessage) message;
            String itemId=activeMQTextMessage.getText();
            LOGGER.info("接收到后台系统消息，删除缓存....,商品的id为{}",itemId);
            itemService.deleteItemCache(Long.valueOf(itemId));
        } catch (JMSException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }


    }



}
