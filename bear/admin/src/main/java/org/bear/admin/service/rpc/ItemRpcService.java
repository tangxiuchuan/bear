package org.bear.admin.service.rpc;

import org.bear.bean.Item;

import javax.jws.WebService;

//@WebService
public interface ItemRpcService {


    public Item queryItemById(Long id);

}
