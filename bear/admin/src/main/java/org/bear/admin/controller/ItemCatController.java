package org.bear.admin.controller;


import org.bear.admin.service.ItemCatService;
import org.bear.bean.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/cat")
@ResponseBody
@Controller
public class ItemCatController {


    @Autowired
    private ItemCatService itemCatService;


    @RequestMapping("/list")
    public List<ItemCat> queryItemCatByParentId(@RequestParam(value = "id",defaultValue = "0") Long parentId){

       ItemCat record=new ItemCat();
       record.setParentId(parentId);
        return itemCatService.queryListByWhere(record);
    }




}
