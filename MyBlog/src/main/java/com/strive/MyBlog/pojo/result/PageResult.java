package com.strive.MyBlog.pojo.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/4/9 0:09
 * @Description:分页返回
 */
@Setter
@Getter
public class PageResult<T> {

    private Boolean success;

    private Long total;

    private List<T> rows;

    public PageResult(Boolean success, Long total, List<T> rows) {
        this.success = success;
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Boolean success) {
        this.success = success;
    }

    public PageResult() {
    }
}
