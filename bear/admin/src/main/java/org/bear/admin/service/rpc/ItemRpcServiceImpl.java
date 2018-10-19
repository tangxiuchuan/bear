package org.bear.admin.service.rpc;

import org.bear.admin.service.ItemService;
import org.bear.bean.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("itemRpcService")
public class ItemRpcServiceImpl implements ItemRpcService {

    @Autowired
    private ItemService itemService;


    @Override
    public Item queryItemById(Long id) {
        return itemService.queryById(id);
    }
}
