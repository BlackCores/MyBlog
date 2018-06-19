package com.blog.admin.module.user.entity;

import com.blog.admin.common.base.entity.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 18:03
 * @Description:角色表
 */
@Entity
@Table(name = "app_role")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
public class AppRole extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_key")
    private String roleKey;

    @Column(name = "super_id")
    private String superId;

    @Transient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_role_fn",joinColumns = @JoinColumn(name = "role_id"),inverseJoinColumns = @JoinColumn(name = "fn_id"))
    private List<AppFn> appFnList;

}
