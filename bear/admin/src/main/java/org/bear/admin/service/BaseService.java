package org.bear.admin.service;

import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BaseService<T> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T queryById(Long id);

    /**
     * 根据指定条件查询一个
     * @param t
     * @return
     */
    public T queryByWhere(T t);


    /**
     * 根据条件查询多个不带分页
     * @param t
     * @return
     */
    public List<T> queryListByWhere(T t);


    /**
     * 根据条件查询多个带分页
     * @param t
     * @return
     */
    public PageInfo<T> queryPageListByWhere(T t,int startIndex,int pageSize);

    /**
     * 添加
     * @param t
     */
    public void save(T t);

    /**
     * 修改
     * @param t
     */
    public void update(T t);


    /**
     * 根据指定的id删除
     * @param id
     */
    public void deleteById(Long id);


    /**
     * 根据指定条件做批量删除
     * @param property  删除的条件
     * @param values   删除条件的集合
     */
    public void deleteByWhere(String property,List<Object> values);

}
