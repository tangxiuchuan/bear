package org.bear.front.controller;

import org.bear.bean.Item;
import org.bear.front.service.ItemService;
import org.bear.reuslt.Result;
import org.bear.reuslt.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/item")
@ResponseBody
@Controller
public class ItemController {

    private Logger LOGGER=LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;


    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public Result<Item> queryItem(@PathVariable("itemId") Long itemId){
        LOGGER.debug("请求的商品id为:{}",itemId);
        Item item=itemService.queryItemById(itemId);
        if(item==null){
            LOGGER.error("请求商品不存在,{}",itemId);
            return ResultUtils.buildFail("请求商品不存在",103);
        }
        LOGGER.debug("请求成功");
        return ResultUtils.buildSuccess(item);
    }

    @RequestMapping(value = "/cache/delete/{itemId}",method = RequestMethod.GET)
    public Result<Item>  deleteItemCache(@PathVariable("itemId") Long itemId){
        LOGGER.debug("前台系统收到后台系统请求，删除缓存,商品id为{}",itemId);
        itemService.deleteItemCache(itemId);
        return ResultUtils.buildSuccess();
    }



}
