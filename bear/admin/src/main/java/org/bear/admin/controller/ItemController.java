package org.bear.admin.controller;

import com.github.pagehelper.PageInfo;
import org.bear.admin.bean.EasyUIResult;
import org.bear.admin.service.ItemDescService;
import org.bear.admin.service.ItemService;
import org.bear.bean.Item;
import org.bear.bean.ItemDesc;
import org.bear.reuslt.Result;
import org.bear.reuslt.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/item")
@Controller
@ResponseBody
public class ItemController {

    private Logger LOGGER=LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;


    @RequestMapping(value ="/add",method = RequestMethod.POST)
    public Result addItem(Item item, String desc){
        if(item==null){
            return ResultUtils.buildFail("商品信息不能为空",101);
        }
        if(StringUtils.isEmpty(desc)){
            return ResultUtils.buildFail("商品的详情不能为空",102);
        }
        itemService.addItem(item,desc);
        return ResultUtils.buildSuccess();
    }


    @RequestMapping(value ="/update",method = RequestMethod.POST)
    public Result updateItem(Item item,String desc){
        if(item==null){
            return ResultUtils.buildFail("商品信息不能为空",101);
        }
        if(StringUtils.isEmpty(desc)){
            return ResultUtils.buildFail("商品的详情不能为空",102);
        }
        itemService.updateItem(item,desc);
        return ResultUtils.buildSuccess();
    }


    @RequestMapping(value = "/list")
    public EasyUIResult<Item> list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                  @RequestParam(value = "rows",defaultValue = "10") Integer rows){
        
        PageInfo<Item> pageInfo = itemService.queryPageListByWhere(null, (page - 1) * rows, rows);
        //组装easyUIResult
        EasyUIResult<Item> easyUIResult=new EasyUIResult<Item>(pageInfo.getTotal(),pageInfo.getList());
        return easyUIResult;
    }



    @RequestMapping(value = "/desc/{itemId}",method = RequestMethod.GET)
    public ItemDesc desc(@PathVariable("itemId") Long itemId){
        return itemDescService.queryById(itemId);
    }


}
