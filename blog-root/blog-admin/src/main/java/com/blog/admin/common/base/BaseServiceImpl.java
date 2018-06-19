package com.blog.admin.common.base;

import com.blog.admin.common.pojo.PageParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/9 1:26
 * @Description:
 */
public abstract class BaseServiceImpl<T,ID extends Serializable> implements BaseService<T,ID> {

    protected BaseDao<T,ID> dao;

    protected void setDao(BaseDao<T,ID> baseDao){
        this.dao = baseDao;
    }

    //得到泛型类T
    public Class getMyClass(){
        System.out.println(this.getClass());
        //class com.dfsj.generic.UserDaoImpl因为是该类调用的该法，所以this代表它

        //返回表示此 Class 所表示的实体类的 直接父类 的 Type。注意，是直接父类
        //这里type结果是 com.dfsj.generic.GetInstanceUtil<com.dfsj.generic.User>
        Type type = getClass().getGenericSuperclass();

        // 判断 是否泛型
        if (type instanceof ParameterizedType) {
            // 返回表示此类型实际类型参数的Type对象的数组.
            // 当有多个泛型类时，数组的长度就不是1了
            Type[] ptype = ((ParameterizedType) type).getActualTypeArguments();
            return (Class) ptype[0];  //将第一个泛型T对应的类返回（这里只有一个）
        } else {
            return Object.class;//若没有给定泛型，则返回Object类
        }

    }

    @Override
    public T get(ID id) {
        Optional<T> opt = dao.findById(id);
        return 0==opt.hashCode()?null:opt.get();
    }

    /**
     * 分页查询
     * @return
     * @param pageParam
     */
    @Override
    public Page<T> findPage(PageParam pageParam) {
        Pageable pageable = new PageRequest(pageParam.getPageNumber(),pageParam.getPageSize(), pageParam.getSorder(),pageParam.getSortName());
        return dao.findAll(pageable);
    }

    @Override
    public T addObj(T obj){
        return dao.save(obj);
    }
}
