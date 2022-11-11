package com.kmodel99.put_appsec_simplecms.repository;

import com.kmodel99.put_appsec_simplecms.models.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long> {

}

