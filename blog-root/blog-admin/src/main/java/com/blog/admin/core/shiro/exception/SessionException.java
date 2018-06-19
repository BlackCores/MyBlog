package com.blog.admin.core.shiro.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:HelloHeSir@gmail.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/6/11 16:09
 * @Description:自定义session异常
 */
@Getter
@Setter
public class SessionException extends RuntimeException {

    private int code = 500;

    private String msg;

    public SessionException() {
        super();
    }

    public SessionException(String message) {
        super(message);
        this.msg = message;
    }

    public SessionException(String message,int code) {
        super(message);
        this.msg = message;
        this.code = code;
    }

    public SessionException(String message, Throwable cause,int code) {
        super(message, cause);
        this.code = code;
    }

    public SessionException(Throwable cause,int code) {
        super(cause);
        this.code = code;
    }

}