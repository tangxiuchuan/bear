package org.bear.front.service.impl;

import org.bear.admin.service.rpc.ItemRpcService;
import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
import org.bear.common.JedisService;
import org.bear.front.service.ItemService;
import org.bear.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private JedisService jedisService;

     private Logger LOGGER=LoggerFactory.getLogger(ItemServiceImpl.class);

    public static final String ITEM_URL="http://manage.xxkj.cn/rest/rpc/item/";

    public static final String ITEM_DESC_URL="http://manage.xxkj.cn/rest/rpc/item/desc/";

    public static final String ITEM_CACHE="item_cache";

    @Autowired
   private ItemRpcService itemRpcService;

    /*
    @Override
    public Item queryItemById(Long itemId) {

        if(StringUtils.isEmpty(itemId)){
            LOGGER.info("传入的商品id不能为空!");
            return null;
        }
        LOGGER.debug("查询的商品id为{}",itemId);
        String json = HttpClientUtil.doGet(ITEM_URL + itemId);

        Result result = JsonUtils.jsonToPojo(json, Result.class);
        if(result==null){
            LOGGER.error("后台服务器返回为null!");
            return null;
        }
        if(result.getStatus().equals("success")){
            LOGGER.debug("请求成功!");
            Item item= MyBeanUtils.copyBean(Item.class,result.getData());
            return item;
        }
        LOGGER.error("后台服务器发生错误，错误原因为{}",result.getMessage());
        return null;
    }
    */
    @Override
    public Item queryItemById(Long itemId) {
        if(StringUtils.isEmpty(itemId)){
            LOGGER.info("传入的商品id不能为空!");
            return null;
        }
        LOGGER.debug("查询的商品id为{}",itemId);
        String result=jedisService.hget(ITEM_CACHE,itemId+"");
        if(!StringUtils.isEmpty(result)){
            //1.先从缓存中去找，如果缓存中有直接返回
            LOGGER.debug("缓存中有，直接返回数据{}",result);
            return JsonUtils.jsonToPojo(result,Item.class);
        }
        //2.如果缓存中没有，调用后台系统服务进行查询，放入缓存中
        LOGGER.debug("缓存中没有找到数据，从后台系统调用!,商品id{}",itemId);
        Item item = itemRpcService.queryItemById(itemId);
        if(item==null){
            LOGGER.error("后台系统没有找到对应的商品，返回null,商品id{}",itemId);
            return null;
        }
        LOGGER.debug("后台系统找到对应的数据，放入缓存中....,hkey:{},key{}",ITEM_CACHE,itemId);
        jedisService.hset(ITEM_CACHE,itemId+"",JsonUtils.objectToJson(item));
        return item;
    }

    @Override
    public ItemDesc queryItemDescById(Long itemId) {
        return null;
    }

    @Override
    public void deleteItemCache(Long itemId) {
        LOGGER.debug("前台系统收到后台系统请求，删除缓存,商品id为{}",itemId);
        jedisService.hdel(ITEM_CACHE,itemId+"");
    }


}
