package com.strive.MyBlog.pojo.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</   b> HeC<br/>
 * @createTime 2018/3/31 21:35
 * @Description:
 */
@Getter
public class JsonResult {

    /**
     * 状态
     */
    private Boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 返回对象
     */
    private Object obj;

    public JsonResult() {
    }

    public JsonResult(Boolean success) {
        this.success = success;
    }

    public JsonResult(Boolean success, Object obj) {
        this.success = success;
        this.obj = obj;
    }

    public JsonResult(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public JsonResult(Boolean success, String msg, Object obj) {
        this.success = success;
        this.msg = msg;
        this.obj = obj;
    }

    public JsonResult(Boolean success, String code, String msg, Object obj) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public JsonResult setSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public JsonResult setCode(String code) {
        this.code = code;
        return this;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public JsonResult setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
