package com.student.service;

public interface BaseService<T,ID>{
    T findById(ID id);
    //新增
    Boolean save(T entity);
    //更新
    Boolean update(T entity);
    //删除
    Boolean delete(ID id);
}
