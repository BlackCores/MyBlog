package com.blog.admin.common.constants;

import lombok.Getter;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/31 22:24
 * @Description:
 */
@Getter
public enum ConstantsUser implements Code{

    ZERO(0,"未激活"),
    ONE(1,"已激活"),
    THREE(2,"已锁定");

    private Integer code;
    private String msg;

    ConstantsUser(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
