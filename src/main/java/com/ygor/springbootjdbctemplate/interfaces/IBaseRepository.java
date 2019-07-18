package com.ygor.springbootjdbctemplate.interfaces;

import java.util.List;

public interface IBaseRepository<T> {

    T getById(int id);

    List<T> getAll();

    T create(T entity);

    void update(int id, T entity);

    void remove(int id);

}
