package org.bear.front.service;

import org.bear.bean.Item;
import org.bear.bean.ItemDesc;

public interface ItemService {

    public Item queryItemById(Long itemId);

    public ItemDesc queryItemDescById(Long itemId);

    public void deleteItemCache(Long itemId);

}
