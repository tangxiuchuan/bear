package org.bear.admin.controller.rpc;

import org.bear.admin.service.ItemDescService;
import org.bear.admin.service.ItemService;
import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
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

@Controller
@ResponseBody
@RequestMapping("/rpc")
public class ItemRpcController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    private Logger LOGGER=LoggerFactory.getLogger(ItemRpcController.class);


         @RequestMapping(value = "/item/{itemId}",method = RequestMethod.GET)
         public Result<Item> queryItem(@PathVariable("itemId") Long itemId){
             LOGGER.debug("请求的商品id为:{}",itemId);
             Item item=itemService.queryById(itemId);
             if(item==null){
                LOGGER.error("请求商品不存在,{}",itemId);
                 return ResultUtils.buildFail("请求商品不存在",103);
             }
             LOGGER.debug("请求成功");
             return ResultUtils.buildSuccess(item);
         }



    @RequestMapping(value = "/item/desc/{itemId}",method = RequestMethod.GET)
    public Result<ItemDesc> queryItemDesc(@PathVariable("itemId") Long itemId){
        LOGGER.debug("请求的商品详情id为:{}",itemId);
        ItemDesc itemDesc=itemDescService.queryById(itemId);
        if(itemDesc==null){
            LOGGER.error("请求商品详情不存在,{}",itemId);
            return ResultUtils.buildFail("请求商品详情不存在",103);
        }
        LOGGER.debug("请求成功");
        return ResultUtils.buildSuccess(itemDesc);
    }




}
