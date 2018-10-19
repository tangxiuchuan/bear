package org.bear.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bear.admin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 让其她的service实现类继承使用
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    //泛型注入
    @Autowired
    private Mapper<T> mapper;

    private Class clzzz;

    //反射泛型
    public BaseServiceImpl(){
        ParameterizedType type= (ParameterizedType)this.getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        this.clzzz= (Class) types[0];

    }


    @Override
    public T queryById(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T queryByWhere(T t) {
        return mapper.selectOne(t);
    }

    @Override
    public List<T> queryListByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public PageInfo<T> queryPageListByWhere(T t,int startIndex,int pageSize) {
        PageHelper.offsetPage(startIndex,pageSize);
        List<T> list = mapper.select(t);
        return new PageInfo<T>(list);
    }

    @Override
    public void save(T t) {

        mapper.insertSelective(t);

    }

    @Override
    public void update(T t) {
        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(Long id) {
      mapper.deleteByPrimaryKey(id);
    }

    public void deleteByWhere(String property,List<Object> values){

        Example example=new Example(clzzz);
        example.createCriteria().andIn(property,values);
        mapper.deleteByExample(example);
    }

}
