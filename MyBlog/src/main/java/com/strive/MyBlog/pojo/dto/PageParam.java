package com.strive.MyBlog.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/10 1:37
 * @Description:分页对象
 */
@Setter
@Getter
public class PageParam {

    //页码0第一页
    private Integer pageNumber=0;

    //每页条数
    private Integer pageSize=10;

    //排序字段
    private String sortName="createTime";

    //排序方式
    private String sortOrder;

    //排序方式 传
    private Sort.Direction sorder;

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber-1;
    }

    public Sort.Direction getSorder() {
        if(sortOrder==null||sortOrder.toLowerCase().equals("desc")){
            return Sort.Direction.DESC;
        }else{
            return Sort.Direction.ASC;
        }
    }
}
