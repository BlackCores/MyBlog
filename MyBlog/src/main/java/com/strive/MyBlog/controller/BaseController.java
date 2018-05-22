package com.strive.MyBlog.controller;

import cn.hutool.core.bean.BeanUtil;
import com.strive.MyBlog.pojo.dto.PageParam;
import com.strive.MyBlog.pojo.po.AppUser;
import com.strive.MyBlog.pojo.result.JsonResult;
import com.strive.MyBlog.pojo.result.PageResult;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</   b> HeC<br/>
 * @createTime 2018/4/9 1:15
 * @Description:
 */
public abstract class BaseController<T, ID extends Serializable> {

    protected BaseService<T, ID> service;

    protected void setService(BaseService<T,ID> baseService){
        this.service = baseService;
    };

    protected JsonResult get(ID id){
        if(null!=id) {
            T po = service.get(id);
            if (po != null) {
                return new JsonResult(true, po);
            } else {
                return new JsonResult(false, "没有查询到数据");
            }
        }else{
            return new JsonResult(false, "操作异常");
        }
    }

    protected T see(ID id){
        if(null!=id) {
            T po = service.get(id);
            if (po != null) {
                return po;
            } else {
                throw new RuntimeException("没有查到数据");
            }
        }else{
            throw new RuntimeException("ID不能为空");
        }
    }

    /**
     * 把浏览器参数转化放到Map集合中
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    protected Map<String, Object> getParam(HttpServletRequest request) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        String method = request.getMethod();
        Enumeration<?> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            if (key != null) {
                if (key instanceof String) {
                    String value = request.getParameter(key.toString());
                    if ("GET".equals(method)) {
                        try {
                            //前台encodeURIComponent('我们');转码后到后台还是ISO-8859-1，所以还需要转码
                            value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    paramMap.put(key.toString(), value);
                }
            }
        }
        return paramMap;
    }

    /**
     * 分页查询
     * @param request
     * @return
     */
    public PageResult findPage(HttpServletRequest request){
        Map<String, Object> param = getParam(request);
        PageParam pageParam;
        if(param!=null&&param.size()>0){
            pageParam = BeanUtil.mapToBean(param, PageParam.class,false);
        }else{
            pageParam = new PageParam();
        }
        Page<T> data = service.findPage(pageParam);
        if(null!=data&&data.getSize()>0){
            return new PageResult(true,data.getTotalElements(),data.getContent());
        }
        return new PageResult(false);

    }

    /**
     * 添加一个对象
     * @param obj
     * @return
     */
    public JsonResult addObj(T obj){
        try {
            obj = service.addObj(obj);
            if(obj!=null){
                return new JsonResult(true);
            }else{
                return new JsonResult(false,"添加失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new JsonResult(false,"系统出现异常");
    }

    /**
     * 更新
     * @param t
     * @return
     */
    public JsonResult update(T t){
        return addObj(t);
    }
}
