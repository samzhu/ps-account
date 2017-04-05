package com.ps.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by samchu on 2017/2/18.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class) //加這行 CreatedBy 才會生效
public class Passwdforger {
    @Id
    private String forgetid;
    private String fromip;
    @NotNull
    @NotEmpty
    private String accountid;
    @NotNull
    @NotEmpty
    private String otp;
    @NotNull
    private Boolean used;
    @NotNull
    private Boolean expired;
    @NotNull
    private Date expirdate;
    @CreatedDate
    private Date createddate;
    @LastModifiedDate
    private Date lastmodifieddate;
}
