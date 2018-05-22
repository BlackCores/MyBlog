package com.strive.MyBlog.controller;

import com.strive.MyBlog.pojo.dto.PageParam;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/9 1:26
 * @Description:
 */
public interface BaseService<T, ID extends Serializable> {

    T get(ID id);

    Page<T> findPage(PageParam pageParam);

    T addObj(T obj);

}
