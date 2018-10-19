package org.bear.admin.service;

import org.bear.bean.Item;

public interface ItemService extends BaseService<Item>{

    public void addItem(Item item, String desc);


    public void updateItem(Item item,String desc);

}
