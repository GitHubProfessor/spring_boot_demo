package com.roncoo.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.example.bean.RoncooUser;
import com.roncoo.example.bean.RoncooUserLog;
import com.roncoo.example.dao.RoncooUserDao;
import com.roncoo.example.dao.RoncooUserLogDao;

@RestController
@RequestMapping("/api")
public class ApiController {
	@Autowired  
    private RoncooUserDao roncooUserDao;  

	@Autowired
	private RoncooUserLogDao roncooUserLogDao;
	@CrossOrigin(origins = "http://localhost:8080")
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public HashMap<String, Object> get(@RequestParam String name) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("title", "hello world");
		map.put("name", name);
		return map;
	}
	
	@GetMapping(value = "/user/{id}")
	public HashMap<String, Object> findUser(@PathVariable int id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		RoncooUser user = roncooUserDao.selectById(id);
		map.put("user", user);
		return map;
	}
	
	@RequestMapping(value = "/userlog/add/{name}")
	public HashMap<String, Object> addUserLog(@PathVariable String name) {
		RoncooUserLog userLog = new RoncooUserLog();
		userLog.setUserIp("127.0.0.1");
		userLog.setUserName(name);
		roncooUserLogDao.save(userLog);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		return map;
	}
	
	@GetMapping(value = "/userlog/find")
	public HashMap<String, Object> findUsers() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<RoncooUserLog> list = roncooUserLogDao.findAll();
		map.put("userlogs", list);
		return map;
	}
	
	@GetMapping(value = "/userlog/find/{name}/{ip}")
	public HashMap<String, Object> findByUserNameAndUserIp(@PathVariable String name, @PathVariable String ip) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		RoncooUserLog userLog= roncooUserLogDao.findByUserNameAndUserIp(name, ip);
		map.put("userlog", userLog);
		return map;
	}
	
	@RequestMapping("error")
	public String error(ModelMap map){
		throw new RuntimeException("测试异常");
	}
}
