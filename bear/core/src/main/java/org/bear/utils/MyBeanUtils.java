package org.bear.utils;


import org.apache.commons.beanutils.BeanUtils;

public class MyBeanUtils {

    public static <T> T  copyBean(Class<T> clazz,Object src){
        try {
            T bean = clazz.newInstance();


            BeanUtils.copyProperties(bean,src);


            return bean;
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }

    }

}
