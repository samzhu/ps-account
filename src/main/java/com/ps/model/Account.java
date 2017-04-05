package com.ps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ps.validators.UniqueUsername;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by samchu on 2017/2/14.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class) //加這行 CreatedBy 才會生效
public class Account {
    @Id
    //@GeneratedValue
    private String accountid;

    @NotNull
    @UniqueUsername(message = "Username already exists")
    @Size(min = 5, max = 255, message = "Username have to be grater than 8 characters")
    @Column(unique = true)
    private String username;

    private String email;

    @JsonIgnore
    @NotNull
    @Size(min = 8, max = 255, message = "Password have to be grater than 8 characters")
    private String password;

    @NotNull
    private boolean enabled = true;

    @NotNull
    private boolean credentialsexpired = false;

    @NotNull
    private boolean expired = false;

    @NotNull
    private boolean locked = false;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "AccountRole", joinColumns = @JoinColumn(name = "accountid", referencedColumnName = "accountid", insertable = false, updatable = false),
//            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "roleid", insertable = false, updatable = false) )
//    private List<Role> roles;

    @CreatedDate
    @Column(name = "createddate")
    private Date createddate;
    @CreatedBy
    @Column(name = "createdby")
    private String createdby;
    @LastModifiedDate
    @Column(name = "lastmodifieddate")
    private Date lastmodifieddate;
    @LastModifiedBy
    @Column(name = "lastmodifiedby")
    private String lastmodifiedby;
}
