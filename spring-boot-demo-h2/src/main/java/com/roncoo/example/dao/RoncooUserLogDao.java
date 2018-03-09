package com.roncoo.example.dao;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roncoo.example.bean.RoncooUserLog;
import com.roncoo.example.util.base.Page;

public interface RoncooUserLogDao extends JpaRepository<RoncooUserLog, Integer>{

	@Query(value="select u from RoncooUserLog u where u.userName=?1")
	RoncooUserLog findByUserName(String string);
	//此种写法按照spring jpa规约，spring-data-jpa会自动将此方法名，转换为一个带有条件的查询语句。所以用了jpa，就不需要写实现了。spring-data-jpa都帮我们做了
	//可参考：http://blog.csdn.net/tian_yan71/article/details/73467899
	RoncooUserLog findByUserNameAndUserIp(String string, String ip);

	Page<RoncooUserLog> findByUserName(String string, Pageable pageable);
	
	@Query(value="select u from RoncooUserLog u",countQuery="2")
	List<RoncooUserLog> findAllUser();
}
