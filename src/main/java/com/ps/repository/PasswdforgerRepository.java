package com.ps.repository;

import com.ps.model.Passwdforger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Date;
import java.util.List;

/**
 * Created by samchu on 2017/2/18.
 */
@RepositoryRestResource
public interface PasswdforgerRepository extends JpaRepository<Passwdforger, String> {

    long countByAccountidAndCreateddateBetween(String accountid, Date start, Date end);

    List<Passwdforger> findByAccountid(String accountid);
    List<Passwdforger> findByCreateddateBetween(Date start, Date end);

    Passwdforger findByAccountidAndOtp(String accountid, String otp);
}
