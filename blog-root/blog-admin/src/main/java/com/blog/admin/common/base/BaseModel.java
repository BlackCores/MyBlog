package com.blog.admin.common.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 17:43
 * @Description:
 */
@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable {

    /**
     * 创建时间
     */
    @JsonIgnore
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    @Column(name = "modify_time")
    private Date modifyTime;

}
