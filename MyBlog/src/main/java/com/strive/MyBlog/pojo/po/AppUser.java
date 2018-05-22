package com.strive.MyBlog.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.strive.MyBlog.pojo.base.BaseModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

/**
 * @author <a href="mailto:18335553083@163.com">Mr_He</a>
 * @Copyright (c)</ b> HeC<br/>
 * @createTime 2018/3/25 17:48
 * @Description:用户表
 */
@Entity
@Table(name = "app_user")
@Getter
@Setter
@DynamicUpdate
@DynamicInsert
public class AppUser extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "salt")
    private String salt;

    @Column(name = "status")
    private Integer status;     //0未激活 1正常 2冻结

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "ip_address")
    private String ipAddress;

    @Transient
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "app_user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<AppRole> roleList;

}
