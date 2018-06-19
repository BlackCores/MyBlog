package com.blog.admin.module.user.entity;

import com.blog.admin.common.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/30 0:03
 * @Description:
 */
@Entity
@Table(name = "app_fn")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
public class AppFn extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fnId;

    @Column(name = "fn_name")
    private String fnName;

    @Column(name = "fn_key")
    private String fnKey;

    @Column(name = "fn_url")
    private String fnUrl;

    @Column(name = "status")
    private Integer status;

}
