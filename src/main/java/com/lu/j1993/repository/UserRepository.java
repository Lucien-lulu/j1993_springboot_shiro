package com.lu.j1993.repository;

import com.lu.j1993.entity.SysUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Administrator on 2019/8/3.
 */
public interface UserRepository extends CrudRepository<SysUser,Long> {
    SysUser findByUserName(String userName);
}
