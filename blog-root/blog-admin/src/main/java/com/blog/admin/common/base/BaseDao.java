package com.blog.admin.common.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/9 1:17
 * @Description:
 */
@NoRepositoryBean
public interface BaseDao<T,ID extends Serializable> extends JpaRepository<T,ID> {


}
